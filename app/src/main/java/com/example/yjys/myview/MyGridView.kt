package com.example.yjys.myview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

class MyGridView(context: Context, attributes: AttributeSet) : GridView(context, attributes){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            Int.MAX_VALUE shr 2,
            MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, makeMeasureSpec)
    }
}