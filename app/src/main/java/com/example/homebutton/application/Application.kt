package com.example.homebutton.application

import android.app.Application
import android.text.method.NumberKeyListener
import android.util.Log
import androidx.multidex.MultiDex
import com.example.homebutton.config.AppConfig
import com.tencent.bugly.Bugly
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.tauth.Tencent
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.umverify.UMVerifyHelper
import com.umeng.umverify.listener.UMTokenResultListener


class Application() : Application() {

    companion object{
        @JvmStatic
        public var instance: Application? = null
        @JvmStatic
        var mTencent : Tencent? = null

        var umVerifyHelper : UMVerifyHelper? = null

        @JvmName("getInstance1")
        @JvmStatic
        fun getInstance(): Application? {
            return instance
        }

    }


    override fun onCreate() {
        super.onCreate()

        // 主要是添加下面这句代码
        MultiDex.install(this);

        instance = this
        mTencent = Tencent.createInstance(AppConfig.TappId, applicationContext);
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = mutableMapOf<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)


        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口
        QbSdk.initX5Environment(this, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                Log.e("app初始化：", "x5核心加载完成！")
            }

            override fun onViewInitFinished(p0: Boolean) {
                if (p0) {
                    Log.e("app初始化：", "x5内核加载成功！")
                } else {
                    Log.e("app初始化：", "x5内核加载失败！")
                }
            }

        });

        initUmeng()

        Bugly.init(applicationContext, AppConfig.bugId, false);

    }

    fun initUmeng(){
        UMConfigure.init(this, AppConfig.umengKey, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }



}