package com.example.homebutton

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.homebutton.adapter.FragmentAdapter
import com.example.homebutton.fragment.FaxianFragment
import com.example.homebutton.fragment.HomeFragment
import com.example.homebutton.fragment.MoverFragment
import com.example.homebutton.fragment.UserFragment
import com.example.homebutton.utils.ActivityCollector

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初始化数据
        val fragments = mutableListOf<Fragment>()
        fragments.add(HomeFragment())
        fragments.add(MoverFragment())
        fragments.add(FaxianFragment())
        fragments.add(UserFragment())

        //添加适配器
        val fragmentAdapter = FragmentAdapter(fragments, supportFragmentManager)
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
}