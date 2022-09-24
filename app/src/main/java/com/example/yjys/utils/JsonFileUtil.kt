package com.example.yjys.utils

import android.content.Context
import com.example.yjys.entity.AutoType
import com.google.gson.Gson
import java.nio.charset.Charset

object JsonFileUtil {


    fun readJsonToAutoType(activity :Context,file :String): AutoType{
        val inputStream = activity?.assets?.open(file)
        val available = inputStream?.available()
        val of = available?.let { ByteArray(available) }
        inputStream?.read(of)
        inputStream?.close()
        val jsonStr = of?.let { String(of, Charset.forName("utf-8")) }
        return Gson().fromJson(jsonStr, AutoType::class.java)
    }

}