package com.example.homebutton

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.homebutton.application.Application
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.umeng.umverify.UMVerifyHelper
import com.umeng.umverify.listener.UMTokenResultListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    var mqqListener : IUiListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            0
        );
        val sharedPreferences = getSharedPreferences("token", 0)
        val string = sharedPreferences.getString("key", null)
        if (string == null || "".equals(string) ){
            initAuth()
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
            val ret = Application.umVerifyHelper?.checkEnvAvailable();
/*
*   6.若步骤4支持，则根据业务情况，调用预取号或者一键登录接口
*     详见demo接入工程
*/
                Application.umVerifyHelper?.getLoginToken(this, 5000);
                Log.e("友盟", "启动验证")
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
                closeLoading()
                Log.e("友盟", p0)
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
                closeLoading()
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
        Application.umVerifyHelper?.setAuthSDKInfo("jvK/NU7ucPgfDhSH/uM1tapRFwJgJFeGnUolNH5OdeGI7ceEEj2srVtBj/CTQTn0iq3nPp8I1oS0dv/+EMmPfzW136WsX9rFDtntzWjOuanDICRmoGiHt7bXa/fECnIWKO05UxvmvuFN4G+8LkfcMvli1UaPZ00bFJtlOoiL5OxwCTipl1GrWmOMfVTiD29uPLpmIty9gzF4kMYtooEEa8xf0UC72XLH+8ZiYoSk0/u+qcgubEnn9BmBI8b3jEz8LGNDTkQm4YqXAL1NNrucp0cYqhAX2X0W");
/*
*   4.设置token监听
*/
        Application.umVerifyHelper?.setAuthListener(mTokenListener);
    }

    override fun onRestart() {
        super.onRestart()
        val sharedPreferences = getSharedPreferences("token", 0)
        val string = sharedPreferences.getString("key", null)
        if (string == null || "".equals(string) ){
            initAuth()
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

        super.onActivityResult(requestCode, resultCode, data);
    }
}