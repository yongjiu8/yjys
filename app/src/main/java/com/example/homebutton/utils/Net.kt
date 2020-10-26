package com.example.homebutton.utils

import android.util.Log
import com.example.homebutton.BaseActivity
import com.example.homebutton.application.Application
import org.jsoup.Connection
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
                    body = Jsoup.connect(url).ignoreContentType(true).timeout(8000).get()
                    callBack.callBack(body)
                }catch (e : Exception){
                    callBack.callError()
                    Log.e("网络请求异常：", e.stackTraceToString())
                }finally {

                }

            }).start()
        }

        fun getList(url:String,pram : Map<String,String>,head:Map<String,String>,ck:Map<String,String>,callBack: NetCallBack){
            Thread(Runnable {
                var body : Connection.Response? = null
                try {
                    body = Jsoup.connect(url).ignoreContentType(true).data(pram).headers(head).cookies(ck).timeout(8000).method(Connection.Method.GET).execute()
                    callBack.callBack(body)
                }catch (e : Exception){
                    callBack.callError()
                    Log.e("网络请求异常：", e.stackTraceToString())
                }finally {

                }

            }).start()
        }

        fun getPlay(url:String,callBack: NetCallBack){
            Thread(Runnable {
                var body : Connection.Response? = null
                try {
                    body = Jsoup.connect(url).ignoreContentType(true).timeout(8000).method(Connection.Method.GET).execute()
                    callBack.callBack(body)
                }catch (e : Exception){
                    callBack.callError()
                    Log.e("网络请求异常：", e.stackTraceToString())
                }finally {

                }

            }).start()
        }
    }

}