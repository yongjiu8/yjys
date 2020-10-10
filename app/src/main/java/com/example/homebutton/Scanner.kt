package com.example.homebutton

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.homebutton.adapter.ScannerGridViewAdapter
import com.example.homebutton.entity.MoverCount
import com.example.homebutton.utils.MyCallBack
import com.example.homebutton.utils.Net
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.common_title.*
import org.jsoup.nodes.Document


class Scanner : BaseActivity() {

    val listData = mutableListOf<MoverCount>()
    var mAdapter : ScannerGridViewAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        supportActionBar?.hide()
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
            loading()
            if(scan_edit.text.toString() == ""){
                closeLoading()
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
       Net.get("https://so.360kan.com/index.php?kw=" + scan_edit.text,object : MyCallBack{
           override fun callBack(doc: Document?) {
               if (doc == null){
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
                   closeLoading()
               }

           }
       })

    }
}