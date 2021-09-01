package com.example.yjys.utils

import org.jsoup.nodes.Document

interface MyCallBack {

    fun callBack(doc:Document?)

    fun callError()
}