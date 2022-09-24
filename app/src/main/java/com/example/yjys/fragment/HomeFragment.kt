package com.example.yjys.fragment

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
import com.example.yjys.Play
import com.example.yjys.R
import com.example.yjys.Scanner
import com.example.yjys.adapter.HomeCountAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.HomeBannerDto
import com.example.yjys.entity.HomeDongManDto
import com.example.yjys.entity.HomeMoverDto
import com.example.yjys.entity.HomeShaoErDto
import com.example.yjys.entity.HomeTvDto
import com.example.yjys.entity.HomeZongYiDto
import com.example.yjys.entity.MoverCount
import com.example.yjys.entity.TvPlayDto
import com.example.yjys.myviewgroup.HomeCountView
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.Net
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import org.jsoup.nodes.Document

class HomeFragment() : Fragment() {

    val bannerDataTitle = mutableListOf<String>()
    val bannerDataImg = mutableListOf<String>()
    val bannerDataUrl = mutableListOf<String>()
    val bannerDataCat = mutableListOf<Int>()

    var banner: Banner? = null
    var tvplay: HomeCountView? = null
    var film: HomeCountView? = null
    var variety: HomeCountView? = null
    var juvenile: HomeCountView? = null
    var comic: HomeCountView? = null
    var refresh: SmartRefreshLayout? = null
    var botScanner: LinearLayout? = null


    val data1 = mutableListOf<MoverCount>()
    val data2 = mutableListOf<MoverCount>()
    val data3 = mutableListOf<MoverCount>()
    val data4 = mutableListOf<MoverCount>()
    val data5 = mutableListOf<MoverCount>()

    var homeCountAdapter1: HomeCountAdapter? = null
    var homeCountAdapter2: HomeCountAdapter? = null
    var homeCountAdapter3: HomeCountAdapter? = null
    var homeCountAdapter4: HomeCountAdapter? = null
    var homeCountAdapter5: HomeCountAdapter? = null

    var inflate: View? = null

    //是否可见
    private var isVisibleToUser = false

    //是否初始化完成
    var isInit = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (isVisibleToUser && isInit) {
            initBanner()
            initCountData()
            initdata()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inflate == null) {
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
            val intent = Intent(context, Play::class.java)
            intent.putExtra("url", bannerDataUrl[it])
            intent.putExtra("img", bannerDataImg[it])
            intent.putExtra("title", bannerDataTitle[it])
            intent.putExtra("type", bannerDataCat[it])
            startActivity(intent)
        }

        //经典样式
        refresh?.setRefreshHeader(ClassicsHeader(activity))

        this.isInit = true

        refresh?.setOnRefreshListener {
            banner?.stopAutoPlay()

            if (isVisibleToUser && isInit) {
                initBanner()
                initCountData()
                initdata()
            }
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


    fun showNetworkOrDataError() {
        activity?.runOnUiThread {
            AestheticDialog.Builder(requireActivity(), DialogStyle.FLASH, DialogType.ERROR)
                .setTitle("提示")
                .setMessage("加载失败！请检查您的网络")
                .show()
        }
    }

    fun showMessage(message: String) {
        activity?.runOnUiThread {
            AestheticDialog.Builder(requireActivity(), DialogStyle.TOASTER, DialogType.INFO)
                .setTitle("提示")
                .setMessage(message)
                .show()
        }
    }


    fun initBanner() {
        Net.get(requireActivity(), AppConfig.homeBannerUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val data = Gson().fromJson(doc?.body()?.text(), HomeBannerDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }

                bannerDataUrl.clear()
                bannerDataImg.clear()
                bannerDataTitle.clear()
                bannerDataCat.clear()

                for (it in data.data.lists) {
                    bannerDataTitle.add(it.title)
                    bannerDataImg.add(it.picLists[0].url)
                    bannerDataUrl.add(it.entId)
                    bannerDataCat.add(it.cat.toInt())
                }

                banner?.setImages(bannerDataImg)
                banner?.setBannerTitles(bannerDataTitle)

                activity?.runOnUiThread {
                    //开始运行
                    banner?.start()
                }
                //initdata(doc)
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })


    }


