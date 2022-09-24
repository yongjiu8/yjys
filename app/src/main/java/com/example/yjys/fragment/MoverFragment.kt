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
import com.alibaba.fastjson.JSON
import com.androidkun.xtablayout.XTabLayout
import com.example.yjys.R
import com.example.yjys.Scanner
import com.example.yjys.adapter.MoverRecyclerViewAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.AutoType
import com.example.yjys.entity.DongManDto
import com.example.yjys.entity.MoverCount
import com.example.yjys.entity.MoverDto
import com.example.yjys.entity.TvDto
import com.example.yjys.entity.ZongYiDto
import com.example.yjys.myview.CategoryView
import com.example.yjys.utils.JsonFileUtil
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.Net
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import kotlinx.android.synthetic.main.activity_play.*
import org.jsoup.nodes.Document
import java.nio.charset.Charset

class MoverFragment() : Fragment() {

    val strList = arrayListOf<String>("电视剧", "电影", "综艺", "动漫")

    var tabMenu: XTabLayout? = null

    var moverList: RecyclerView? = null
    var refresh: SmartRefreshLayout? = null

    var tabSelected = 1

    var page = 1

    var menuSelect = 0

    val dataList = mutableListOf<MoverCount>()

    var moverRecyclerViewAdapter: MoverRecyclerViewAdapter? = null

    var baseUrl = AppConfig.pcUrl + "/v1/filter/list"

    var inflate: View? = null

    var botScanner: LinearLayout? = null

    var catid: CategoryView? = null
    var yearid: CategoryView? = null
    var areaid: CategoryView? = null
    var actid: CategoryView? = null

    var mcatid: CategoryView? = null
    var myearid: CategoryView? = null
    var mareaid: CategoryView? = null
    var mactid: CategoryView? = null

    var zcatid: CategoryView? = null
    var zactid: CategoryView? = null
    var zareaid: CategoryView? = null

    var dcatid: CategoryView? = null
    var dyearid: CategoryView? = null
    var dareaid: CategoryView? = null

    var filterButton: ImageView? = null

    val cats = mutableMapOf<String, String>()

    val years = mutableMapOf<String, String>()

    val areas = mutableMapOf<String, String>()

    val acts = mutableMapOf<String, String>()

    var cat = ""
    var year = ""
    var area = ""
    var act = ""

    var tv: LinearLayout? = null
    var mv: LinearLayout? = null
    var zv: LinearLayout? = null
    var dv: LinearLayout? = null

    var categoryId: Int = 2


    //是否可见
    private var isVisibleToUser = false

