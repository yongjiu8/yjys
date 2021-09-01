package com.example.yjys.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.androidkun.xtablayout.XTabLayout
import com.example.yjys.R
import com.example.yjys.Scanner
import com.example.yjys.adapter.MoverRecyclerViewAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.MoverCount
import com.example.yjys.myview.CategoryView
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.Net
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import org.jsoup.nodes.Document

class MoverFragment() : Fragment() {

    val strList = arrayListOf<String>("电视剧","电影","综艺","动漫")

    var tabMenu : XTabLayout? = null

    var moverList : RecyclerView? = null
    var refresh : SmartRefreshLayout? = null

    var tabSelected = 1

    var page = 1

    var menuSelect = 0

    val dataList = mutableListOf<MoverCount>()

    var moverRecyclerViewAdapter: MoverRecyclerViewAdapter? = null

    var baseUrl = AppConfig.pcUrl+"/dianshi"

    var inflate : View? = null

    var botScanner : LinearLayout? =null

    var catid : CategoryView? =null
    var yearid : CategoryView? =null
    var areaid : CategoryView? =null
    var actid : CategoryView? =null

    var mcatid : CategoryView? =null
    var myearid : CategoryView? =null
    var mareaid : CategoryView? =null
    var mactid : CategoryView? =null

    var zcatid : CategoryView? =null
    var zactid : CategoryView? =null
    var zareaid : CategoryView? =null

    var dcatid : CategoryView? =null
    var dyearid : CategoryView? =null
    var dareaid : CategoryView? =null

    var filterButton : ImageView ? = null

    val cats = mutableMapOf<String,String>()

    val years = mutableMapOf<String,String>()

    val areas = mutableMapOf<String,String>()

    val acts = mutableMapOf<String,String>()

    var cat = "all"
    var year = "all"
    var area = "all"
    var act = "all"

    var tv:LinearLayout? = null
    var mv:LinearLayout? = null
    var zv:LinearLayout? = null
    var dv:LinearLayout? = null


    //是否可见
    private var isVisibleToUser = false

