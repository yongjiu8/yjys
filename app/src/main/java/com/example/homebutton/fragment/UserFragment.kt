package com.example.homebutton.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.homebutton.*
import com.example.homebutton.adapter.MSelectRecyclerViewAdapter
import com.example.homebutton.adapter.UserGridViewAdapter
import com.example.homebutton.application.Application
import com.example.homebutton.entity.FaXian
import com.example.homebutton.myview.MyFragment
import com.example.homebutton.myview.MyGridView
import com.tencent.connect.UserInfo
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.user_fragment.*
import org.json.JSONObject

class UserFragment() : Fragment() {
    var mselect : RecyclerView? = null
    var mView : View? = null
    var gridView : GridView? =null
    val data = listOf<FaXian>(FaXian("分享",R.drawable.share),
        FaXian("关于",R.drawable.jizhan),
        FaXian("退出",R.drawable.wangguan))

    val datas = arrayListOf<FaXian>(
        FaXian("收藏",R.drawable.shouchang_white), FaXian("历史",
        R.drawable.lishi_white), FaXian("反馈",R.drawable.fankui_white)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null){
            mView = inflater.inflate(R.layout.user_fragment, container, false)
        }

        val title = mView?.findViewById<TextView>(R.id.comTitle)
        title?.setText("个人中心")

        mselect = mView?.findViewById(R.id.mSelect)
        val linearLayoutManager = LinearLayoutManager(activity)
        mselect?.layoutManager = linearLayoutManager
        val mSelectRecyclerViewAdapter = MSelectRecyclerViewAdapter(activity!!, data)
        mselect?.adapter = mSelectRecyclerViewAdapter

        gridView = mView?.findViewById(R.id.hoGrid)
        gridView?.adapter = UserGridViewAdapter(activity!!,datas)
        gridView?.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0 -> {
                    val intent = Intent(context, Favorites::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, History::class.java)
                    startActivity(intent)
                }
                2 -> {

                }
            }

        }


        return mView
    }

    override fun onStart() {
        super.onStart()

        if (Application.mTencent?.isSessionValid!!){
            UserInfo(activity,Application.mTencent?.qqToken).getUserInfo(object : IUiListener{
                override fun onComplete(p0: Any?) {
                    Log.e("获取信息",p0.toString())
                    val parseObject = JSON.parseObject(p0.toString())
                    val ret = parseObject.getInteger("ret")
                    if (ret == 0){
                        val name = parseObject.getString("nickname")
                        val img = parseObject.getString("figureurl_qq")
                        qqname.setText(name)
                        Glide.with(activity!!).load(img).into(headImg)
                    }
                }

                override fun onError(p0: UiError?) {

                }

                override fun onCancel() {

                }
            })
        }else{
            val edit = activity?.getSharedPreferences("token", 0)
            val string = edit?.getString("key", null)
            if (string != null && string.equals("qq")){
                Toast.makeText(activity, "QQ登录已过期", Toast.LENGTH_SHORT).show()
            }
        }

    }
}