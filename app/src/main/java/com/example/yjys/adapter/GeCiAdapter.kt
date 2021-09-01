package com.example.yjys.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yjys.R
import com.example.yjys.entity.GeCi

class GeCiAdapter(val context: Context, data: List<GeCi>) :
    RecyclerView.Adapter<GeCiAdapter.MyHolder>() {

    val dataList=data

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
         var text : TextView = view.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var inflate:View? = null
            if (viewType == 0){
                inflate = LayoutInflater.from(context)
                    .inflate(R.layout.ge_ci_active_adapater, parent, false)
            }else{
                inflate = LayoutInflater.from(context)
                    .inflate(R.layout.ge_ci_default_adapater, parent, false)
            }
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.text.text = dataList[position].text
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (dataList[position].flag){
            return 0
        }else{
            return 1
        }
    }
}