package com.example.yjys.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yjys.Play
import com.example.yjys.R
import com.example.yjys.entity.Drama
import com.example.yjys.myview.X5WebView
import kotlinx.android.synthetic.main.activity_play.*

class DramaRecyclerViewAdapter(val context: Context, data: List<Drama>,val isPlus : Boolean) :
    RecyclerView.Adapter<DramaRecyclerViewAdapter.MyHolder>() {

    val dataList=data

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
         var title : TextView = view.findViewById(R.id.dtitle)
        var drama_item : LinearLayout = view.findViewById(R.id.drama_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var inflate:View? = null
        if (isPlus){
            if (viewType == 0){
                inflate = LayoutInflater.from(context)
                    .inflate(R.layout.drama_selected_recyclerview_adapater_plus, parent, false)
            }else{
                inflate = LayoutInflater.from(context)
                    .inflate(R.layout.drama_recyclerview_adapater_plus, parent, false)
            }

        }else{
            if (viewType == 0){
                inflate = LayoutInflater.from(context)
                    .inflate(R.layout.drama_selected_recyclerview_adapater, parent, false)
            }else {
                inflate = LayoutInflater.from(context)
                    .inflate(R.layout.drama_recyclerview_adapater, parent, false)
            }
        }

        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.setText(dataList.get(position).title)
        holder.drama_item.setOnClickListener {
            val activity = context as Activity
            val web = activity.findViewById<X5WebView>(R.id.web)

            for (it in dataList){
                it.isSelected = false
            }

            dataList[position].isSelected = true

            notifyDataSetChanged()

            Play.nowDrama = dataList.get(position).url
            web.loadUrl(Play.nowLine + Play.nowDrama)

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (dataList[position].isSelected){
            return 0
        }else{
            return 1
        }
    }
}