package com.example.yjys.utils

import org.jsoup.Connection

interface NetCallBack {

    fun callBack(doc: Connection.Response?)

    fun callError()
}