    //是否初始化完成
    var isInit = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && isInit) {
            initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inflate == null){
            inflate = inflater.inflate(R.layout.mover_fragment, container, false)
        }
        tabMenu = inflate?.findViewById(R.id.tabMenu)
        moverList = inflate?.findViewById(R.id.moverList)
        refresh = inflate?.findViewById(R.id.refresh)
        botScanner = inflate?.findViewById(R.id.bot_scanner)

        catid = inflate?.findViewById(R.id.catid)
        yearid = inflate?.findViewById(R.id.yearid)
        areaid = inflate?.findViewById(R.id.areaid)
        actid = inflate?.findViewById(R.id.actid)

        mcatid = inflate?.findViewById(R.id.mcatid)
        myearid = inflate?.findViewById(R.id.myearid)
        mareaid = inflate?.findViewById(R.id.mareaid)
        mactid = inflate?.findViewById(R.id.mactid)

        zcatid = inflate?.findViewById(R.id.zcatid)
        zactid = inflate?.findViewById(R.id.zactid)
        zareaid = inflate?.findViewById(R.id.zareaid)

        dcatid = inflate?.findViewById(R.id.dcatid)
        dyearid = inflate?.findViewById(R.id.dyearid)
        dareaid = inflate?.findViewById(R.id.dareaid)

        filterButton = inflate?.findViewById(R.id.filter)

        tv = inflate?.findViewById(R.id.tv)
        mv = inflate?.findViewById(R.id.mv)
        zv = inflate?.findViewById(R.id.zv)
        dv = inflate?.findViewById(R.id.dv)

        botScanner?.setOnClickListener {
            val intent = Intent(activity, Scanner::class.java)
            activity?.startActivity(intent)
        }
        this.isInit = true
        return inflate
    }

    var tvFlag = false
    var mvFlag = false
    var zvFlag = false
    var dvFlag = false
    override fun onStart() {
        super.onStart()

        tvFlag = true
        mvFlag = true
        zvFlag = true
        dvFlag = true
        val linearLayoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        moverList?.layoutManager = linearLayoutManager
        moverRecyclerViewAdapter = MoverRecyclerViewAdapter(requireActivity(), dataList)
        moverList?.adapter = moverRecyclerViewAdapter

        refresh?.setRefreshHeader(ClassicsHeader(activity))
        refresh?.setRefreshFooter(
            ClassicsFooter(activity)
                .setSpinnerStyle(SpinnerStyle.Scale))

        refresh?.setOnRefreshListener {
            dataList.clear()
            page=1
            when(tabSelected){
                1 -> {
                    initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
                2 -> {
                    initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
                3 -> {
                    initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
                4 -> {
                    initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
            }
        }

        refresh?.setOnLoadMoreListener {
            when(tabSelected){
                1 -> {
                    initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno="+(++page)+"&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
                2 -> {
                    initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno="+(++page)+"&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
                3 -> {
                    initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&pageno="+(++page)+"&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
                4 -> {
                    initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&year=$year&area=$area&pageno="+(++page)+"&from=dianshi_channel%7Cdianshi_list",tabSelected)
                }
            }
        }

        tabMenu?.removeAllTabs()
        for (i in 0..3){
            tabMenu?.addTab(tabMenu?.newTab()!!)
            tabMenu?.getTabAt(i)?.setText(strList[i])
        }

        tabMenu?.getTabAt(menuSelect)?.select()

        tabMenu?.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                when(tab?.text){
                    "电视剧" -> {
                        tabSelected = 1
                        baseUrl = AppConfig.pcUrl+"/dianshi"
                        dataList.clear()
                        page=1
                        menuSelect = 0
                        tv?.visibility = View.VISIBLE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE

                        tvFlag = true
                        cat = "all"
                        year = "all"
                        area = "all"
                        act = "all"
                        initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                    "电影" -> {
                        tabSelected = 2
                        baseUrl = AppConfig.pcUrl+"/dianying"
                        dataList.clear()
                        page=1
                        menuSelect = 1
                        mv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                        mvFlag = true
                        cat = "all"
                        year = "all"
                        area = "all"
                        act = "all"
                        initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                    "综艺" -> {
                        tabSelected = 3
                        baseUrl =AppConfig.pcUrl+"/zongyi"
                        dataList.clear()
                        page=1
                        menuSelect = 2
                        zv?.visibility = View.VISIBLE
                        mv?.visibility = View.GONE
                        tv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                        zvFlag = true
                        cat = "all"
                        year = "all"
                        area = "all"
                        act = "all"
                        initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                    "动漫" -> {
                        tabSelected = 4
                        baseUrl = AppConfig.pcUrl+"/dongman"
                        dataList.clear()
                        page=1
                        menuSelect = 3
                        dv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dvFlag = true
                        cat = "all"
                        year = "all"
                        area = "all"
                        act = "all"
                        initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                }
            }



            override fun onTabUnselected(tab: XTabLayout.Tab?) {

            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {

            }

        })


        filterButton?.setOnClickListener {
            when(tabSelected){
                1 ->{
                    if (tv?.visibility == View.GONE){
                        tv?.visibility = View.VISIBLE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }else{
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
                2 ->{
                    if (mv?.visibility == View.GONE){
                        mv?.visibility = View.VISIBLE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                        tv?.visibility = View.GONE
                    }else{
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
                3 ->{
                    if (zv?.visibility == View.GONE){
                        zv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }else{
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
                4 ->{
                    if (dv?.visibility == View.GONE){
                        dv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                    }else{
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
            }
        }

        dataList.clear()

        if (isVisibleToUser && isInit) {
            initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
        }
    }

    fun initData(url : String,type : Int){
        when(type){
            1 -> {
                Net.get(requireActivity(),url,object :MyCallBack{
                    override fun callBack(doc: Document?) {

                        if (doc == null || "".equals(doc.body().text())) {
                            activity?.runOnUiThread {

                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
                            AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                            }
                            return
                        }
                        val home = doc.select(".s-tab-main")
                        val titles = home[0].select(".s1")
                        val counts = home[0].select(".star")
                        val juJis = home[0].select(".hint")
                        val imgs = home[0].getElementsByTag("img")
                        val urls = home[0].getElementsByTag("a")
                        var i = 0
                        for (it in titles){
                            val title = it.text()
                            val count = counts[i].text()
                            val juJi = juJis[i].text()
                            val img = imgs[i].attr("src")
                            val url = AppConfig.pcUrl + urls[i].attr("href")
                            Log.e("url输出：",title)
                            Log.e("url输出：",count)
                            Log.e("url输出：",juJi)
                            Log.e("url输出：",img)
                            Log.e("url输出：",url)
                            dataList.add(MoverCount(title, img, count, juJi, url))
                            i+=1
                        }

                        if (tvFlag){

                            //初始化分类
                            val select = doc.select("dd.item")
                            val select1 = select[1].select("a")
                            cats.clear()
                            cats["全部"] = "all"
                            for (it in select1){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("cat=")).replace("cat=","")
                                cats[key] = value
                            }

                            activity?.runOnUiThread {
                                catid?.add(cats)
                                catid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat = cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            val select2 = select[2].select("a")
                            years.clear()
                            years["全部"] = "all"
                            for (it in select2){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("year=")).replace("year=","")
                                years[key] = value
                            }

                            activity?.runOnUiThread {
                                yearid?.add(years)
                                yearid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        year =
                                            years[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            val select3 = select[3].select("a")
                            areas.clear()
                            areas["全部"] = "all"
                            for (it in select3){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("area=")).replace("area=","")
                                areas[key] = value
                            }

                            activity?.runOnUiThread {
                                areaid?.add(areas)
                                areaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            val select4 = select[4].select("a")
                            acts.clear()
                            acts["全部"] = "all"
                            for (it in select4){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("act=")).replace("act=","")
                                acts[key] = value
                            }

                            activity?.runOnUiThread {
                                actid?.add(acts)
                                actid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        act =
                                            acts[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            tvFlag = false
                             cat = "all"
                             year = "all"
                             area = "all"
                             act = "all"

                        }

                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()

                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        activity?.runOnUiThread {
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                        }
                    }

                })
            }
            2 -> {

                Net.get(requireActivity(),url,object :MyCallBack{

                    override fun callBack(doc: Document?) {

                        if (doc == null || "".equals(doc.body().text())) {
                            activity?.runOnUiThread {
                                refresh?.finishRefresh(false)
                                refresh?.finishLoadMore(false)
                                AestheticDialog.Builder(
                                    activity!!,
                                    DialogStyle.FLASH,
                                    DialogType.ERROR
                                )
                                    .setTitle("提示")
                                    .setMessage("加载失败！请检查您的网络")
                                    .show()
                            }
                            return
                        }

                        val home = doc.select(".s-tab-main")
                        val titles = home[0].select(".s1")
                        val counts = home[0].select(".star")
                        val juJis = home[0].select(".hint")
                        val imgs = home[0].getElementsByTag("img")
                        val urls = home[0].getElementsByTag("a")
                        var i = 0
                        for (it in titles){
                            val title = it.text()
                            val count = counts[i].text()
                            val juJi = juJis[i].text()
                            val img = imgs[i].attr("src")
                            val url = AppConfig.pcUrl + urls[i].attr("href")
                            Log.e("url输出：",title)
                            Log.e("url输出：",count)
                            Log.e("url输出：",juJi)
                            Log.e("url输出：",img)
                            Log.e("url输出：",url)
                            dataList.add(MoverCount(title, img, count, juJi, url))
                            i+=1
                        }

                        if (mvFlag){

                            //初始化分类
                            val select = doc.select("dd.item")
                            val select1 = select[1].select("a")
                            cats.clear()
                            cats["全部"] = "all"
                            for (it in select1){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("cat=")).replace("cat=","")
                                cats[key] = value
                            }

                            activity?.runOnUiThread {
                                mcatid?.add(cats)
                                mcatid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat = cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            val select2 = select[2].select("a")
                            years.clear()
                            years["全部"] = "all"
                            for (it in select2){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("year=")).replace("year=","")
                                years[key] = value
                            }

                            activity?.runOnUiThread {
                                myearid?.add(years)
                                myearid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        year =
                                            years[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            val select3 = select[3].select("a")
                            areas.clear()
                            areas["全部"] = "all"
                            for (it in select3){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("area=")).replace("area=","")
                                areas[key] = value
                            }

                            activity?.runOnUiThread {
                                mareaid?.add(areas)
                                mareaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            val select4 = select[4].select("a")
                            acts.clear()
                            acts["全部"] = "all"
                            for (it in select4){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("act=")).replace("act=","")
                                acts[key] = value
                            }

                            activity?.runOnUiThread {
                                mactid?.add(acts)
                                mactid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        act =
                                            acts[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1&from=dianshi_channel%7Cdianshi_list",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            mvFlag = false
                            cat = "all"
                            year = "all"
                            area = "all"
                            act = "all"

                        }

                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        activity?.runOnUiThread {
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                        }
                    }

                })

            }
            3 -> {

                Net.get(requireActivity(),url,object :MyCallBack{

                    override fun callBack(doc: Document?) {

                        if (doc == null || "".equals(doc.body().text())) {
                            activity?.runOnUiThread {
                                refresh?.finishRefresh(false)
                                refresh?.finishLoadMore(false)
                                AestheticDialog.Builder(
                                    activity!!,
                                    DialogStyle.FLASH,
                                    DialogType.ERROR
                                )
                                    .setTitle("提示")
                                    .setMessage("加载失败！请检查您的网络")
                                    .show()
                            }
                            return
                        }

                        val home = doc.select(".s-tab-main")
                        val titles = home[0].select(".s1")
                        val counts = home[0].select(".star")
                        val juJis = home[0].select(".hint")
                        val imgs = home[0].getElementsByTag("img")
                        val urls = home[0].getElementsByTag("a")
                        var i = 0
                        for (it in titles){
                            val title = it.text()
                            val count = counts[i].text()
                            val juJi = juJis[i].text()
                            val img = imgs[i].attr("src")
                            val url = AppConfig.pcUrl + urls[i].attr("href")
                            Log.e("url输出：",title)
                            Log.e("url输出：",count)
                            Log.e("url输出：",juJi)
                            Log.e("url输出：",img)
                            Log.e("url输出：",url)
                            dataList.add(MoverCount(title, img, count, juJi, url))
                            i+=1
                        }

                        if (zvFlag){

                            //初始化分类
                            val select = doc.select("dd.item")
                            val select1 = select[1].select("a")
                            cats.clear()
                            cats["全部"] = "all"
                            for (it in select1){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("cat=")).replace("cat=","")
                                cats[key] = value
                            }

                            activity?.runOnUiThread {
                                zcatid?.add(cats)
                                zcatid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat = cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                                        dataList.clear()
                                    }
                                })
                            }

                            val select4 = select[2].select("a")
                            acts.clear()
                            acts["全部"] = "all"
                            for (it in select4){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("act=")).replace("act=","")
                                acts[key] = value
                            }

                            activity?.runOnUiThread {
                                zactid?.add(acts)
                                zactid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        act =
                                            acts[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                                        dataList.clear()
                                    }
                                })
                            }


                            val select3 = select[3].select("a")
                            areas.clear()
                            areas["全部"] = "all"
                            for (it in select3){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("area=")).replace("area=","")
                                areas[key] = value
                            }

                            activity?.runOnUiThread {
                                zareaid?.add(areas)
                                zareaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData("$baseUrl/list.php?rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                                        dataList.clear()
                                    }
                                })
                            }

                            zvFlag = false
                            cat = "all"
                            year = "all"
                            area = "all"
                            act = "all"

                        }


                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        activity?.runOnUiThread {
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                        }
                    }


                })

            }
            4 -> {

                Net.get(requireActivity(),url,object :MyCallBack{

                    override fun callBack(doc: Document?) {

                        if (doc == null || "".equals(doc.body().text())) {
                            activity?.runOnUiThread {
                                refresh?.finishRefresh(false)
                                refresh?.finishLoadMore(false)
                                AestheticDialog.Builder(
                                    activity!!,
                                    DialogStyle.FLASH,
                                    DialogType.ERROR
                                )
                                    .setTitle("提示")
                                    .setMessage("加载失败！请检查您的网络")
                                    .show()
                            }
                            return
                        }

                        val home = doc.select(".s-tab-main")
                        val titles = home[0].select(".s1")
                        val counts = home[0].select(".star")
                        val juJis = home[0].select(".hint")
                        val imgs = home[0].getElementsByTag("img")
                        val urls = home[0].getElementsByTag("a")
                        var i = 0
                        for (it in titles){
                            val title = it.text()
                            val count = ""
                            val juJi = juJis[i].text()
                            val img = imgs[i].attr("src")
                            val url = AppConfig.pcUrl + urls[i].attr("href")
                            Log.e("url输出：",title)
                            Log.e("url输出：",count)
                            Log.e("url输出：",juJi)
                            Log.e("url输出：",img)
                            Log.e("url输出：",url)
                            dataList.add(MoverCount(title, img, count, juJi, url))
                            i+=1
                        }


                        if (dvFlag){

                            //初始化分类
                            val select = doc.select("dd.item")
                            val select1 = select[1].select("a")
                            cats.clear()
                            cats["全部"] = "all"
                            for (it in select1){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("cat=")).replace("cat=","")
                                cats[key] = value
                            }

                            activity?.runOnUiThread {
                                dcatid?.add(cats)
                                dcatid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat = cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                                        dataList.clear()
                                    }
                                })
                            }

                            val select4 = select[2].select("a")
                            years.clear()
                            years["全部"] = "all"
                            for (it in select4){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("year=")).replace("year=","")
                                years[key] = value
                            }

                            activity?.runOnUiThread {
                                dyearid?.add(years)
                                dyearid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        year =
                                            years[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                                        dataList.clear()
                                    }
                                })
                            }


                            val select3 = select[3].select("a")
                            areas.clear()
                            areas["全部"] = "all"
                            for (it in select3){
                                val key = it.text()
                                val href = it.attr("href")
                                val value = href.substring(href.indexOf("area=")).replace("area=","")
                                areas[key] = value
                            }

                            activity?.runOnUiThread {
                                dareaid?.add(areas)
                                dareaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(baseUrl+"/list.php?rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                                        dataList.clear()
                                    }
                                })
                            }

                            dvFlag = false
                            cat = "all"
                            year = "all"
                            area = "all"
                            act = "all"

                        }


                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        activity?.runOnUiThread {
                            refresh?.finishRefresh(false)
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                        }
                    }

                })

            }
        }

    }


}