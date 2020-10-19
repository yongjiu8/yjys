package com.example.homebutton.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.homebutton.BaseActivity
import com.example.homebutton.Play
import com.example.homebutton.R
import com.example.homebutton.Scanner
import com.example.homebutton.adapter.HomeCountAdapter
import com.example.homebutton.config.AppConfig
import com.example.homebutton.entity.MoverCount
import com.example.homebutton.myview.MyFragment
import com.example.homebutton.myviewgroup.HomeCountView
import com.example.homebutton.utils.MyCallBack
import com.example.homebutton.utils.Net
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class HomeFragment() : Fragment(){

    val bannerDataTitle = mutableListOf<String>()
    val bannerDataImg = mutableListOf<String>()
    val bannerDataUrl = mutableListOf<String>()

    var banner : Banner? = null
    var tvplay : HomeCountView? = null
    var film : HomeCountView? = null
    var variety : HomeCountView? = null
    var juvenile : HomeCountView? = null
    var comic : HomeCountView? = null
    var refresh : SmartRefreshLayout? = null
    var botScanner : LinearLayout? =null


    val data1 = mutableListOf<MoverCount>()
    val data2 = mutableListOf<MoverCount>()
    val data3 = mutableListOf<MoverCount>()
    val data4 = mutableListOf<MoverCount>()
    val data5 = mutableListOf<MoverCount>()

    var homeCountAdapter1 : HomeCountAdapter? = null
    var homeCountAdapter2 : HomeCountAdapter? = null
    var homeCountAdapter3 : HomeCountAdapter? = null
    var homeCountAdapter4 : HomeCountAdapter? = null
    var homeCountAdapter5 : HomeCountAdapter? = null

    var inflate : View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inflate == null){
            inflate = inflater.inflate(R.layout.home_fragment, container, false)
        }

        banner = inflate?.findViewById(R.id.home_banner)

        tvplay = inflate?.findViewById(R.id.tvplay)

        film = inflate?.findViewById(R.id.film)

        variety = inflate?.findViewById(R.id.variety)

        juvenile = inflate?.findViewById(R.id.juvenile)

        comic = inflate?.findViewById(R.id.comic)

        refresh = inflate?.findViewById(R.id.refresh)

        botScanner = inflate?.findViewById(R.id.bot_scanner)
        botScanner?.setOnClickListener {
            val intent = Intent(activity, Scanner::class.java)
            activity?.startActivity(intent)
        }

        banner?.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner?.setImageLoader(MyLoader())
        banner?.setBannerAnimation(Transformer.Default)
        //切换频率
        banner?.setDelayTime(3000)
        //自动启动
        banner?.isAutoPlay(true)
        //位置设置
        banner?.setIndicatorGravity(BannerConfig.CENTER)
        banner?.setOnBannerListener {
            if (bannerDataUrl[it].indexOf("qhvideo") == -1){
                val intent = Intent(context, Play::class.java)
                intent.putExtra("url",bannerDataUrl[it])
                intent.putExtra("img",bannerDataImg[it])
                intent.putExtra("title",bannerDataTitle[it])
                startActivity(intent)
            }
        }

        //经典样式
        refresh?.setRefreshHeader(ClassicsHeader(activity))

        refresh?.setOnRefreshListener {
            banner?.stopAutoPlay()
            initBanner()
            initCountData()
        }


        return inflate
    }

    override fun onStart() {
        super.onStart()

        banner?.stopAutoPlay()

        /*initBanner()
        initCountData()*/

        refresh?.autoRefresh()

    }

    override fun onDestroy() {
        super.onDestroy()
    }



    fun initBanner(){
        Net.get(AppConfig.mobileUrl, object : MyCallBack {
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
                val select = doc.getElementsByClass("swiper-wrapper")
                val imgs = select[0].getElementsByTag("img")
                val urls = select[0].getElementsByTag("a")
                val titles = select[0].getElementsByTag("span")

                bannerDataUrl.clear()
                bannerDataImg.clear()
                bannerDataTitle.clear()

                var i = 0
                for (img in imgs) {
                    val img = img.attr("src").toString()
                    Log.e("url输出：", img)
                    val url = urls[i].attr("href")
                    Log.e("url输出：", url)
                    val title = urls[i].text()
                    Log.e("url输出：", title)
                    bannerDataTitle.add(title)
                    bannerDataImg.add(img)
                    bannerDataUrl.add(url)
                    i += 1
                }

                banner?.setImages(bannerDataImg)
                banner?.setBannerTitles(bannerDataTitle)

                activity?.runOnUiThread {
                    //开始运行
                    banner?.start()
                }
                initdata(doc)
            }

            override fun callError() {
                activity?.runOnUiThread {
                    refresh?.finishRefresh(false)
                    AestheticDialog.Builder(activity!!, DialogStyle.FLASH, DialogType.ERROR)
                        .setTitle("提示")
                        .setMessage("加载失败！请检查您的网络")
                        .show()
                }
            }
        })





    }


    private class MyLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }

    fun initCountData(){
        var ctitle = tvplay?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("电视剧")
        var img = tvplay?.findViewById<ImageView>(R.id.img)
        img?.setImageResource(R.drawable.tv)
        tvplay?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked = true
        }
        var gridview = tvplay?.findViewById<GridView>(R.id.gridview)
        var activity = this.activity
        data1.clear()
        homeCountAdapter1 = HomeCountAdapter(activity!!, data1)
        gridview?.adapter=homeCountAdapter1

        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data1[position].url, Toast.LENGTH_SHORT).show()
        }


        ctitle = film?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("电影")
        var img_film = film?.findViewById<ImageView>(R.id.img)
        img_film?.setImageResource(R.drawable.mover_home)
        film?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked = true
        }
        gridview = film?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data2.clear()
        homeCountAdapter2 = HomeCountAdapter(activity!!, data2)
        gridview?.adapter=homeCountAdapter2
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data2[position].url, Toast.LENGTH_SHORT).show()
        }

        ctitle = variety?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("综艺")
        var img_variety = variety?.findViewById<ImageView>(R.id.img)
        img_variety?.setImageResource(R.drawable.zongyi)
        variety?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked = true
        }
        gridview = variety?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data3.clear()
        homeCountAdapter3 = HomeCountAdapter(activity!!, data3)
        gridview?.adapter=homeCountAdapter3
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data3[position].url, Toast.LENGTH_SHORT).show()
        }

        ctitle = juvenile?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("少儿")
        var img_juvenile = juvenile?.findViewById<ImageView>(R.id.img)
        img_juvenile?.setImageResource(R.drawable.saoer)
        juvenile?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked = true
        }
        gridview = juvenile?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data4.clear()
        homeCountAdapter4 = HomeCountAdapter(activity!!, data4)
        gridview?.adapter=homeCountAdapter4
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data4[position].url, Toast.LENGTH_SHORT).show()
        }

        ctitle = comic?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("动漫")
        var img_comic = comic?.findViewById<ImageView>(R.id.img)
        img_comic?.setImageResource(R.drawable.dongman)
        comic?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked = true
        }
        gridview = comic?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data5.clear()
        homeCountAdapter5 = HomeCountAdapter(activity!!, data5)
        gridview?.adapter=homeCountAdapter5
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data5[position].url, Toast.LENGTH_SHORT).show()
        }

    }

    fun initdata(doc: Document){

                data1.clear()
                data2.clear()
                data3.clear()
                data4.clear()
                data5.clear()
                val uls = doc.select(".mb-list")
                var urls = uls[1].getElementsByTag("a")
                var imgs = uls[1].select("div[class='mb-img']")
                var juJis = uls[1].select("span[class='duration']")
                var titles = uls[1].select("p[class='title']")
                var counts = uls[1].select("p[class='desc']")
                var i = 0
                for (urlTag in urls) {
                    val url = urlTag.attr("href")
                    Log.e("url输出：", url)
                    val img = imgs[i].attr("style").toString().split("(")[1].split(")")[0]
                    Log.e("url输出：", img)
                    val juJi = juJis[i].text()
                    Log.e("url输出：", juJi)
                    val title = titles[i].text()
                    Log.e("url输出：", title)
                    val count = counts[i].text()
                    Log.e("url输出：", count)
                    data1.add(MoverCount(title, img, count, juJi, url))
                    i += 1
                }
                activity?.runOnUiThread {
                    homeCountAdapter1?.notifyDataSetChanged()
                }

                urls = uls[2].getElementsByTag("a")
                imgs = uls[2].select("div[class='mb-img']")
                juJis = uls[2].select("span[class='year']")
                titles = uls[2].select("p[class='title']")
                counts = uls[2].select("p[class='desc']")
                i = 0
                for (urlTag in urls) {
                    val url = urlTag.attr("href")
                    Log.e("url输出：", url)
                    val img = imgs[i].attr("style").toString().split("(")[1].split(")")[0]
                    Log.e("url输出：", img)
                    val juJi = juJis[i].text()
                    Log.e("url输出：", juJi)
                    val title = titles[i].text()
                    Log.e("url输出：", title)
                    val count = counts[i].text()
                    Log.e("url输出：", count)
                    data2.add(MoverCount(title, img, count, juJi, url))
                    i += 1
                }
                activity?.runOnUiThread {
                    homeCountAdapter2?.notifyDataSetChanged()
                }

                urls = uls[3].getElementsByTag("a")
                imgs = uls[3].select("div[class='mb-img']")
                juJis = uls[3].select("span[class='duration']")
                titles = uls[3].select("p[class='title']")
                counts = uls[3].select("p[class='desc']")
                i = 0
                for (urlTag in urls) {
                    val url = urlTag.attr("href")
                    Log.e("url输出：", url)
                    val img = imgs[i].attr("style").toString().split("(")[1].split(")")[0]
                    Log.e("url输出：", img)
                    val juJi = juJis[i].text()
                    Log.e("url输出：", juJi)
                    val title = titles[i].text()
                    Log.e("url输出：", title)
                    val count = counts[i].text()
                    Log.e("url输出：", count)
                    data3.add(MoverCount(title, img, count, juJi, url))
                    i += 1
                }
                activity?.runOnUiThread {
                    homeCountAdapter3?.notifyDataSetChanged()
                }

                urls = uls[4].getElementsByTag("a")
                imgs = uls[4].select("div[class='mb-img']")
                juJis = uls[4].select("span[class='duration']")
                titles = uls[4].select("p[class='title']")
                counts = uls[4].select("p[class='desc']")
                i = 0
                for (urlTag in urls) {
                    val url = urlTag.attr("href")
                    Log.e("url输出：", url)
                    val img = imgs[i].attr("style").toString().split("(")[1].split(")")[0]
                    Log.e("url输出：", img)
                    val juJi = juJis[i].text()
                    Log.e("url输出：", juJi)
                    val title = titles[i].text()
                    Log.e("url输出：", title)
                    val count = counts[i].text()
                    Log.e("url输出：", count)
                    data4.add(MoverCount(title, img, count, juJi, url))
                    i += 1
                }
                activity?.runOnUiThread {
                    homeCountAdapter4?.notifyDataSetChanged()
                }

                urls = uls[5].getElementsByTag("a")
                imgs = uls[5].select("div[class='mb-img']")
                juJis = uls[5].select("span[class='duration']")
                titles = uls[5].select("p[class='title']")
                counts = uls[5].select("p[class='desc']")
                i = 0
                for (urlTag in urls) {
                    val url = urlTag.attr("href")
                    Log.e("url输出：", url)
                    val img = imgs[i].attr("style").toString().split("(")[1].split(")")[0]
                    Log.e("url输出：", img)
                    val juJi = juJis[i].text()
                    Log.e("url输出：", juJi)
                    val title = titles[i].text()
                    Log.e("url输出：", title)
                    val count = counts[i].text()
                    Log.e("url输出：", count)
                    data5.add(MoverCount(title, img, count, juJi, url))
                    i += 1
                }
                activity?.runOnUiThread {
                    homeCountAdapter5?.notifyDataSetChanged()
                    refresh?.finishRefresh(true)
                }

    }




}