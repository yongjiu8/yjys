package com.example.homebutton.myviewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.homebutton.R

class HomeCountView(context: Context,attributeSet: AttributeSet) : LinearLayout(context,attributeSet) {

    init {
        LayoutInflater.from(context).inflate(R.layout.home_count_item,this)
    }


}