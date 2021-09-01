package com.example.yjys.faxianui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yjys.BaseActivity
import com.example.yjys.R
import com.example.yjys.adapter.MusicScannerListAdapter
import com.example.yjys.entity.MusicScannerList
import com.example.yjys.utils.Net
import com.example.yjys.utils.NetCallBack
import com.google.gson.Gson
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.activity_music_scaner.*
import kotlinx.android.synthetic.main.activity_scanner.scan_bot
import kotlinx.android.synthetic.main.activity_scanner.scan_edit
import kotlinx.android.synthetic.main.common_title.*
import org.jsoup.Connection
import java.net.URLEncoder

class MusicScanerActivity : AppCompatActivity() {

    val listData = mutableListOf<MusicScannerList.DataBean.ListBean>()
    var mAdapter : MusicScannerListAdapter? = null

    var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_scaner)
        supportActionBar?.hide()

        activity = this
        val linearLayoutManager = LinearLayoutManager(this)
        mAdapter= MusicScannerListAdapter(this,listData)
        mlist.layoutManager = linearLayoutManager
        mlist.adapter = mAdapter

        //加载历史数据
        val edit = getSharedPreferences("musicName", 0)
        val string = edit.getString("name", null)
        if (string != null){
            scan_edit.setText(string)
        }

        //获得焦点
        scan_edit.requestFocus()
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        initClick()
    }


    fun initClick(){
        imgback.setOnClickListener {
            finish()
        }
        comTitle.text = "音乐搜索"

        scan_bot.setOnClickListener {
            if(scan_edit.text.toString() == ""){
                Toast.makeText(baseContext, "请输入搜索内容", Toast.LENGTH_SHORT).show()
            }else{
                val edit = getSharedPreferences("musicName", 0).edit()
                edit.putString("name",scan_edit.text.toString())
                edit.commit()
                initData()
            }
        }

    }

    private fun initData() {
        listData.clear()

        val parm = mutableMapOf<String,String>()
        val head = mutableMapOf<String,String>()
        val ck = mutableMapOf<String,String>()

        val sharedPreferences = getSharedPreferences("get", Context.MODE_PRIVATE)
        val ckValue = sharedPreferences.getString("ck", "780C0026FMD")
        head.put("csrf",ckValue!!)
        head.put("Referer","http://www.kuwo.cn/search/list?key="+URLEncoder.encode(scan_edit.text.toString(),"UTF-8"))
        ck.put("kw_token",ckValue!!)
        Net.getList(activity!!,"http://www.kuwo.cn/api/www/search/searchMusicBykeyWord?key="+URLEncoder.encode(scan_edit.text.toString(),"UTF-8")+"&pn=1&rn=60&httpsStatus=1&reqId=e74cd020-35d1-11eb-8b47-292a16e43b4a",parm,head,ck,object :
            NetCallBack {
            override fun callBack(doc: Connection.Response?) {
                val data = doc?.body().toString()
                if ("".equals(data)){
                    ref?.finishRefresh(false)
                    ref?.finishLoadMore(false)
                    AestheticDialog.Builder(
                            activity!!,
                        DialogStyle.FLASH,
                        DialogType.ERROR
                    )
                        .setTitle("提示")
                        .setMessage("加载失败！请检查您的网络")
                        .show()
                    return
                }

                //更新cookie
                val edit = getSharedPreferences("get", Context.MODE_PRIVATE).edit()
                val cookie = doc?.cookie("kw_token")
                edit.putString("ck",cookie)
                edit.commit()

                Log.e("音乐输出",data)
                val musicData = Gson().fromJson(data, MusicScannerList::class.java)
                listData.addAll(musicData.data.list)
                activity?.runOnUiThread {
                    mAdapter?.notifyDataSetChanged()
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