package com.example.yjys.utils

import android.content.Context
import android.os.Environment

class DirUtil {
    companion object{
        /**
         * 获取cache路径
         */
        fun getDiskCachePath(context: Context): String? {
            return if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
                context.getExternalCacheDir()?.getPath()
            } else {
                context.getCacheDir().getPath()
            }
        }
    }

}