package com.example.yjys.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.yjys.Play
import com.example.yjys.R
import com.example.yjys.entity.MoverCount


class ScannerGridViewAdapter(val context: Context, data1: List<MoverCount>) : BaseAdapter() {

    val data : List<MoverCount> = data1

    override fun getCount(): Int {
       return data.size
    }

    override fun getItem(position: Int): Any {
        return data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View ?= null
        var myHolder:MyHolder ?= null

        if(convertView == null){
            myHolder = MyHolder()
            view = LayoutInflater.from(context).inflate(R.layout.scanner_gridview_adapater,null)
            myHolder.img = view.findViewById(R.id.img)
            myHolder.title = view.findViewById(R.id.stitle)
            myHolder.count = view.findViewById(R.id.count)
            myHolder.juJi = view.findViewById(R.id.juJi)
            myHolder.gitem = view.findViewById(R.id.gitem)
            view.tag = myHolder
        }else{
            view = convertView
            myHolder = view.tag as MyHolder
        }

        if (data.size>0){
            myHolder.title.setText(data.get(position).title)
            Glide.with(context).load(data.get(position).img).into(myHolder.img)
            myHolder.count.setText(data.get(position).count)
            myHolder.juJi.setText(data.get(position).juJi)
            myHolder.gitem.setOnClickListener {
                val intent = Intent(context, Play::class.java)
                intent.putExtra("url",data.get(position).url)
                intent.putExtra("title",data.get(position).title)
                intent.putExtra("img",data.get(position).img)
                intent.putExtra("type",data.get(position).type)
                val activity = context as Activity
                activity.startActivity(intent)
            }
        }
        return view!!
    }

    inner class MyHolder(){
        lateinit var img : ImageView
        lateinit var title : TextView
        lateinit var count : TextView
        lateinit var juJi : TextView
        lateinit var gitem : LinearLayout
    }
}