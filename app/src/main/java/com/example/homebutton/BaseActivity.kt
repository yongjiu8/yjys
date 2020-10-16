package com.example.homebutton

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homebutton.dialog.LodingDiglog
import com.example.homebutton.utils.ActivityCollector
import com.example.homebutton.utils.LoadDiglogList
import com.kaopiz.kprogresshud.KProgressHUD

open class BaseActivity : AppCompatActivity() {
    companion object{
        /*var loading : LodingDiglog? = null*/
        var context : Context ? = null
        var activity :Activity ? = null
        var load : KProgressHUD? = null

        @JvmStatic
        fun closeLoading(){
            activity?.runOnUiThread {
                for (it in LoadDiglogList.getList()){
                    if (it?.isShowing!!){
                        it?.dismiss()
                    }
                }
                LoadDiglogList.remover()
            }
        }

        @JvmStatic
        fun loading(context: Context){
             load = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
            LoadDiglogList.add(load!!)
            /*loading = LodingDiglog.getInstance(context)
            loading?.show()*/
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