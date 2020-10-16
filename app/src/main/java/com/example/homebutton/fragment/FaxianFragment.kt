package com.example.homebutton.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.homebutton.R
import com.example.homebutton.adapter.FaXianGridViewAdapter
import com.example.homebutton.entity.FaXian
import com.example.homebutton.myview.MyFragment

class FaxianFragment() : Fragment() {

    var inflate : View? = null
    val data = arrayListOf<FaXian>(FaXian("天气",R.drawable.tianqi),FaXian("腾讯",
        R.drawable.shouchang_blue),FaXian("爱奇艺",R.drawable.fankui_blue),
        FaXian("芒果TV",R.drawable.lishi_blue))

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

                }
            }
        }


        return inflate
    }
}