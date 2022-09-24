package com.example.yjys

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.yjys.adapter.DramaRecyclerViewAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.DongManDto
import com.example.yjys.entity.Drama
import com.example.yjys.entity.MoverInfo
import com.example.yjys.entity.MoverPlayDto
import com.example.yjys.entity.MoverUrl
import com.example.yjys.entity.TvPlayDto
import com.example.yjys.entity.ZongYiPlayDto
import com.example.yjys.net.getList
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.MyDb
import com.example.yjys.utils.Net
import com.example.yjys.utils.StringUtil
import com.google.gson.Gson
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
import java.util.Calendar
import java.util.Date


class Play : BaseActivity() {

    val yuanList = mutableListOf<String>()

    val dramaBanner = mutableListOf<Drama>()
    val dramaData = mutableListOf<String>()

    var dramaAdapter: DramaRecyclerViewAdapter? = null

    var url: String? = null
    var title: String? = null
    var img: String? = null
    var cattype: Int = 0
    var myDb: SQLiteDatabase? = null

    var activity: Activity? = null

    var type: String = ""

    var playView: View? = null


    companion object {
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

                homeTitle.visibility = View.GONE

                web.visibility = View.GONE

                playSelect.visibility = View.GONE

                playMiaoShu.visibility = View.GONE

                drama.visibility = View.GONE

                view1.visibility = View.GONE

                view2.visibility = View.GONE

                (activity as Play).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                if (Build.VERSION.SDK_INT >= 30) {
                    window.insetsController?.let {
                        it.hide(WindowInsets.Type.statusBars())
                        it.hide(WindowInsets.Type.navigationBars())
                    }
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // 全屏显示，隐藏状态栏和导航栏，拉出状态栏和导航栏显示一会儿后消失。
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                    } else {
                        // 全屏显示，隐藏状态栏
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_FULLSCREEN
                    }

                }


