package com.example.yjys

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yjys.utils.ActivityCollector
import com.example.yjys.utils.LoadDiglogList
import com.kaopiz.kprogresshud.KProgressHUD
import java.lang.Exception

open class BaseActivity : AppCompatActivity() {

    companion object{
        /**
        var loading : LodingDiglog? = null
        */

        var context : Context ? = null

        var load : KProgressHUD? =null

        @JvmStatic
        fun closeLoading(activity: Activity){
            activity?.runOnUiThread {
                try {
                    for (it in LoadDiglogList.getList()){
                        if (it.isShowing){
                            it.dismiss()
                        }
                    }
                    LoadDiglogList.remover()
                }catch (e:Exception) {
                    e.printStackTrace()
                }

            }
        }

        @JvmStatic
        fun loading(activity: Activity){
            activity?.runOnUiThread {
                try {
                    load = KProgressHUD.create(activity)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    LoadDiglogList.add(load!!)
                }catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        context = this

        ActivityCollector.addActivity(this)

        Log.d("BaseActivity",javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}