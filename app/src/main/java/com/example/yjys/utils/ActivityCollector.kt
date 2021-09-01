package com.example.yjys.utils

import android.app.Activity

object ActivityCollector {
    val activitys = ArrayList<Activity>()

    fun addActivity(activity: Activity){
        activitys.add(activity)
    }

    fun removeActivity(activity: Activity){
        activitys.remove(activity)
    }

    fun finishAll(){
        for (it in activitys){
            it.finish()
        }
        activitys.clear()
    }
}