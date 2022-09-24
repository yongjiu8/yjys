package com.example.yjys

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.Toast
import com.example.yjys.adapter.ScannerGridViewAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.HomeBannerDto
import com.example.yjys.entity.MoverCount
import com.example.yjys.entity.SearchDto
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.Net
import com.google.gson.Gson
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.common_title.*
import org.jsoup.nodes.Document


class Scanner : BaseActivity() {

    val listData = mutableListOf<MoverCount>()
    var mAdapter : ScannerGridViewAdapter ? = null

    var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        supportActionBar?.hide()

        activity = this

        comTitle.setText("搜索")
        val edit = getSharedPreferences("scName", 0)
        val string = edit.getString("name", null)
        if (string != null){
            scan_edit.setText(string)
        }
        imgback.setOnClickListener {
            finish()
        }
        scan_edit.requestFocus()
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        scan_bot.setOnClickListener {
            if(scan_edit.text.toString() == ""){
                Toast.makeText(baseContext, "请输入搜索内容", Toast.LENGTH_SHORT).show()
            }else{
                val edit = getSharedPreferences("scName", 0).edit()
                edit.putString("name",scan_edit.text.toString())
                edit.commit()
                initData()
            }
        }

        mAdapter= ScannerGridViewAdapter(this,listData)
        glist.adapter = mAdapter


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

   fun initData(){
       listData.clear()
       Net.get(activity!!,AppConfig.searchUrl + "&kw=" + Uri.encode(scan_edit.text.toString()), object : MyCallBack{
           override fun callBack(doc: Document?) {
               var data: SearchDto?
               try {
                   data = Gson().fromJson(doc?.body()?.text(), SearchDto::class.java)
               }catch (e: java.lang.Exception){
                   showMessage("数据跑外太空去了！")
                   return
               }

               if (data.code != 0) {
                   showNetworkOrDataError()
                   return
               } else if (data.data == null) {
                   showMessage("加载数据为空哦！")
                   return
               }


               for (it in data.data.longData.rows){
                   listData.add(MoverCount(it.titleTxt,it.cover,it.description,it.catName,it.enId, it.catId.toInt()))
               }

               activity?.runOnUiThread {
                   mAdapter?.notifyDataSetChanged()
               }

           }

           override fun callError() {
               showNetworkOrDataError()
           }
       })

    }
}