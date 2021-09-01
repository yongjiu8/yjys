package com.example.yjys.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yjys.R
import com.example.yjys.entity.MusicPlayList
import com.example.yjys.utils.RecycleViewItemClick

class MusicPlayListAdapter(val context: Context, data: List<MusicPlayList>) :
    RecyclerView.Adapter<MusicPlayListAdapter.MyHolder>() {

    val dataList=data
    var recycleViewItemClick : RecycleViewItemClick ? =null

    fun setOnItemClick(recycleViewItemClick: RecycleViewItemClick){
        this.recycleViewItemClick = recycleViewItemClick
    }


    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
         var text : TextView = view.findViewById(R.id.text)
        var home : LinearLayout = view.findViewById(R.id.home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var inflate = LayoutInflater.from(context)
                    .inflate(R.layout.musicplay_list_default_adapater, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.text.text = dataList[position].text
        holder.home.setOnClickListener {
                recycleViewItemClick?.onClick(position,dataList)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}