package com.example.yjys

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.yjys.adapter.ScannerGridViewAdapter
import com.example.yjys.entity.MoverCount
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.Net
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

   fun initData(){
       listData.clear()
       Net.get(activity!!,"https://so.360kan.com/index.php?kw=" + scan_edit.text,object : MyCallBack{
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

               val select = doc.select(".m-mainpic")
               for (it in select){
                   val title = it.getElementsByTag("a")[0].attr("title")
                   val url = it.getElementsByTag("a")[0].attr("href")
                   val img = it.getElementsByTag("img")[0].attr("src")
                   listData.add(MoverCount(title,img,"","",url))
               }

               activity?.runOnUiThread {
                   mAdapter?.notifyDataSetChanged()
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
}