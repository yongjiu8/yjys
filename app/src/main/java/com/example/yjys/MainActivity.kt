package com.example.yjys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.yjys.adapter.FragmentAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.Line
import com.example.yjys.fragment.FaxianFragment
import com.example.yjys.fragment.HomeFragment
import com.example.yjys.fragment.MoverFragment
import com.example.yjys.fragment.UserFragment
import com.example.yjys.utils.ActivityCollector
import com.example.yjys.utils.MyCallBack
import com.example.yjys.utils.Net
import com.example.yjys.utils.StringUtil
import com.umeng.socialize.UMShareAPI
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.nodes.Document


class MainActivity : BaseActivity() {

    private var exitTime: Long = 0

    var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this

        if (Build.VERSION.SDK_INT >= 23) {
            val mPermissionList = arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS
            )
            ActivityCompat.requestPermissions(this, mPermissionList, 123)
        }

        //初始化数据
        val fragments = mutableListOf<Fragment>()
        fragments.add(HomeFragment())
        fragments.add(MoverFragment())
        fragments.add(FaxianFragment())
        fragments.add(UserFragment())

        //添加适配器
        val fragmentAdapter = FragmentAdapter(fragments, supportFragmentManager)
        page.offscreenPageLimit = 3
        page.adapter=fragmentAdapter


        //item 点击事件
        but_home.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeitem -> page.currentItem = 0
                R.id.moveritem -> page.currentItem = 1
                R.id.faxianitem -> page.currentItem = 2
                R.id.useritem -> page.currentItem = 3
            }
            false
        }

        //page滑动监听
        page.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                but_home.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        initData()
    }

    private fun initData() {
        Net.get(activity!!,AppConfig.domonUrl, object : MyCallBack {
            override fun callBack(doc: Document?) {

                val text = doc?.body()?.toString()?.replace("\n","")
                        ?.replace("\t","")

                if (doc != null && !"".equals(text)) {
                    AppConfig.lineData.clear()
                    val lines =
                        StringUtil.getSubStrArray(text, "<line>", "</line>")

                    lines?.let {
                        for (it in lines) {
                            val lineSource = it.split("----")
                            AppConfig.lineData.add(Line(lineSource[0].trim(),lineSource[1].trim()))
                        }
                    }

                    Log.e("初始化",AppConfig.lineData.toString())


                    val subString3 = StringUtil.getSubString(
                        doc.body().toString(),
                        "<sharetitle>",
                        "</sharetitle>"
                    ).trim()
                    AppConfig.shareTitle = subString3

                    val subString4 = StringUtil.getSubString(
                            text,
                        "<sharecount>",
                        "</sharecount>"
                    ).trim()
                    AppConfig.shareCount = subString4

                    val subString5 =
                        StringUtil.getSubString(text, "<shareimg>", "</shareimg>").trim()
                    AppConfig.shareImg = subString5

                    val subString6 =
                        StringUtil.getSubString(text, "<shareurl>", "</shareurl>").trim()
                    AppConfig.shareUrl = subString6

                    val cao = StringUtil.getSubString(text, "<cao>", "</cao>").trim()
                    val split = cao.split(",")
                    AppConfig.caoList.clear()
                    for (it in split){
                        if (it.equals("")){
                            break
                        }
                        AppConfig.caoList.add(it)
                    }

                    Log.e("初始化",subString3)
                    Log.e("初始化",subString4)
                    Log.e("初始化",subString5)
                    Log.e("初始化",subString6)
                    activity?.runOnUiThread {
                    Toast.makeText(context, "获取配置成功", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(context, "获取配置失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun callError() {
                activity?.runOnUiThread {
                    Toast.makeText(context, "获取配置失败", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.getAction() == KeyEvent.ACTION_DOWN) {

            if (System.currentTimeMillis() - exitTime > 3000) {
                Toast.makeText(getApplicationContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //关闭所有窗口
                ActivityCollector.finishAll()
                android.os.Process.killProcess(android.os.Process.myPid())
            }
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }


}