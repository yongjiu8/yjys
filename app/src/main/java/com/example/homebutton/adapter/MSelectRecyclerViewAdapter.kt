package com.example.homebutton.adapter

import android.R.attr.thumb
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.homebutton.LoginActivity
import com.example.homebutton.R
import com.example.homebutton.application.Application
import com.example.homebutton.config.AppConfig
import com.example.homebutton.entity.FaXian
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import kotlinx.android.synthetic.main.activity_play.*


class MSelectRecyclerViewAdapter(val context: Context, data: List<FaXian>) :
    RecyclerView.Adapter<MSelectRecyclerViewAdapter.MyHolder>() {

    val umShareListener = object : UMShareListener{
        override fun onStart(p0: SHARE_MEDIA?) {

        }

        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
            Toast.makeText(context, "分享出错", Toast.LENGTH_SHORT).show()
            Log.e("分享",p1.toString())
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
            Toast.makeText(context, "分享取消", Toast.LENGTH_SHORT).show()
        }

    }

    val dataList=data

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
         var title : TextView = view.findViewById(R.id.title)
        var img : ImageView = view.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var inflate:View? = null
            inflate = LayoutInflater.from(context)
                .inflate(R.layout.mselect_recyclerview_adapater, parent, false)
        return MyHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.setText(dataList.get(position).text)
        holder.img.setImageResource(dataList.get(position).img)

        holder.title.setOnClickListener {
            when(position){
                0 -> {
                    val image = UMImage(
                        context,
                        AppConfig.shareImg
                    ) //网络图片
                    val web = UMWeb(AppConfig.shareUrl)
                    web.title = AppConfig.shareTitle //标题

                    web.setThumb(image) //缩略图

                    web.description = AppConfig.shareCount //描述
                    ShareAction(context as Activity?)
                        .withMedia(web)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();
                }
                1 -> {
                    Application.mTencent?.logout(context)
                    val edit = context?.getSharedPreferences("token", 0)?.edit()
                    edit?.putString("key", "")
                    edit?.commit()
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}