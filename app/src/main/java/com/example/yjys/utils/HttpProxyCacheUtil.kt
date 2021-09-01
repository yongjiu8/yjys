package com.example.yjys.utils

import com.danikula.videocache.HttpProxyCacheServer
import com.example.yjys.application.Application
import com.example.yjys.config.CacheFileNameGenerator
import java.io.File

class HttpProxyCacheUtil {

    companion object{
        @JvmStatic
        var audioProxy : HttpProxyCacheServer? = null

        @JvmStatic
        fun getAudioProxyOne():HttpProxyCacheServer{
            if (audioProxy == null){
                audioProxy = HttpProxyCacheServer.Builder(Application.instance?.applicationContext).cacheDirectory(
                    File(DirUtil.getDiskCachePath(Application.instance?.applicationContext!!))
                ).maxCacheSize(1024*1024*1024).fileNameGenerator(CacheFileNameGenerator()).build()
            }
            return audioProxy!!
        }
    }

}