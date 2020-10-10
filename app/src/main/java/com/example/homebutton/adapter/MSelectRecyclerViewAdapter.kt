package com.example.homebutton.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homebutton.LoginActivity
import com.example.homebutton.Play
import com.example.homebutton.R
import com.example.homebutton.application.Application
import com.example.homebutton.entity.Drama
import com.example.homebutton.entity.MoverCount
import com.example.homebutton.myview.X5WebView
import kotlinx.android.synthetic.main.activity_play.*

class MSelectRecyclerViewAdapter(val context: Context, data: List<String>) :
    RecyclerView.Adapter<MSelectRecyclerViewAdapter.MyHolder>() {

    val dataList=data

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
         var title : TextView = view.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var inflate:View? = null
            inflate = LayoutInflater.from(context)
                .inflate(R.layout.mselect_recyclerview_adapater, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.setText(dataList.get(position))
        holder.title.setOnClickListener {
            when(position){
                0 -> {
                    Toast.makeText(context, "开发者QQ：2584059921", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Application.mTencent?.logout(context)
                    val edit = context?.getSharedPreferences("token", 0)?.edit()
                    edit?.putString("key", "")
                    edit?.commit()
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}