    //是否初始化完成
    var isInit = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && isInit) {
            initData(
                "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                tabSelected
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inflate == null) {
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
        val linearLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        moverList?.layoutManager = linearLayoutManager
        moverRecyclerViewAdapter = MoverRecyclerViewAdapter(requireActivity(), dataList)
        moverList?.adapter = moverRecyclerViewAdapter

        refresh?.setRefreshHeader(ClassicsHeader(activity))
        refresh?.setRefreshFooter(
            ClassicsFooter(activity)
                .setSpinnerStyle(SpinnerStyle.Scale)
        )

        refresh?.setOnRefreshListener {
            dataList.clear()
            page = 1
            when (tabSelected) {
                1 -> {
                    categoryId = 2
                    initData(
                        "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                        tabSelected
                    )
                }
                2 -> {
                    categoryId = 1
                    initData(
                        "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                        tabSelected
                    )
                }
                3 -> {
                    categoryId = 3
                    initData(
                        "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1",
                        tabSelected
                    )
                }
                4 -> {
                    categoryId = 4
                    initData(
                        "$baseUrl?catid=4&rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1",
                        tabSelected
                    )
                }
            }
        }

        refresh?.setOnLoadMoreListener {
            when (tabSelected) {
                1 -> {
                    categoryId = 2
                    initData(
                        "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=" + (++page),
                        tabSelected
                    )
                }
                2 -> {
                    categoryId = 1
                    initData(
                        "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=" + (++page),
                        tabSelected
                    )
                }
                3 -> {
                    categoryId = 3
                    initData(
                        "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&pageno=" + (++page),
                        tabSelected
                    )
                }
                4 -> {
                    categoryId = 4
                    initData(
                        "$baseUrl?catid=4&rank=rankhot&cat=$cat&year=$year&area=$area&pageno=" + (++page),
                        tabSelected
                    )
                }
            }
        }

        tabMenu?.removeAllTabs()
        for (i in 0..3) {
            tabMenu?.addTab(tabMenu?.newTab()!!)
            tabMenu?.getTabAt(i)?.setText(strList[i])
        }

        tabMenu?.getTabAt(menuSelect)?.select()

        tabMenu?.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                when (tab?.text) {
                    "电视剧" -> {
                        tabSelected = 1
                        dataList.clear()
                        page = 1
                        menuSelect = 0
                        tv?.visibility = View.VISIBLE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE

                        tvFlag = true
                        cat = ""
                        year = ""
                        area = ""
                        act = ""
                        categoryId = 2
                        initData(
                            "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                            tabSelected
                        )
                    }
                    "电影" -> {
                        tabSelected = 2
                        dataList.clear()
                        page = 1
                        menuSelect = 1
                        mv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                        mvFlag = true
                        cat = ""
                        year = ""
                        area = ""
                        act = ""
                        categoryId = 1
                        initData(
                            "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                            tabSelected
                        )
                    }
                    "综艺" -> {
                        tabSelected = 3
                        dataList.clear()
                        page = 1
                        menuSelect = 2
                        zv?.visibility = View.VISIBLE
                        mv?.visibility = View.GONE
                        tv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                        zvFlag = true
                        cat = ""
                        year = ""
                        area = ""
                        act = ""
                        categoryId = 3
                        initData(
                            "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1",
                            tabSelected
                        )
                    }
                    "动漫" -> {
                        tabSelected = 4
                        dataList.clear()
                        page = 1
                        menuSelect = 3
                        dv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dvFlag = true
                        cat = ""
                        year = ""
                        area = ""
                        act = ""
                        categoryId = 4
                        initData(
                            "$baseUrl?catid=4&rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1",
                            tabSelected
                        )
                    }
                }
            }


            override fun onTabUnselected(tab: XTabLayout.Tab?) {

            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {

            }

        })


        filterButton?.setOnClickListener {
            when (tabSelected) {
                1 -> {
                    if (tv?.visibility == View.GONE) {
                        tv?.visibility = View.VISIBLE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    } else {
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
                2 -> {
                    if (mv?.visibility == View.GONE) {
                        mv?.visibility = View.VISIBLE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                        tv?.visibility = View.GONE
                    } else {
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
                3 -> {
                    if (zv?.visibility == View.GONE) {
                        zv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    } else {
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                        dv?.visibility = View.GONE
                    }
                }
                4 -> {
                    if (dv?.visibility == View.GONE) {
                        dv?.visibility = View.VISIBLE
                        tv?.visibility = View.GONE
                        mv?.visibility = View.GONE
                        zv?.visibility = View.GONE
                    } else {
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
            when(tabSelected){
                1->{
                    initData(
                        "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                        tabSelected
                    )
                }
                2->{
                    initData(
                        "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                        tabSelected
                    )
                }
                3->{
                    initData(
                        "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                        tabSelected
                    )
                }
                4->{
                    initData(
                        "$baseUrl?catid=4&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                        tabSelected
                    )
                }
            }

        }
    }

    fun showNetworkOrDataError() {
        activity?.runOnUiThread {
            refresh?.finishRefresh(false)
            refresh?.finishLoadMore(false)
            AestheticDialog.Builder(requireActivity(), DialogStyle.FLASH, DialogType.ERROR)
                .setTitle("提示")
                .setMessage("加载失败！请检查您的网络")
                .show()
        }
    }

    fun showMessage(message: String) {
        activity?.runOnUiThread {
            refresh?.finishRefresh(false)
            refresh?.finishLoadMore(false)
            AestheticDialog.Builder(requireActivity(), DialogStyle.TOASTER, DialogType.INFO)
                .setTitle("提示")
                .setMessage(message)
                .show()
        }
    }

    fun pushJuji(up: Int, max :Int): String {
        if (up == max) {
            return "$max 集全"
        } else {
            return "更新至$up 集"
        }
    }

    fun initData(url: String, type: Int) {
        when (type) {
            1 -> {
                Net.get(requireActivity(), url, object : MyCallBack {
                    override fun callBack(doc: Document?) {
                        val data = Gson().fromJson<TvDto>(doc?.body()?.text(), TvDto::class.java)

                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        }else if (data.data == null){
                            showMessage("加载数据为空哦！")
                            return
                        }

                        for (it in data.data.movies) {
                            val title = it.title
                            val count = it.comment
                            val juJi = pushJuji(it.upinfo, it.total)
                            val img = "http:" + it.cdncover
                            val url = it.id
                            Log.e("url输出：", title)
                            Log.e("url输出：", count)
                            Log.e("url输出：", juJi)
                            Log.e("url输出：", img)
                            Log.e("url输出：", url)
                            dataList.add(MoverCount(title, img, count, juJi, url, categoryId))
                        }

                        if (tvFlag) {

                            //初始化分类

                            cats.clear()
                            val catJson = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/cat.json"
                                )
                            }
                            if (catJson != null) {
                                for (it in catJson.data) {
                                    cats[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                catid?.add(cats)
                                catid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat =
                                            cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }



                            years.clear()
                            val catJson2 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/year.json"
                                )
                            }
                            if (catJson2 != null) {
                                for (it in catJson2.data) {
                                    val key = it.title
                                    val value = it.id
                                    years[key] = value
                                }
                            }

                            activity?.runOnUiThread {
                                yearid?.add(years)
                                yearid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        year =
                                            years[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            areas.clear()
                            val catJson3 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/area.json"
                                )
                            }
                            if (catJson3 != null) {
                                for (it in catJson3.data) {
                                    val key = it.title
                                    val value = it.id
                                    areas[key] = value
                                }
                            }

                            activity?.runOnUiThread {
                                areaid?.add(areas)
                                areaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            acts.clear()
                            val catJson4 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/act.json"
                                )
                            }
                            if (catJson4 != null) {
                                for (it in catJson4.data) {
                                    val key = it.title
                                    val value = it.id
                                    acts[key] = value
                                }
                            }

                            activity?.runOnUiThread {
                                actid?.add(acts)
                                actid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        act =
                                            acts[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=2&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            tvFlag = false
                            cat = ""
                            year = ""
                            area = ""
                            act = ""

                        }

                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()

                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        showNetworkOrDataError()
                    }

                })
            }
            2 -> {

                Net.get(requireActivity(), url, object : MyCallBack {

                    override fun callBack(doc: Document?) {

                        val data = Gson().fromJson(doc?.body()?.text(), MoverDto::class.java)

                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        }else if (data.data == null){
                            showMessage("加载数据为空哦！")
                            return
                        }



                        for (it in data.data.movies) {
                            val title = it.title
                            val count = it.comment
                            val juJi = it.score.toString()
                            val img = "http:" + it.cdncover
                            val url = it.id
                            Log.e("url输出：", title)
                            Log.e("url输出：", count)
                            Log.e("url输出：", juJi)
                            Log.e("url输出：", img)
                            Log.e("url输出：", url)
                            dataList.add(MoverCount(title, img, count, juJi, url, categoryId))
                        }

                        if (mvFlag) {

                            //初始化分类
                            cats.clear()
                            val catJson = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/catmv.json"
                                )
                            }
                            if (catJson != null) {
                                for (it in catJson.data) {
                                    cats[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                mcatid?.add(cats)
                                mcatid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat =
                                            cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            years.clear()
                            val catJson2 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/yearmv.json"
                                )
                            }
                            if (catJson2 != null) {
                                for (it in catJson2.data) {
                                    years[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                myearid?.add(years)
                                myearid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        year =
                                            years[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            areas.clear()
                            val catJson3 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/areamv.json"
                                )
                            }
                            if (catJson3 != null) {
                                for (it in catJson3.data) {
                                    areas[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                mareaid?.add(areas)
                                mareaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            acts.clear()
                            val catJson4 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/actmv.json"
                                )
                            }
                            if (catJson4 != null) {
                                for (it in catJson4.data) {
                                    acts[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                mactid?.add(acts)
                                mactid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        act =
                                            acts[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=1&rank=rankhot&cat=$cat&area=$area&act=$act&year=$year&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            mvFlag = false
                            cat = ""
                            year = ""
                            area = ""
                            act = ""

                        }

                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        showNetworkOrDataError()
                    }

                })

            }
            3 -> {

                Net.get(requireActivity(), url, object : MyCallBack {

                    override fun callBack(doc: Document?) {

                        val data = Gson().fromJson(doc?.body()?.text(), ZongYiDto::class.java)

                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        }else if (data.data == null){
                            showMessage("加载数据为空哦！")
                            return
                        }


                        for (it in data.data.movies) {
                            val title = it.title
                            val count = it.lasttitle
                            val juJi = it.tag
                            val img = "http:" + it.cdncover
                            val url = it.id
                            Log.e("url输出：", title)
                            Log.e("url输出：", count)
                            Log.e("url输出：", juJi)
                            Log.e("url输出：", img)
                            Log.e("url输出：", url)
                            dataList.add(MoverCount(title, img, count, juJi, url, categoryId))
                        }

                        if (zvFlag) {

                            //初始化分类
                            cats.clear()
                            val catJson = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/catzongyi.json"
                                )
                            }
                            if (catJson != null) {
                                for (it in catJson.data) {
                                    cats[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                zcatid?.add(cats)
                                zcatid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat =
                                            cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            acts.clear()
                            val catJson2 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/actzongyi.json"
                                )
                            }
                            if (catJson2 != null) {
                                for (it in catJson2.data) {
                                    acts[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                zactid?.add(acts)
                                zactid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        act =
                                            acts[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            areas.clear()
                            val catJson3 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/areazongyi.json"
                                )
                            }
                            if (catJson3 != null) {
                                for (it in catJson3.data) {
                                    areas[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                zareaid?.add(areas)
                                zareaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=3&rank=rankhot&cat=$cat&area=$area&act=$act&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            zvFlag = false
                            cat = ""
                            year = ""
                            area = ""
                            act = ""

                        }


                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        showNetworkOrDataError()
                    }


                })

            }
            4 -> {

                Net.get(requireActivity(), url, object : MyCallBack {

                    override fun callBack(doc: Document?) {

                        val data = Gson().fromJson(doc?.body()?.text(), DongManDto::class.java)

                        if (data.errno != 0) {
                            showNetworkOrDataError()
                            return
                        }else if (data.data == null){
                            showMessage("加载数据为空哦！")
                            return
                        }


                        for (it in data.data.movies) {
                            val title = it.title
                            val count = it.comment
                            val juJi = pushJuji(it.upinfo, it.total)
                            val img = "http:" + it.cdncover
                            val url = it.id
                            Log.e("url输出：", title)
                            Log.e("url输出：", count)
                            Log.e("url输出：", juJi)
                            Log.e("url输出：", img)
                            Log.e("url输出：", url)
                            dataList.add(MoverCount(title, img, count, juJi, url, categoryId))
                        }


                        if (dvFlag) {

                            //初始化分类
                            cats.clear()
                            val catJson = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/catdongman.json"
                                )
                            }
                            if (catJson != null) {
                                for (it in catJson.data) {
                                    cats[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                dcatid?.add(cats)
                                dcatid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        cat =
                                            cats[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=4&rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            years.clear()
                            val catJson2 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/yeardongman.json"
                                )
                            }
                            if (catJson2 != null) {
                                for (it in catJson2.data) {
                                    years[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                dyearid?.add(years)
                                dyearid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        year =
                                            years[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=4&rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }


                            areas.clear()
                            val catJson3 = activity?.let {
                                JsonFileUtil.readJsonToAutoType(
                                    it,
                                    "json/areadongman.json"
                                )
                            }
                            if (catJson3 != null) {
                                for (it in catJson3.data) {
                                    areas[it.title] = it.id
                                }
                            }

                            activity?.runOnUiThread {
                                dareaid?.add(areas)
                                dareaid?.setOnClickCategoryListener(object :
                                    CategoryView.OnClickCategoryListener {
                                    override fun click(group: RadioGroup?, checkedId: Int) {
                                        area =
                                            areas[group?.findViewById<RadioButton>(checkedId)?.text.toString()].toString()
                                        initData(
                                            "$baseUrl?catid=4&rank=rankhot&cat=$cat&year=$year&area=$area&pageno=1",
                                            tabSelected
                                        )
                                        dataList.clear()
                                    }
                                })
                            }

                            dvFlag = false
                            cat = ""
                            year = ""
                            area = ""
                            act = ""

                        }


                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)

                    }

                    override fun callError() {
                        showNetworkOrDataError()
                    }

                })

            }
        }

    }


}