package com.example.yjys.config

import android.net.Uri
import com.danikula.videocache.file.FileNameGenerator

class CacheFileNameGenerator : FileNameGenerator {

    override fun generate(url: String?): String {
        val uri = Uri.parse(url)
        val pathSegments = uri.pathSegments
        var path:String ? = null
        if (pathSegments?.size!! > 0){
            path = pathSegments[pathSegments.size-1]
        }else{
            path = url
        }
        return path!!
    }

}