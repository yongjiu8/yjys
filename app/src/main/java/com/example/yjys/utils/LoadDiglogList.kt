package com.example.yjys.utils

import com.kaopiz.kprogresshud.KProgressHUD

object LoadDiglogList {

    val list = mutableListOf<KProgressHUD>()

    fun add(load : KProgressHUD){
        list.add(load)
    }

    fun remover(){
        list.clear()
    }

    @JvmName("getList1")
    fun getList() : List<KProgressHUD>{
        return list
    }
}