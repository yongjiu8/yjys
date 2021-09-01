package com.example.yjys.myviewgroup

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yjys.R

class CommonTitle(context: Context,attributeSet: AttributeSet) :LinearLayout(context,attributeSet) {

    var title : TextView? = null
    var setter : ImageView? = null
    var imgback : ImageView? = null

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.common_title, this)
        title = inflate.findViewById(R.id.comTitle)
        imgback = inflate.findViewById(R.id.imgback)
        setter = inflate.findViewById(R.id.setter)
        imgback?.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }
    }


}