package com.example.yjys.faxianui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.androidkun.xtablayout.XTabLayout
import com.example.yjys.BaseActivity
import com.example.yjys.R
import com.example.yjys.adapter.MusicListAdapter
import com.example.yjys.entity.MusicList
import com.example.yjys.utils.Net
import com.example.yjys.utils.NetCallBack
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.common_title.*
import org.jsoup.Connection

class MusicActivity : BaseActivity() {

    val names = mutableListOf<String>("酷我飙升榜","酷我新歌榜","酷我热歌榜","抖音热歌榜","会员畅听榜","酷我铃声榜",
        "流行趋势榜","国风音乐榜","DJ嗨歌榜","网红新歌榜","经典怀旧榜","酷我热评榜","极品电音榜","最强翻唱榜","爆笑相声榜","ACG新歌榜","影视金曲榜")
    val datas = mutableListOf<String>("93","17","16","15","145","292","187","278","176","153","26","284","242","185","291","290","64")
    var musicList = mutableListOf<MusicList.DataBean.MusicListBean>()
    var musicListAdapter:MusicListAdapter? =null

    var page = 1
    var dataFlag = 0
    var sumPage = 1

    var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        supportActionBar?.hide()

        activity = this

        imgback.setOnClickListener{
            finish()
        }

        for (it in names){
            tabSelect.addTab(tabSelect.newTab().setText(it))
        }

        val linearLayoutManager = LinearLayoutManager(context)
        list.layoutManager=linearLayoutManager
        musicListAdapter = MusicListAdapter(this, musicList)
        list.adapter=musicListAdapter

        tabSelect.getTabAt(0)?.select()

        comTitle.setText("在线音乐")

        tabSelect.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                page = 1
                dataFlag = tab?.position!!
                musicList.clear()
                ref.setNoMoreData(false)
                initData(datas[tab?.position!!])
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {

            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {

            }

        })

        setter.setImageResource(R.drawable.scanner)
        setter.setOnClickListener {
            val intent = Intent(context, MusicScanerActivity::class.java)
            startActivity(intent)
        }


        //初始化数据
        initData(datas[0])

        ref?.setRefreshHeader(ClassicsHeader(activity))
        ref?.setRefreshFooter(
            ClassicsFooter(activity)
                .setSpinnerStyle(SpinnerStyle.Scale))

        ref?.setOnRefreshListener {
            musicList.clear()
            page=1
            ref.setNoMoreData(false)
            initData(datas[dataFlag])
        }

        ref?.setOnLoadMoreListener {
            page++
            if (page<= sumPage){
                initData(datas[dataFlag])
            }else{
                ref.setNoMoreData(true)
            }

        }


    }


    fun initData(bangId:String){
        val parm = mutableMapOf<String,String>()
        val head = mutableMapOf<String,String>()
        val ck = mutableMapOf<String,String>()

        val sharedPreferences = getSharedPreferences("get", Context.MODE_PRIVATE)
        val ckValue = sharedPreferences.getString("ck", "780C0026FMD")
        parm.put("bangId",bangId)
        head.put("csrf",ckValue!!)
        ck.put("kw_token",ckValue!!)
        Net.getList(activity!!,"http://www.kuwo.cn/api/www/bang/bang/musicList?pn="+page+"&rn=30&httpsStatus=1&reqId=4c9685a0-12ac-11eb-b95d-ddb143e88229",parm,head,ck,object : NetCallBack{
            override fun callBack(doc: Connection.Response?) {
                val data = doc?.body().toString()
                if ("".equals(data)){
                    return
                }

                //更新cookie
                val edit = getSharedPreferences("get", Context.MODE_PRIVATE).edit()
                val cookie = doc?.cookie("kw_token")
                edit.putString("ck",cookie)
                edit.commit()

                Log.e("音乐输出",data)
                val musicData = Gson().fromJson(data, MusicList::class.java)
                val parseObject = JSON.parseObject(data)
                val jsonObject = parseObject.getJSONObject("data")
                val num = jsonObject.getInteger("num")
                sumPage = if (num % 30 == 0) num / 30 else (num / 30) + 1

                musicList.addAll(musicData.data.musicList)

                activity?.runOnUiThread {
                    musicListAdapter?.notifyDataSetChanged()
                    ref?.finishRefresh(true)
                    ref?.finishLoadMore(true)
                }

            }

            override fun callError() {
                activity?.runOnUiThread {
                    ref?.finishRefresh(false)
                    ref?.finishLoadMore(false)
                    AestheticDialog.Builder(
                        activity!! ,
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