                val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )


                p0?.layoutParams = layoutParams

                playHome.removeView(playView)

                playHome.addView(p0)

                playView = p0
            }

            @RequiresApi(Build.VERSION_CODES.R)
            override fun onHideCustomView() {

                homeTitle.visibility = View.VISIBLE

                web.visibility = View.VISIBLE

                playSelect.visibility = View.VISIBLE

                playMiaoShu.visibility = View.VISIBLE

                drama.visibility = View.VISIBLE

                view1.visibility = View.VISIBLE

                view2.visibility = View.VISIBLE

                (activity as Play).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


                if (Build.VERSION.SDK_INT >= 30) {
                    window.insetsController?.let {
                        it.show(WindowInsets.Type.statusBars())
                        it.show(WindowInsets.Type.navigationBars())
                    }

                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    } else {

                        // 全屏显示，显示
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }


            }
        }


        url = intent.getStringExtra("url")
        title = intent.getStringExtra("title")
        img = intent.getStringExtra("img")
        cattype = intent.getIntExtra("type", 2)
        val db = MyDb(this, "myData.db", 1)
        myDb = db.writableDatabase

        //添加历史记录
        val rawQuery = myDb?.rawQuery("select id from history where title = ?", arrayOf(title))
        if (rawQuery?.count == 0) {
            myDb?.execSQL(
                "insert into history (title,img,url,cattype) values(?,?,?,?)", arrayOf(
                    title,
                    img,
                    url,
                    cattype
                )
            )
        }

        //查询是否收藏
        val rawQuery1 = myDb?.rawQuery("select id from favorites where title = ?", arrayOf(title))
        if (rawQuery1?.count == 0) {
            chang.setImageResource(R.drawable.shouchang_blue)
        } else {
            chang.setImageResource(R.drawable.shouchang_yelow)
        }

        //收藏被点击
        chang.setOnClickListener {
            val rawQuery1 = myDb?.rawQuery(
                "select id from favorites where title = ?",
                arrayOf(title)
            )
            if (rawQuery1?.count == 0) {
                myDb?.execSQL(
                    "insert into favorites (title,img,url,cattype) values(?,?,?,?)", arrayOf(
                        title,
                        img,
                        url,
                        cattype
                    )
                )
                chang.setImageResource(R.drawable.shouchang_yelow)
                Toast.makeText(activity, "收藏成功", Toast.LENGTH_SHORT).show()
            } else {
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
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                nowLine = AppConfig.lineData[position].url
                web.loadUrl(nowLine + nowDrama)
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

    fun showNetworkOrDataError() {
        activity?.runOnUiThread {
            AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                .setTitle("提示")
                .setMessage("加载失败！请检查您的网络")
                .show()
        }
    }

    fun showMessage(message: String) {
        activity?.runOnUiThread {
            AestheticDialog.Builder(activity!!, DialogStyle.TOASTER, DialogType.INFO)
                .setTitle("提示")
                .setMessage(message)
                .show()
        }
    }

    fun getDetails(url: String) {
        val urlDeital = "${AppConfig.pcUrl}/v1/detail?cat=$cattype&id=$url"

        Net.get(activity!!, urlDeital, object : MyCallBack {
            override fun callBack(doc: Document?) {
                when(cattype) {
                    2,4 -> {
                        val data = Gson().fromJson(doc?.body()?.text(), TvPlayDto::class.java)
                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        } else if (data.data == null) {
                            showMessage("加载数据为空哦！")
                            return
                        }

                        val biaoTi = data.data.title
                        val leiXing = data.data.moviecategory.joinToString(" ")
                        val nianDai = data.data.pubdate
                        val diQu = data.data.area.joinToString(" ")
                        val daoYan = data.data.director.joinToString(" ")
                        val yanYuan = data.data.actor.joinToString(" ")
                        val content = data.data.description

                        activity?.runOnUiThread {
                            comTitle.setText(biaoTi)
                            miaoShu.setText("类型：" + leiXing + "\n" + "年代：" +  nianDai + "\n" + "地区：" +  diQu + "\n" + "导演：" + daoYan + "\n" + "演员：" + yanYuan + "\n" + "介绍：" + content)
                        }

                        //播放源
                        yuanList.clear()
                        for (it in data.data.playlinkSites) {
                            yuanList.add(it)
                        }


                        activity?.runOnUiThread {
                            val arrayAdapter = ArrayAdapter(
                                context!!,
                                android.R.layout.simple_spinner_item,
                                yuanList
                            )
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        initTv(yuanList[position], urlDeital)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }
                                }
                        }


                    }

                    1->{
                        val data = Gson().fromJson(doc?.body()?.text(), MoverPlayDto::class.java)
                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        } else if (data.data == null) {
                            showMessage("加载数据为空哦！")
                            return
                        }

                        val biaoTi = data.data.title
                        val leiXing = data.data.moviecategory.joinToString(" ")
                        val nianDai = data.data.pubdate
                        val diQu = data.data.area.joinToString(" ")
                        val daoYan = data.data.director.joinToString(" ")
                        val yanYuan = data.data.actor.joinToString(" ")
                        val content = data.data.description
                        activity?.runOnUiThread {
                            comTitle.setText(biaoTi)
                            miaoShu.setText("类型：" + leiXing + "\n" + "年代：" +  nianDai + "\n" + "地区：" +  diQu + "\n" + "导演：" + daoYan + "\n" + "演员：" + yanYuan + "\n" + "介绍：" + content)
                        }

                        //播放源
                        yuanList.clear()
                        for (it in data.data.playlinkSites) {
                            yuanList.add(it)
                        }


                        activity?.runOnUiThread {
                            val arrayAdapter = ArrayAdapter(
                                context!!,
                                android.R.layout.simple_spinner_item,
                                yuanList
                            )
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        initMover(yuanList[position], urlDeital)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }
                                }
                        }

                    }
                    3->{
                        val data = Gson().fromJson(doc?.body()?.text(), ZongYiPlayDto::class.java)
                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        } else if (data.data == null) {
                            showMessage("加载数据为空哦！")
                            return
                        }

                        val biaoTi = data.data.title
                        val leiXing = data.data.moviecategory.joinToString(" ")
                        val nianDai = data.data.pubdate
                        val diQu = data.data.area.joinToString(" ")
                        val daoYan = data.data.director.joinToString(" ")
                        val yanYuan = data.data.actor.joinToString(" ")
                        val content = data.data.description
                        activity?.runOnUiThread {
                            comTitle.setText(biaoTi)
                            miaoShu.setText("类型：" + leiXing + "\n" + "年代：" +  nianDai + "\n" + "地区：" +  diQu + "\n" + "播出：" + daoYan + "\n" + "演员：" + yanYuan + "\n" + "介绍：" + content)
                        }

                        //播放源
                        yuanList.clear()
                        for (it in data.data.playlinkSites) {
                            yuanList.add(it)
                        }


                        activity?.runOnUiThread {
                            val arrayAdapter = ArrayAdapter(
                                context!!,
                                android.R.layout.simple_spinner_item,
                                yuanList
                            )
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner1.adapter = arrayAdapter

                            spinner1.onItemSelectedListener =
                                object : AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long
                                    ) {
                                        initZongYi(yuanList[position], urlDeital)
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>?) {

                                    }
                                }
                        }

                    }



                }
            }

            override fun callError() {

            }
        })

    }


    fun initTv(key: String, url:String) {
        Net.get(activity!!, url+ "&site=$key", object : MyCallBack {
            override fun callBack(doc: Document?) {
                val data = Gson().fromJson(doc?.body()?.text(), TvPlayDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }
                val source = data.data.allepidetail
                var list = mutableListOf<TvPlayDto.DataDTO.AllepidetailDTO.QiyiDTO>()
                when(key){
                    "qq"->{
                        list = source.qq
                    }
                    "cntv"->{
                        list = source.cntv
                    }
                    "imgo"->{
                        list = source.imgo
                    }
                    "leshi"->{
                        list = source.leshi
                    }
                    "qiyi"->{
                        list = source.qiyi
                    }
                    "sohu"->{
                        list = source.sohu
                    }
                    "youku"->{
                        list = source.youku
                    }
                    "xigua"->{
                        list = source.xigua
                    }
                    "m1905"->{
                        list = source.m1905
                    }
                    "pptv"->{
                        list = source.pptv
                    }
                    "levp"->{
                        list = source.levp
                    }
                    "douyin"->{
                        list = source.douyin
                    }
                }

                if (list == null || list.size == 0){
                    showMessage("此影片没有播放数据哦！换一个试试吧")
                }

                dramaBanner.clear()
                dramaData.clear()
                for (it in list){
                    dramaBanner.add(Drama(it.playlinkNum, it.url, false))
                    dramaData.add(it.url)
                }

                activity?.runOnUiThread {
                    if (dramaBanner.size > 0) {
                        dramaBanner[0].isSelected = true
                    }
                    dramaAdapter?.notifyDataSetChanged()
                    nowDrama = dramaData[0]
                    web.loadUrl(nowLine + nowDrama)
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

    }

    fun initMover(key: String, url:String) {
        Net.get(activity!!, url+ "&site=$key", object : MyCallBack {
            override fun callBack(doc: Document?) {
                val data = Gson().fromJson(doc?.body()?.text(), MoverPlayDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }
                val source = data.data.playlinksdetail

                when(key){
                    "qq"->{
                        nowDrama = source.qq.defaultUrl
                    }
                    "cntv"->{
                        nowDrama = source.cntv.defaultUrl
                    }
                    "imgo"->{
                        nowDrama = source.imgo.defaultUrl
                    }
                    "leshi"->{
                        nowDrama = source.leshi.defaultUrl
                    }
                    "qiyi"->{
                        nowDrama = source.qiyi.defaultUrl
                    }
                    "sohu"->{
                        nowDrama = source.sohu.defaultUrl
                    }
                    "youku"->{
                        nowDrama = source.youku.defaultUrl
                    }
                    "xigua"->{
                        nowDrama = source.xigua.defaultUrl
                    }
                    "m1905"->{
                        nowDrama = source.m1905.defaultUrl
                    }
                    "pptv"->{
                        nowDrama = source.pptv.defaultUrl
                    }
                    "levp"->{
                        nowDrama = source.levp.defaultUrl
                    }
                    "douyin"->{
                        nowDrama = source.douyin.defaultUrl
                    }
                    else ->{
                        Log.e(this.toString(), type)
                        showMessage("此影片没有播放数据哦！换一个试试吧")
                        return
                    }
                }



                activity?.runOnUiThread {
                    web.loadUrl(nowLine + nowDrama)
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

    }

    fun initZongYi(key: String, url:String) {
        Net.get(activity!!, url+ "&site=$key&year=" + Calendar.getInstance().get(Calendar.YEAR), object : MyCallBack {
            override fun callBack(doc: Document?) {
                val data = Gson().fromJson(doc?.body()?.text(), ZongYiPlayDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }
                val list = data.data.defaultepisode

                if (list == null || list.size == 0){
                    showMessage("此影片没有播放数据哦！换一个试试吧")
                    return
                }

                dramaBanner.clear()
                dramaData.clear()
                for (it in list){
                    dramaBanner.add(Drama(it.name, it.url, false))
                    dramaData.add(it.url)
                }

                activity?.runOnUiThread {
                    if (dramaBanner.size > 0) {
                        dramaBanner[0].isSelected = true
                    }
                    dramaAdapter = DramaRecyclerViewAdapter(context!!, dramaBanner, true)
                    drama.adapter = dramaAdapter
                    dramaAdapter?.notifyDataSetChanged()
                    nowDrama = dramaData[0]
                    web.loadUrl(nowLine + nowDrama)
                }

            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

    }


}

