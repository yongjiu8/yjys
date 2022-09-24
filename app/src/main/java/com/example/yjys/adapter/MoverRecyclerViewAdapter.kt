package com.example.yjys.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yjys.Play
import com.example.yjys.R
import com.example.yjys.entity.MoverCount

class MoverRecyclerViewAdapter(val context: Context,data: List<MoverCount>) :
    RecyclerView.Adapter<MoverRecyclerViewAdapter.MyHolder>() {

    val dataList=data

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
         var img : ImageView = view.findViewById(R.id.img)
         var title : TextView = view.findViewById(R.id.stitle)
         var count : TextView = view.findViewById(R.id.count)
         var juJi : TextView = view.findViewById(R.id.juJi)
        var homeItem : LinearLayout = view.findViewById(R.id.home_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflate = LayoutInflater.from(context)
            .inflate(R.layout.mover_recyclerview_adapater, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.setText(dataList.get(position).title)
        holder.juJi.setText(dataList.get(position).juJi)
        holder.count.setText(dataList.get(position).count)
        Glide.with(context).load(dataList.get(position).img).into(holder.img)

        holder.homeItem.setOnClickListener {
            val intent = Intent(context,Play::class.java)
            intent.putExtra("url",dataList.get(position).url)
            intent.putExtra("title",dataList.get(position).title)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("type",dataList.get(position).type)
            val activity = context as Activity
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}