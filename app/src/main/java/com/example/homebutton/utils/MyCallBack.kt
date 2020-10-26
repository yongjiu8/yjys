package com.example.homebutton.utils

import org.jsoup.Connection
import org.jsoup.nodes.Document

interface MyCallBack {

    fun callBack(doc:Document?)

    fun callError()
}