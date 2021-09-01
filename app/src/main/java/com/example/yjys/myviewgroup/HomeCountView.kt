package com.example.yjys.myviewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.yjys.R

class HomeCountView(context: Context,attributeSet: AttributeSet) : LinearLayout(context,attributeSet) {

    init {
        LayoutInflater.from(context).inflate(R.layout.home_count_item,this)
    }


}