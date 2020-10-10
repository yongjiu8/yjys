package com.example.homebutton.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.androidkun.xtablayout.XTabLayout
import com.example.homebutton.R
import com.example.homebutton.Scanner
import com.example.homebutton.adapter.FragmentAdapter
import com.example.homebutton.adapter.MoverFragmentAdapter
import com.example.homebutton.adapter.MoverRecyclerViewAdapter
import com.example.homebutton.config.AppConfig
import com.example.homebutton.entity.MoverCount
import com.example.homebutton.myview.MyFragment
import com.example.homebutton.utils.MyCallBack
import com.example.homebutton.utils.Net
import com.google.android.material.tabs.TabLayout
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.home_gridview_adapater.*
import org.jsoup.nodes.Document

class MoverFragment() : Fragment() {

    val strList = arrayListOf<String>("电视剧","电影","综艺","动漫")

    var tabMenu : XTabLayout? = null

    var moverList : RecyclerView? = null
    var refresh : SmartRefreshLayout? = null

    var tabSelected = 1

    var page = 24

    var menuSelect = 0

    val dataList = mutableListOf<MoverCount>()

    var moverRecyclerViewAdapter: MoverRecyclerViewAdapter? = null

    var baseUrl = AppConfig.pcUrl+"/dianshi"

    var inflate : View? = null

    var botScanner : LinearLayout? =null


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
        botScanner?.setOnClickListener {
            val intent = Intent(activity, Scanner::class.java)
            activity?.startActivity(intent)
        }
        return inflate
    }

    override fun onStart() {
        super.onStart()

        val linearLayoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        moverList?.layoutManager = linearLayoutManager
        moverRecyclerViewAdapter = MoverRecyclerViewAdapter(activity!!, dataList)
        moverList?.adapter = moverRecyclerViewAdapter

        refresh?.setRefreshHeader(ClassicsHeader(activity))
        refresh?.setRefreshFooter(
            ClassicsFooter(activity)
                .setSpinnerStyle(SpinnerStyle.Scale))

        refresh?.setOnRefreshListener {
            dataList.clear()
            page=1
            initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
        }

        refresh?.setOnLoadMoreListener {
            initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno="+(++page)+"&from=dianshi_channel%7Cdianshi_list",tabSelected)
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
                        initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                    "电影" -> {
                        tabSelected = 2
                        baseUrl = AppConfig.pcUrl+"/dianying"
                        dataList.clear()
                        page=1
                        menuSelect = 1
                        initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                    "综艺" -> {
                        tabSelected = 3
                        baseUrl =AppConfig.pcUrl+"/zongyi"
                        dataList.clear()
                        page=1
                        menuSelect = 2
                        initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                    "动漫" -> {
                        tabSelected = 4
                        baseUrl = AppConfig.pcUrl+"/dongman"
                        dataList.clear()
                        page=1
                        menuSelect = 3
                        initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)
                    }
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {

            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {

            }

        })
        dataList.clear()
        initData(baseUrl+"/list.php?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=1&from=dianshi_channel%7Cdianshi_list",tabSelected)

    }

    fun initData(url : String,type : Int){
        when(type){
            1 -> {
                Net.get(url,object :MyCallBack{
                    override fun callBack(doc: Document?) {
                        if (doc == null){
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
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
                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)
                    }

                })
            }
            2 -> {

                Net.get(url,object :MyCallBack{

                    override fun callBack(doc: Document?) {
                        if (doc == null){
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
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
                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)
                    }

                })

            }
            3 -> {

                Net.get(url,object :MyCallBack{

                    override fun callBack(doc: Document?) {
                        if (doc == null){
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
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
                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)
                    }

                })

            }
            4 -> {

                Net.get(url,object :MyCallBack{

                    override fun callBack(doc: Document?) {
                        if (doc == null){
                            refresh?.finishRefresh(false)
                            refresh?.finishLoadMore(false)
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
                        activity?.runOnUiThread {
                            moverRecyclerViewAdapter?.notifyDataSetChanged()
                        }

                        refresh?.finishRefresh(true)
                        refresh?.finishLoadMore(true)
                    }

                })

            }
        }

    }


}