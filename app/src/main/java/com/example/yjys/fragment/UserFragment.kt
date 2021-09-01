package com.example.yjys.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.yjys.*
import com.example.yjys.adapter.MSelectRecyclerViewAdapter
import com.example.yjys.adapter.UserGridViewAdapter
import com.example.yjys.application.Application
import com.example.yjys.entity.FaXian
import com.tencent.connect.UserInfo
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import kotlinx.android.synthetic.main.user_fragment.*

class UserFragment() : Fragment() {
    var mselect : RecyclerView? = null
    var mView : View? = null
    var gridView : GridView? =null
    val data = listOf<FaXian>(FaXian("分享给朋友",R.drawable.share),
        FaXian("永久社区官网",R.drawable.jizhan),
        FaXian("退出登陆",R.drawable.wangguan))

    val datas = arrayListOf<FaXian>(
        FaXian("我的收藏",R.drawable.shouchang_white), FaXian("播放历史",
        R.drawable.lishi_white), FaXian("反馈中心",R.drawable.fankui_white)
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
        val mSelectRecyclerViewAdapter = MSelectRecyclerViewAdapter(requireActivity(), data)
        mselect?.adapter = mSelectRecyclerViewAdapter

        gridView = mView?.findViewById(R.id.hoGrid)
        gridView?.adapter = UserGridViewAdapter(requireActivity(),datas)
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
                    if (isQQClientAvailable(requireContext())) {
                        val qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=2584059921&version=1"
                        activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)))
                    } else {
                        Toast.makeText(context, "请安装QQ客户端", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


        return mView
    }

    //检查QQ是否安装
    fun isQQClientAvailable(context: Context): Boolean {
        val packageManager = context.packageManager
        val pinfo = packageManager.getInstalledPackages(0)
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mobileqq") {
                    return true
                }
            }
        }
        return false
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

                override fun onWarning(p0: Int) {

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