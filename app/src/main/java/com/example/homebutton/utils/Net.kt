package com.example.homebutton.utils

import android.util.Log
import com.example.homebutton.BaseActivity
import com.example.homebutton.application.Application
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
/**
 * 网络请求类
 * */
class Net(){

    companion object{
        fun get(url:String,callBack: MyCallBack){
            Thread(Runnable {
                var body : Document? = null
                try {
                    body = Jsoup.connect(url).ignoreContentType(true).timeout(10000).get()
                }catch (e : Exception){
                    Log.e("网络请求异常：", e.stackTraceToString())
                }finally {
                    callBack.callBack(body)
                    BaseActivity.closeLoading()
                }

            }).start()
        }
    }

}