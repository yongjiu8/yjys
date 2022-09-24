package com.example.yjys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.yjys.application.Application
import com.example.yjys.config.AppConfig
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.umeng.umverify.UMVerifyHelper
import com.umeng.umverify.listener.UMTokenResultListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    var mqqListener : IUiListener? = null
    var activity: Activity? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        activity = this

        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = resources.getColor(R.color.md_purple_800)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            0
        );
        val sharedPreferences = getSharedPreferences("token", 0)
        val string = sharedPreferences.getString("key", null)
        initAuth()
        if (string == null || "".equals(string) ){
            login_home.visibility = View.VISIBLE
        }else{
            if (string.equals("qq")){
                if (Application.mTencent?.isSessionValid!!){
                    login_home.visibility = View.GONE
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(activity, "QQ登录已过期", Toast.LENGTH_SHORT).show()
                }
            }else{
                login_home.visibility = View.GONE
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
            }

        }

        tiao.setOnClickListener {
            val edit = getSharedPreferences("token", 0).edit()
            edit.putString("key", "1f56a1f56we15f15aw1f5wea5")
            edit.commit()
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {

            loading(this)
/*
*   5.检测终端网络环境是否支持一键登录或者号码认证
*/
            val checkEnvAvailable = Application.umVerifyHelper?.checkEnvAvailable();

            if (checkEnvAvailable == true) {
                Application.umVerifyHelper?.getLoginToken(this, 5000);
                Log.e("友盟", "启动验证")
            } else {
                closeLoading(this)
                Toast.makeText(this, "当前环境不支持手机号登陆，请使用数据网络进行手机号登陆", Toast.LENGTH_LONG).show()
            }

        }

        mqqListener = object : IUiListener{

            override fun onComplete(p0: Any?) {
                Log.e("qq互联", p0.toString())
                val jo: JSONObject = JSON.parseObject(p0.toString())
                val ret: Int = jo.getInteger("ret")
                if (ret == 0) {
                    Toast.makeText(
                        activity, "登录成功",
                        Toast.LENGTH_LONG
                    ).show()
                    val openID = jo.getString("openid")
                    val accessToken = jo.getString("access_token")
                    val expires = jo.getString("expires_in")
                    Application.mTencent?.setOpenId(openID)
                    Application.mTencent?.setAccessToken(accessToken, expires)
                    val edit = getSharedPreferences("token", 0).edit()
                    edit.putString("key", "qq")
                    edit.commit()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)


                }

            }

            override fun onError(p0: UiError?) {
                Log.e("qq互联", p0.toString())
            }

            override fun onCancel() {
                Log.e("qq互联", "取消登陆")
            }

            override fun onWarning(p0: Int) {
                Log.e("qq互联", p0.toString())
            }

        }

        qq.setOnClickListener {
            if (!Application.mTencent?.isSessionValid!!){
                Log.e("qq互联", "QQ登录点击")
                Application.mTencent?.login(activity, "all", mqqListener)
            }else{
                val edit = getSharedPreferences("token", 0).edit()
                edit.putString("key", "qq")
                edit.commit()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }

    fun initAuth(){
        /*
* 1.初始化获取token实例
*/
        val mTokenListener = object : UMTokenResultListener {
            override fun onTokenSuccess(p0: String?) {
                activity?.let { closeLoading(it) }
                Log.e("友盟", p0!!)
                val parseObject = JSON.parseObject(p0)
                if (parseObject.getString("code").equals("600000")){
                    Log.e("友盟", "token成功" + p0)
                    val edit = getSharedPreferences("token", 0).edit()
                    edit.putString("key", p0)
                    edit.commit()
                    Application.umVerifyHelper?.quitLoginPage()
                    Application.umVerifyHelper?.hideLoginLoading()
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(baseContext, parseObject.getString("msg"), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onTokenFailed(p0: String?) {
                activity?.let { closeLoading(it) }
                Log.e("友盟", "token失败" + p0)
                val parseObject = JSON.parseObject(p0)
                Toast.makeText(baseContext, parseObject.getString("msg"), Toast.LENGTH_SHORT).show()
            }

        }

        /*
*   2.初始化SDK实例
*/
        Application.umVerifyHelper = UMVerifyHelper.getInstance(this, mTokenListener);
/*
*   3.设置SDK秘钥
*/
        Application.umVerifyHelper?.setAuthSDKInfo(AppConfig.umAuthKey);
/*
*   4.设置token监听
*/
        Application.umVerifyHelper?.setAuthListener(mTokenListener);

    }

    override fun onRestart() {
        super.onRestart()
        initAuth()
        val sharedPreferences = getSharedPreferences("token", 0)
        val string = sharedPreferences.getString("key", null)
        if (string == null || "".equals(string) ){
            login_home.visibility = View.VISIBLE
        }else{
            if (string.equals("qq")){
                if (Application.mTencent?.isSessionValid!!){
                    login_home.visibility = View.GONE
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(activity, "QQ登录已过期", Toast.LENGTH_SHORT).show()
                }
            }else{
                login_home.visibility = View.GONE
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_LOGIN ||
            requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mqqListener);
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

