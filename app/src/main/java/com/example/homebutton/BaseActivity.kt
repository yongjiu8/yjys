package com.example.homebutton

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homebutton.dialog.LodingDiglog
import com.example.homebutton.utils.ActivityCollector

open class BaseActivity : AppCompatActivity() {
    companion object{
        var loading : LodingDiglog? = null
        var context : Context ? = null
        var activity :Activity ? = null

        fun closeLoading(){
            activity?.runOnUiThread {
                if (loading?.isShowing!!){
                    loading?.dismiss()
                }
            }
        }

        fun loading(){
            loading = LodingDiglog.getInstance(context)
            loading?.show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        context = this
        activity = this as Activity

        ActivityCollector.addActivity(this)

        Log.d("BaseActivity",javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}