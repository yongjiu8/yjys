package com.example.homebutton.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homebutton.R
import com.example.homebutton.adapter.FaXianGridViewAdapter
import com.example.homebutton.entity.FaXian
import com.example.homebutton.faxianui.MusicActivity
import com.example.homebutton.myview.MyFragment

class FaxianFragment() : Fragment() {

    var inflate : View? = null
    val data = arrayListOf<FaXian>(FaXian("全国天气",R.drawable.tianqi),FaXian("在线音乐",
        R.drawable.music))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(inflate == null){
            inflate = inflater.inflate(R.layout.faxian_fragment, container, false)
        }

        val title = inflate?.findViewById<TextView>(R.id.comTitle)
        title?.setText("发现")
        val gride = inflate?.findViewById<GridView>(R.id.gride)
        gride?.adapter=FaXianGridViewAdapter(activity!!,data)
        gride?.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0 -> {
                    Toast.makeText(context, "开发中...", Toast.LENGTH_SHORT).show()
                }

                1 -> {
                    startActivity(Intent(context,MusicActivity::class.java))
                }
            }
        }


        return inflate
    }
}