package com.example.yjys.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.yjys.BaseActivity
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.security.AccessControlContext

/**
 * 网络请求类
 * */
class Net(){

    companion object{
        fun get(activity: Activity,url:String,callBack: MyCallBack){
            Thread(Runnable {
                var body : Document? = null
                try {
                    BaseActivity.loading(activity)

                    body = Jsoup.connect(url).ignoreContentType(true).timeout(8000).get()
                    callBack.callBack(body)
                }catch (e : Exception){
                    callBack.callError()
                    e.printStackTrace()
                    Log.e("网络请求异常：", e.toString())
                }finally {
                    BaseActivity.closeLoading(activity)
                }

            }).start()
        }

        fun getList(activity: Activity,url:String,pram : Map<String,String>,head:Map<String,String>,ck:Map<String,String>,callBack: NetCallBack){
            Thread(Runnable {
                var body : Connection.Response? = null
                try {
                    BaseActivity.loading(activity)
                    body = Jsoup.connect(url).ignoreContentType(true).data(pram).headers(head).cookies(ck).timeout(8000).method(Connection.Method.GET).execute()
                    callBack.callBack(body)
                }catch (e : Exception){
                    callBack.callError()
                    Log.e("网络请求异常：", e.toString())
                }finally {
                    BaseActivity.closeLoading(activity)
                }

            }).start()
        }

        fun getPlay(activity: Activity,url:String,callBack: NetCallBack){
            Thread(Runnable {
                var body : Connection.Response? = null
                try {
                    BaseActivity.loading(activity)
                    body = Jsoup.connect(url).ignoreContentType(true).timeout(8000).method(Connection.Method.GET).execute()
                    callBack.callBack(body)
                }catch (e : Exception){
                    callBack.callError()
                    Log.e("网络请求异常：", e.toString())
                }finally {
                    BaseActivity.closeLoading(activity)
                }

            }).start()
        }
    }

}