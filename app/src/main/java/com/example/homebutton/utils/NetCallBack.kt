package com.example.homebutton.utils

import org.jsoup.Connection

interface NetCallBack {

    fun callBack(doc: Connection.Response?)

    fun callError()
}