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
import com.example.yjys.R
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.MusicList
import com.example.yjys.entity.MusicScannerList
import com.example.yjys.faxianui.MusicPlayActivity

class MusicScannerListAdapter(val context: Context, data: List<MusicScannerList.DataBean.ListBean>) :
    RecyclerView.Adapter<MusicScannerListAdapter.MyHolder>() {

    val dataList=data

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var number : TextView = view.findViewById(R.id.number)
        var img : ImageView = view.findViewById(R.id.img)
        var title : TextView = view.findViewById(R.id.title)
        var geshou : TextView = view.findViewById(R.id.geshou)
        val home :LinearLayout = view.findViewById(R.id.home)
        //var zhuanji : TextView = view.findViewById(R.id.zhuanji)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var inflate:View? = null
        inflate = LayoutInflater.from(context)
                    .inflate(R.layout.musicllist_adapater, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.number.setText((position+1).toString())
        holder.title.setText(dataList.get(position).name)
        Glide.with(context).load(dataList.get(position).pic).into(holder.img)
        holder.geshou.setText(dataList.get(position).artist)
        //holder.zhuanji.setText(dataList.get(position).album)

        holder.home.setOnClickListener {
            val activity = context as Activity
            val intent = Intent(context, MusicPlayActivity::class.java)
            intent.putExtra("rid",dataList.get(position).rid.toString())
            AppConfig.MusicDataList.clear()
            val arrayList = ArrayList<MusicList.DataBean.MusicListBean>()
            for (it in dataList){
                val musicListBean = MusicList.DataBean.MusicListBean()
                musicListBean.name=it.name
                musicListBean.pic=it.pic
                musicListBean.albumpic=it.albumpic
                musicListBean.artist=it.artist
                musicListBean.rid=it.rid
                arrayList.add(musicListBean)
            }
            AppConfig.MusicDataList.addAll(arrayList)
            activity.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}