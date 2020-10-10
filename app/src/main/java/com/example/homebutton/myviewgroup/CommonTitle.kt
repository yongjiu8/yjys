package com.example.homebutton.myviewgroup

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.homebutton.R

class CommonTitle(context: Context,attributeSet: AttributeSet) :LinearLayout(context,attributeSet) {

    var title : TextView? = null
    var score : TextView? = null
    var imgback : ImageView? = null

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.common_title, this)
        title = inflate.findViewById(R.id.comTitle)
        imgback = inflate.findViewById(R.id.imgback)
        score = inflate.findViewById(R.id.score)
        imgback?.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }
    }

    fun setTitle(title:String){
        this.title?.setText(title)
    }

    fun getTitle() : String {
        return this.title?.text.toString()
    }

    fun setScore(score:String){
        this.score?.setText(score)
    }

    fun getScore() : String {
        return this.score?.text.toString()
    }


}