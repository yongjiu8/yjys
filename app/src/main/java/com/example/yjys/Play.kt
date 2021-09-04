package com.example.yjys

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.yjys.adapter.DramaRecyclerViewAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.Drama
import com.example.yjys.entity.MoverInfo
import com.example.yjys.entity.MoverUrl
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.MyDb
import com.example.yjys.utils.Net
import com.example.yjys.utils.StringUtil
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.sdk.WebChromeClient
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.common_title.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class Play : BaseActivity() {

    val yuanList = mutableListOf<String>()

    val yuandataList = mutableListOf<String>()

    val dramaBanner = mutableListOf<Drama>()
    val dramaData = mutableListOf<String>()

    var dramaAdapter : DramaRecyclerViewAdapter? = null

    var url:String? = null
    var title:String? = null
    var img:String? = null
    var myDb : SQLiteDatabase? = null

    var activity: Activity? = null

    var type:String = ""

    var playView: View? = null


            companion object{
        @JvmStatic
        var nowLine = AppConfig.lineData[0].url
        @JvmStatic
        var nowDrama = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        supportActionBar?.hide()

        activity = this

        web.webChromeClient = object : WebChromeClient() {

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onShowCustomView(p0: View?, p1: IX5WebChromeClient.CustomViewCallback?) {

                homeTitle[0].visibility = View.GONE

                web.visibility = View.GONE

                playSelect.visibility = View.GONE

                playMiaoShu.visibility = View.GONE

                drama.visibility = View.GONE

                view1.visibility = View.GONE

                view2.visibility = View.GONE

                (activity as Play).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                window.insetsController?.also {
                    it.hide(WindowInsets.Type.statusBars())
                    it.hide(WindowInsets.Type.navigationBars())
                }



                val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)


                p0?.layoutParams = layoutParams

                playHome.removeView(playView)

                playHome.addView(p0)

                playView = p0
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onHideCustomView() {

                window.insetsController?.also {
                    it.show(WindowInsets.Type.statusBars())
                    it.show(WindowInsets.Type.navigationBars())
                }

                homeTitle[0].visibility = View.VISIBLE

                web.visibility = View.VISIBLE

                playSelect.visibility = View.VISIBLE

                playMiaoShu.visibility = View.VISIBLE

                drama.visibility = View.VISIBLE

                view1.visibility = View.VISIBLE

                view2.visibility = View.VISIBLE

                (activity as Play).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


            }
        }


        url = intent.getStringExtra("url")?.replace("//m.", "//www.")
        title = intent.getStringExtra("title")
        img = intent.getStringExtra("img")

        val db = MyDb(this, "myData.db", 1)
        myDb = db.writableDatabase

        //添加历史记录
        val rawQuery = myDb?.rawQuery("select id from history where title = ?", arrayOf(title))
        if (rawQuery?.count == 0){
            myDb?.execSQL("insert into history (title,img,url) values(?,?,?)", arrayOf(title, img, url))
        }

        //查询是否收藏
        val rawQuery1 = myDb?.rawQuery("select id from favorites where title = ?", arrayOf(title))
        if (rawQuery1?.count == 0){
            chang.setImageResource(R.drawable.shouchang_blue)
        }else{
            chang.setImageResource(R.drawable.shouchang_yelow)
        }

        //收藏被点击
        chang.setOnClickListener {
            val rawQuery1 = myDb?.rawQuery("select id from favorites where title = ?", arrayOf(title))
            if (rawQuery1?.count == 0){
                myDb?.execSQL("insert into favorites (title,img,url) values(?,?,?)", arrayOf(title, img, url))
                chang.setImageResource(R.drawable.shouchang_yelow)
                Toast.makeText(activity, "收藏成功", Toast.LENGTH_SHORT).show()
            }else{
                myDb?.execSQL("delete from favorites where title=?", arrayOf(title))
                chang.setImageResource(R.drawable.shouchang_blue)
                Toast.makeText(activity, "已取消收藏", Toast.LENGTH_SHORT).show()
            }
        }

        loading(this)

        val sharedPreferences = getSharedPreferences("data", 0)
        val position = sharedPreferences.getInt("line", 0)

        val lineNames = mutableListOf<String>()
        for (it in AppConfig.lineData) {
            lineNames.add(it.name)
        }


        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lineNames)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                nowLine = AppConfig.lineData[position].url
                if (nowDrama.indexOf("360kan.com") != -1) {

                    when(type){
                        "tv" -> {
                            playDirectByJuji(nowDrama, 0)
                        }
                        "movie" -> {
                            playDirect(nowDrama)
                        }
                        "arts" -> {
                            playDirectByJuji(nowDrama, 0)
                        }
                        "comics" -> {
                            playDirectByJuji(nowDrama, 0)
                        }
                    }

                }else {
                    web.loadUrl(nowLine + nowDrama)
                }
                val sharedPreferences = getSharedPreferences("data", 0).edit()
                sharedPreferences.putInt("line", position)
                sharedPreferences.commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        spinner.setSelection(position, true)




        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        dramaAdapter = DramaRecyclerViewAdapter(context!!, dramaBanner, false)
        drama.layoutManager = linearLayoutManager
        drama.adapter = dramaAdapter

        getDetails(url!!)

    }


    fun px2dp(context: Context, pxValue: Int): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun getDetails(url: String){
        Net.get(activity!!, url, object : MyCallBack {

            override fun callBack(doc: Document?) {
                if (doc == null || "".equals(doc.body().text())) {
                    activity?.runOnUiThread {
                        AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                    }
                    return
                }

                val split = url.split("/")
                var flag = true
                for (it in split) {
                    if (it.equals("va")) {
                        flag = false
                    }
                }
                var jsonText = ""
                if (flag) {
                    jsonText = StringUtil.getSubString(doc.toString(), "var serverdata =", ";")
                } else {
                    jsonText = StringUtil.getSubString(doc.toString(), "var serverdata=", ";")
                }

                Log.e("匹配", jsonText)

                val parseObject = JSON.parseObject(jsonText)
                type = parseObject.getString("playtype")
                val ids = parseObject.getString("id")
                val cat = parseObject.getString("cat")
                val playArray = parseObject.getJSONArray("playsite")
                Log.e("匹配", type)
                Log.e("匹配", ids)
                Log.e("匹配", cat)

                val biaoTi = doc.select(".title-left h1").text()
                var select: Elements? = null
                if (flag) {
                    if (type.equals("comics")) {
                        select = doc.select(".item-wrap")
                        val item = select[0].select(".item")
                        val leiXings = item[0].getElementsByTag("a")
                        var leiXing = ""
                        for (it in leiXings) {
                            leiXing += it.text() + " "
                        }

                        val nianDai = item[1].text().replace("<span>年代 ：</span>", "")
                        val diQu = item[2].text().replace("<span>地区 ：</span>", "")


                        val counts = doc.select(".item-desc")
                        val count = counts[counts.size - 1].text().replace("<<收起", "").replace("收起<<", "")
                        Log.e("匹配", biaoTi)
                        Log.e("匹配", leiXing)
                        Log.e("匹配", nianDai)
                        Log.e("匹配", diQu)
                        Log.e("匹配", count)
                        activity?.runOnUiThread {
                            comTitle.setText(biaoTi)
                            miaoShu.setText("类型：" + leiXing + "\n" + nianDai + "\n" + diQu + "\n" + "介绍：" + count)
                        }
                    } else {
                        select = doc.select(".item-wrap")
                        val item = select[0].select(".item")
                        val leiXings = item[0].getElementsByTag("a")
                        var leiXing = ""
                        for (it in leiXings) {
                            leiXing += it.text() + " "
                        }

                        val nianDai = item[1].text().replace("<span>年代 ：</span>", "")
                        val diQu = item[2].text().replace("<span>地区 ：</span>", "")

                        val daoYans = item[3].getElementsByTag("a")
                        var daoYan = ""
                        for (it in daoYans) {
                            daoYan += it.text() + " "
                        }

                        val yanYuans = item[4].getElementsByTag("a")
                        var yanYuan = ""
                        for (it in yanYuans) {
                            yanYuan += it.text() + " "
                        }


                        val counts = doc.select(".item-desc")
                        val count = counts[counts.size - 1].text().replace("<<收起", "").replace("收起<<", "")
                        Log.e("匹配", biaoTi)
                        Log.e("匹配", leiXing)
                        Log.e("匹配", nianDai)
                        Log.e("匹配", diQu)
                        Log.e("匹配", daoYan)
                        Log.e("匹配", yanYuan)
                        Log.e("匹配", count)
                        activity?.runOnUiThread {
                            comTitle.setText(biaoTi)
                            miaoShu.setText("类型：" + leiXing + "\n" + nianDai + "\n" + diQu + "\n" + "导演：" + daoYan + "\n" + "演员：" + yanYuan + "\n" + "介绍：" + count)
                        }
                    }

                } else {
                    select = doc.select(".base-item-wrap")
                    val item = select[0].select(".item")
                    val leiXings = item[0].getElementsByTag("a")
                    var leiXing = ""
                    for (it in leiXings) {
                        leiXing += it.text() + " "
                    }

                    val nianDai = item[1].text().replace("<span>年代 ：</span>", "")
                    val diQu = item[2].text().replace("<span>地区 ：</span>", "")

                    var daoYan = ""
                    if (item.size > 3) {
                        val daoYans = item[3].getElementsByTag("a")
                        for (it in daoYans) {
                            daoYan += it.text() + " "
                        }
                    }



                    val counts = doc.select(".item-desc")
                    val count = counts[counts.size - 1].text().replace("<<收起", "").replace("收起<<", "")
                    Log.e("匹配", biaoTi)
                    Log.e("匹配", leiXing)
                    Log.e("匹配", nianDai)
                    Log.e("匹配", diQu)
                    Log.e("匹配", daoYan)
                    Log.e("匹配", count)
                    activity?.runOnUiThread {
                        comTitle.setText(biaoTi)
                        miaoShu.setText("类型：" + leiXing + "\n" + nianDai + "\n" + diQu + "\n" + "明星：" + daoYan + "\n" + "介绍：" + count)
                    }
                }




                when (type) {
                    "tv" -> {
                        for (it in playArray) {
                            val jsonObject = it as JSONObject
                            val cnsite = jsonObject.getString("cnsite")
                            val ensite = jsonObject.getString("ensite")
                            yuanList.add(cnsite)
                            yuandataList.add(ensite)
                        }


                        activity?.runOnUiThread {
                            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, yuanList)
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                ) {
                                    initTv(yuandataList[position], ids, cat)
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }
                        }


                    }
                    "movie" -> {
                        yuanList.clear()
                        yuandataList.clear()
                        val select1 = doc.select(".js-site")
                        for (it in select1) {
                            val cnsite = it.text()
                            val ensite = it.attr("href")
                            yuanList.add(cnsite)
                            yuandataList.add(ensite)
                        }

                        activity?.runOnUiThread {
                            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, yuanList)
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                ) {
                                    nowDrama = yuandataList[position]
                                    if (nowDrama.indexOf("360kan.com") != -1) {
                                        playDirect(nowDrama)
                                    } else {
                                        web.loadUrl(nowLine + nowDrama)
                                    }

                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }
                        }

                    }
                    "arts" -> {
                        dramaData.clear()
                        dramaBanner.clear()

                        val titles = doc.select(".w-newfigure-hint")
                        val urls = doc.select(".js-link")
                        var i = 0
                        for (it in titles) {
                            val title = it.text()
                            val url = urls[i].attr("href")
                            Log.e("匹配", title)
                            Log.e("匹配", url)
                            dramaBanner.add(Drama(title, url, false))
                            dramaData.add(url)
                            i += 1
                        }

                        yuanList.clear()
                        yuandataList.clear()
                        val select1 = doc.select(".ea-site")
                        for (it in select1) {
                            val cnsite = it.text()
                            yuanList.add(cnsite)
                            yuandataList.add("")
                        }

                        activity?.runOnUiThread {
                            if (dramaBanner.size > 0) {
                                dramaBanner[0].isSelected = true
                            }
                            dramaAdapter = DramaRecyclerViewAdapter(context!!, dramaBanner, true)
                            drama.adapter = dramaAdapter

                            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, yuanList)
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                ) {

                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }
                        }

                        activity?.runOnUiThread {
                            dramaAdapter?.notifyDataSetChanged()
                            nowDrama = dramaData[0]
                            if (nowDrama.indexOf("360kan.com") != -1) {
                                playDirect(nowDrama)
                            } else {
                                web.loadUrl(nowLine + nowDrama)
                            }
                        }


                    }
                    "comics" -> {


                        for (it in playArray) {
                            val jsonObject = it as JSONObject
                            val cnsite = jsonObject.getString("cnsite")
                            val ensite = jsonObject.getString("ensite")
                            yuanList.add(cnsite)
                            yuandataList.add(ensite)
                        }


                        activity?.runOnUiThread {
                            val arrayAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, yuanList)
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                ) {
                                    initCommic(yuandataList[position], ids, cat)
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }
                        }


                    }
                }

            }

            override fun callError() {
                activity?.runOnUiThread {
                    AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                            .setTitle("提示")
                            .setMessage("加载失败！请检查您的网络")
                            .show()
                }
            }

        })

    }


    fun initTv(ensite: String, id: String, cat: String){
        Net.get(activity!!, "https://www.360kan.com/cover/switchsitev2?site=" + ensite + "&id=" + id + "&category=" + cat, object : MyCallBack {
            override fun callBack(doc: Document?) {
                if (doc == null || "".equals(doc.body().text())) {
                    activity?.runOnUiThread {
                        AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                    }
                    return
                }

                dramaData.clear()
                dramaBanner.clear()
                val text: String = doc.body().html().replace("\\&quot;", "")
                        .replace("&gt;", "")
                        .replace("&lt;", "")
                        .replace("\\\"", "\"")
                        .replace("\\r", "")
                Log.e("剧集输出", text)
                val subString = StringUtil.getSubString(text, "data\":\"", "\",\"error")
                Log.e("剧集输出", subString)

                val parse = Jsoup.parse(subString)
                val elementdt = parse.select(".num-tab-main")
                val element = elementdt[elementdt.size - 1]
                val titles = element.getElementsByTag("a")
                for (it in titles) {
                    val title = it.attr("data-num").replace("\\", "").replace("\"", "")
                    val url = it.attr("href").replace("\\", "")
                    if (!url.equals("#")) {
                        dramaBanner.add(Drama(title, url, false))
                        dramaData.add(url)
                        Log.e("剧集输出", title)
                        Log.e("剧集输出", url)
                    }
                }

                activity?.runOnUiThread {
                    if (dramaBanner.size > 0) {
                        dramaBanner[0].isSelected = true
                    }
                    dramaAdapter?.notifyDataSetChanged()
                    nowDrama = dramaData[0]
                    if (nowDrama.indexOf("360kan.com") != -1) {
                        playDirectByJuji(nowDrama, 0)
                    } else {
                        web.loadUrl(nowLine + nowDrama)
                    }
                }


            }

            override fun callError() {
                activity?.runOnUiThread {
                    AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                            .setTitle("提示")
                            .setMessage("加载失败！请检查您的网络")
                            .show()
                }
            }
        })

    }

    fun initCommic(ensite: String, id: String, cat: String){
        Net.get(activity!!, "https://www.360kan.com/cover/switchsitev2?site=" + ensite + "&id=" + id + "&category=" + cat, object : MyCallBack {
            override fun callBack(doc: Document?) {
                if (doc == null || "".equals(doc.body().text())) {
                    activity?.runOnUiThread {
                        AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                    }
                    return
                }

                dramaData.clear()
                dramaBanner.clear()
                val text: String = doc.body().html().replace("\\&quot;", "")
                        .replace("&gt;", "")
                        .replace("&lt;", "")
                        .replace("\\\"", "\"")
                        .replace("\\r", "")
                        .replace("js-series-part\"", "js-series-part")
                Log.e("剧集输出", text)
                val subString = StringUtil.getSubString(text, "data\":\"", "\",\"error")
                Log.e("剧集输出", subString)

                val parse = Jsoup.parse(subString)
                val elementdt = parse.select(".m-series-number-container")
                val element = elementdt[elementdt.size - 1]
                val titles = element.getElementsByTag("a")
                for (it in titles) {
                    val title = it.attr("data-num")
                            .replace("\\", "")
                            .replace("\"", "")
                            .replace("data-daochu=to=imgo", "")
                            .replace("data-daochu=to=qq", "")
                            .replace("data-daochu=to=qiyi", "")
                            .replace("data-daochu=to=cntv", "")
                            .replace("data-daochu=to=leshi", "")
                            .replace("data-daochu=to=sohu", "")
                            .replace("data-daochu=to=pptv", "")
                            .replace("data-daochu=to=tudou", "")
                            .replace("data-daochu=to=youku", "")
                    val url = it.attr("href").replace("\\", "")
                    if (!url.equals("###") && !url.equals("")) {
                        dramaBanner.add(Drama(title, url, false))
                        dramaData.add(url)
                        Log.e("剧集输出", title)
                        Log.e("剧集输出", url)
                    }
                }

                activity?.runOnUiThread {
                    if (dramaBanner.size > 0) {
                        dramaBanner[0].isSelected = true
                    }
                    dramaAdapter?.notifyDataSetChanged()
                    nowDrama = dramaData[0]
                    if (nowDrama.indexOf("360kan.com") != -1) {
                        playDirectByJuji(nowDrama, 0)
                    } else {
                        web.loadUrl(nowLine + nowDrama)
                    }
                }


            }

            override fun callError() {
                activity?.runOnUiThread {
                    AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                            .setTitle("提示")
                            .setMessage("加载失败！请检查您的网络")
                            .show()
                }
            }
        })

    }



    fun playDirect(url: String) {
        Net.get(activity!!, url, object : MyCallBack {
            override fun callBack(doc: Document?) {
                var moverBody = doc?.body().toString()
                val ceCode = StringUtil.getSubString(moverBody, "\"ce_code\":\"", "\"")

                var currentMinuteSends = System.currentTimeMillis()
                Net.get(
                        activity!!,
                        "https://pfmg.funshion.com/v1/play/list?ctime=$currentMinuteSends&fudid=$currentMinuteSends&sdk_type=fmg-web-js&sdk_ver=0.0.2.8&uc=99&sdk_token=ZjhsOWtnbixyaHI0cGR3cHV3Z2p2Z2FxLDE2MzAzOTYxNDU2OTMsZDY1MDI0MmRkMjIwOGM4MjlkNTQwM2E0OTFhMzIzM2E=&ce_code=$ceCode&appid=rhr4pdwpuwgjvgaq&uid=",
                        object : MyCallBack {
                            override fun callBack(doc: Document?) {
                                var textJson = doc?.body()?.text()
                                val moverInfo = JSON.parseObject(textJson, MoverInfo::class.java)
                                val playList = moverInfo.data.playList
                                val infohash = playList.mp4H264[playList.mp4H264.size - 1].infohash

                                Net.get(activity!!,
                                        "https://pfmg.funshion.com/v1/play/cdnurl?ctime=$currentMinuteSends&fudid=$currentMinuteSends&sdk_type=fmg-web-js&sdk_ver=0.0.2.8&uc=99&sdk_token=ZjhsOWtnbixyaHI0cGR3cHV3Z2p2Z2FxLDE2MzAzOTYxNDU2OTMsZDY1MDI0MmRkMjIwOGM4MjlkNTQwM2E0OTFhMzIzM2E=&ce_code=$ceCode&appid=rhr4pdwpuwgjvgaq&infohash=$infohash&uid=",
                                        object : MyCallBack {
                                            override fun callBack(doc: Document?) {
                                                if (doc != null) {
                                                    val text = doc.body().text()
                                                    val urlsDTO =
                                                            JSON.parseObject(text, MoverUrl::class.java)
                                                    val playUrl = urlsDTO.cdnUrls[0].url
                                                    activity?.runOnUiThread {
                                                        web.loadUrl(playUrl)
                                                    }

                                                }
                                            }

                                            override fun callError() {

                                            }
                                        }
                                )

                            }

                            override fun callError() {

                            }

                        }
                )

            }

            override fun callError() {

            }

        })
    }



    fun playDirectByJuji(url: String, position: Int) {
        Net.get(activity!!, url, object : MyCallBack {
            override fun callBack(doc: Document?) {
                var moverBody = doc?.body().toString()
                val ceCodes = StringUtil.getSubStrArray(moverBody, "data-ce=\"", "\"")
                val ceCode = ceCodes[position]
                var currentMinuteSends = System.currentTimeMillis()
                Net.get(
                        activity!!,
                        "https://pfmg.funshion.com/v1/play/list?ctime=$currentMinuteSends&fudid=$currentMinuteSends&sdk_type=fmg-web-js&sdk_ver=0.0.2.8&uc=99&sdk_token=ZjhsOWtnbixyaHI0cGR3cHV3Z2p2Z2FxLDE2MzAzOTYxNDU2OTMsZDY1MDI0MmRkMjIwOGM4MjlkNTQwM2E0OTFhMzIzM2E=&ce_code=$ceCode&appid=rhr4pdwpuwgjvgaq&uid=",
                        object : MyCallBack {
                            override fun callBack(doc: Document?) {
                                var textJson = doc?.body()?.text()
                                val moverInfo = JSON.parseObject(textJson, MoverInfo::class.java)
                                val playList = moverInfo.data.playList
                                val infohash = playList.mp4H264[playList.mp4H264.size - 1].infohash

                                Net.get(activity!!,
                                        "https://pfmg.funshion.com/v1/play/cdnurl?ctime=$currentMinuteSends&fudid=$currentMinuteSends&sdk_type=fmg-web-js&sdk_ver=0.0.2.8&uc=99&sdk_token=ZjhsOWtnbixyaHI0cGR3cHV3Z2p2Z2FxLDE2MzAzOTYxNDU2OTMsZDY1MDI0MmRkMjIwOGM4MjlkNTQwM2E0OTFhMzIzM2E=&ce_code=$ceCode&appid=rhr4pdwpuwgjvgaq&infohash=$infohash&uid=",
                                        object : MyCallBack {
                                            override fun callBack(doc: Document?) {
                                                if (doc != null) {
                                                    val text = doc.body().text()
                                                    val urlsDTO =
                                                            JSON.parseObject(text, MoverUrl::class.java)
                                                    val playUrl = urlsDTO.cdnUrls[0].url
                                                    activity?.runOnUiThread {
                                                        web.loadUrl(playUrl)
                                                    }

                                                }
                                            }

                                            override fun callError() {

                                            }
                                        }
                                )

                            }

                            override fun callError() {

                            }

                        }
                )

            }

            override fun callError() {

            }

        })
    }

}

