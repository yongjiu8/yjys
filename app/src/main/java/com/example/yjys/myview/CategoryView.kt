package com.example.yjys.myview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.yjys.R


class CategoryView : LinearLayout,RadioGroup.OnCheckedChangeListener {
   var inflater : LayoutInflater? = null
    var mListener: OnClickCategoryListener? = null

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        inflater = LayoutInflater.from(context)
    }
    constructor(context: Context) : super(context)

    var radioHome : RadioGroup? =null

    var view : View? = null

    fun add(map: Map<String,String>){
        if (map.isNotEmpty()){
            if (view != null){
                removeView(view)
            }
            view = inflater?.inflate(R.layout.category_container, null)
            addView(view)
            radioHome = view?.findViewById<RadioGroup>(R.id.container)
            for (it in map){
                if (it.key == "全部"){
                    val newRadioButton = newRadioButton(it.key)
                    radioHome?.addView(newRadioButton)
                    radioHome?.check(newRadioButton?.id!!)
                }else{
                    radioHome?.addView(newRadioButton(it.key))
                }

            }

            radioHome?.setOnCheckedChangeListener(this)

        }
    }

    fun removeAll(){
        removeView(view)
    }

    /**创建RadioButton */
    fun newRadioButton(text: String): RadioButton? {
        val button = RadioButton(context)
        val params = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.WRAP_CONTENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        )

        //设置内外边距
        params.leftMargin = 6
        params.rightMargin = 6
        button.layoutParams = params
        button.setPadding(4, 0, 4, 0)

        //设置背景
        button.setBackgroundResource(R.drawable.selector_category_bg)
        //去掉左侧默认的圆点
        button.setButtonDrawable(android.R.color.transparent)
        //设置不同状态下文字颜色，通过ColorStateList，对应的selector放在res/color文件目录中，否则没有效果
        button.setTextColor(resources.getColorStateList(R.color.selector_category_text))
        button.gravity = Gravity.CENTER
        button.textSize = 15f
        button.text = text
        return button
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (mListener != null){
            mListener?.click(group,checkedId)
        }
    }

    interface OnClickCategoryListener {
        /**点击事件发生 */
        fun click(group: RadioGroup?, checkedId: Int)
    }

    /**指定监听器 */
    fun setOnClickCategoryListener(l: OnClickCategoryListener) {
        mListener = l
    }
}