    private class MyLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }

    fun initCountData() {
        var ctitle = tvplay?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("电视剧")
        var img = tvplay?.findViewById<ImageView>(R.id.img)
        img?.setImageResource(R.drawable.tv)
        tvplay?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked =
                true
        }
        var gridview = tvplay?.findViewById<GridView>(R.id.gridview)
        var activity = this.activity
        data1.clear()
        homeCountAdapter1 = HomeCountAdapter(requireActivity(), data1)
        gridview?.adapter = homeCountAdapter1

        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data1[position].url, Toast.LENGTH_SHORT).show()
        }


        ctitle = film?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("电影")
        var img_film = film?.findViewById<ImageView>(R.id.img)
        img_film?.setImageResource(R.drawable.mover_home)
        film?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked =
                true
        }
        gridview = film?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data2.clear()
        homeCountAdapter2 = HomeCountAdapter(requireActivity(), data2)
        gridview?.adapter = homeCountAdapter2
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data2[position].url, Toast.LENGTH_SHORT).show()
        }

        ctitle = variety?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("综艺")
        var img_variety = variety?.findViewById<ImageView>(R.id.img)
        img_variety?.setImageResource(R.drawable.zongyi)
        variety?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked =
                true
        }
        gridview = variety?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data3.clear()
        homeCountAdapter3 = HomeCountAdapter(requireActivity(), data3)
        gridview?.adapter = homeCountAdapter3
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data3[position].url, Toast.LENGTH_SHORT).show()
        }

        ctitle = juvenile?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("少儿")
        var img_juvenile = juvenile?.findViewById<ImageView>(R.id.img)
        img_juvenile?.setImageResource(R.drawable.saoer)
        juvenile?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked =
                true
        }
        gridview = juvenile?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data4.clear()
        homeCountAdapter4 = HomeCountAdapter(requireActivity(), data4)
        gridview?.adapter = homeCountAdapter4
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data4[position].url, Toast.LENGTH_SHORT).show()
        }

        ctitle = comic?.findViewById<TextView>(R.id.ctitle)
        ctitle?.setText("动漫")
        var img_comic = comic?.findViewById<ImageView>(R.id.img)
        img_comic?.setImageResource(R.drawable.dongman)
        comic?.findViewById<LinearLayout>(R.id.more)?.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.page)?.currentItem = 1
            activity?.findViewById<BottomNavigationView>(R.id.but_home)?.menu?.getItem(1)?.isChecked =
                true
        }
        gridview = comic?.findViewById<GridView>(R.id.gridview)
        activity = this.activity
        data5.clear()
        homeCountAdapter5 = HomeCountAdapter(requireActivity(), data5)
        gridview?.adapter = homeCountAdapter5
        gridview?.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(activity, data5[position].url, Toast.LENGTH_SHORT).show()
        }

    }

    fun pushJuji(up: Int, max :Int): String {
        if (up == max) {
            return "$max 集全"
        } else {
            return "更新至$up 集"
        }
    }

    fun initdata() {

        data1.clear()
        data2.clear()
        data3.clear()
        data4.clear()
        data5.clear()

        Net.get(requireActivity(), AppConfig.homeTvUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val data = Gson().fromJson(doc?.body()?.text(), HomeTvDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }

                for (it in data.data.lists) {
                    data1.add(MoverCount(it.title, it.picLists[0].url, it.comment, pushJuji(it.upinfo, it.total), it.entId, it.cat.toInt()))
                }
                activity?.runOnUiThread {
                    homeCountAdapter1?.notifyDataSetChanged()
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

        Net.get(requireActivity(), AppConfig.homeMoverUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val data = Gson().fromJson(doc?.body()?.text(), HomeMoverDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }

                for (it in data.data.lists) {
                    data2.add(MoverCount(it.title, it.picLists[0].url, it.comment, pushJuji(it.upinfo, it.total), it.entId, it.cat.toInt()))
                }
                activity?.runOnUiThread {
                    homeCountAdapter2?.notifyDataSetChanged()
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

        Net.get(requireActivity(), AppConfig.homeZongYiUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val data = Gson().fromJson(doc?.body()?.text(), HomeZongYiDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }

                for (it in data.data.lists) {
                    data3.add(MoverCount(it.title, it.picLists[0].url, it.comment, pushJuji(it.upinfo, it.total), it.entId, it.cat.toInt()))
                }
                activity?.runOnUiThread {
                    homeCountAdapter3?.notifyDataSetChanged()
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

        Net.get(requireActivity(), AppConfig.homeShaoErUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val data = Gson().fromJson(doc?.body()?.text(), HomeShaoErDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }

                for (it in data.data.lists) {
                    data4.add(MoverCount(it.title, it.picLists[0].url, it.comment, pushJuji(it.upinfo, it.total), it.entId, it.cat.toInt()))
                }
                activity?.runOnUiThread {
                    homeCountAdapter4?.notifyDataSetChanged()
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })

        Net.get(requireActivity(), AppConfig.homeDongManUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val data = Gson().fromJson(doc?.body()?.text(), HomeDongManDto::class.java)
                if (data.errno != 0) {
                    showNetworkOrDataError()
                    return
                } else if (data.data == null) {
                    showMessage("加载数据为空哦！")
                    return
                }

                for (it in data.data.lists) {
                    data5.add(MoverCount(it.title, it.picLists[0].url, it.comment, pushJuji(it.upinfo, it.total), it.entId, it.cat.toInt()))
                }
                activity?.runOnUiThread {
                    homeCountAdapter5?.notifyDataSetChanged()
                }
            }

            override fun callError() {
                showNetworkOrDataError()
            }
        })


        activity?.runOnUiThread {
            refresh?.finishRefresh(true)
        }

    }


}