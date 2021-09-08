package com.example.yjys.faxianui

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yjys.BaseActivity
import com.example.yjys.R
import com.example.yjys.adapter.GeCiAdapter
import com.example.yjys.adapter.MusicPlayListAdapter
import com.example.yjys.config.AppConfig
import com.example.yjys.entity.GeCi
import com.example.yjys.entity.Lyric
import com.example.yjys.entity.MusicPlay
import com.example.yjys.entity.MusicPlayList
import com.example.yjys.utils.HttpProxyCacheUtil
import com.example.yjys.utils.Net
import com.example.yjys.utils.NetCallBack
import com.example.yjys.utils.RecycleViewItemClick
import com.google.gson.Gson
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import kotlinx.android.synthetic.main.activity_music_play.*
import kotlinx.android.synthetic.main.common_title.*
import org.jsoup.Connection
import java.util.*

class MusicPlayActivity : BaseActivity() {
    var isOk = false
    var rid:String = "0"
    var mediaPlayer:MediaPlayer? = null
    var playMode = "single"
    var geCiList = mutableListOf<GeCi>()

    var geCiAdapter : GeCiAdapter? = null

    var musicList = mutableListOf<MusicPlayList>()

    var isShow = false

    var isNext = true

    var activity: Activity? = null

    val onAudioFocusChangeListener = object : AudioManager.OnAudioFocusChangeListener{
        override fun onAudioFocusChange(focusChange: Int) {
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> {

                }
                AudioManager.AUDIOFOCUS_LOSS ->{
                    mediaPlayer?.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    mediaPlayer?.pause()
                }

                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {

                }
            }
        }

    }

    var lrclist = mutableListOf<Lyric.DataBean.LrclistBean>()

    var notificationManager:NotificationManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_play)
        supportActionBar?.hide()

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //创建通知渠道
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel =
                NotificationChannel("music", "音乐播放", NotificationManager.IMPORTANCE_HIGH)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        activity = this
        isShow=true

        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer()
        }
        try {
            rid = intent.getStringExtra("rid").toString()
            val edit = getSharedPreferences("rid", 0).edit()
            edit.putString("rid",rid)
            edit.commit()
        }catch (e:java.lang.Exception){
            val edit = getSharedPreferences("rid", 0)
            rid = edit?.getString("rid","157191563").toString()
        }

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN)
        initPlayer()
        initgeCi(rid)

        xunhuan.setImageResource(R.drawable.danqu)
        playMode = "single"

        val linearLayoutManager = LinearLayoutManager(context)
        geCi.layoutManager = linearLayoutManager
        geCiAdapter = GeCiAdapter(this,geCiList)
        geCi.adapter = geCiAdapter

        //初始化播放列表数据
        val musicDataList = AppConfig.MusicDataList
        musicList.clear()
        for (it in musicDataList){
            musicList.add(MusicPlayList(it.name+"-"+it.artist,it.rid))
        }

        val linearLayoutManager1 = LinearLayoutManager(context)
        mlist.layoutManager = linearLayoutManager1
        val musicPlayListAdapter = MusicPlayListAdapter(this, musicList)
        mlist.adapter = musicPlayListAdapter
        musicPlayListAdapter.setOnItemClick(object : RecycleViewItemClick{
            override fun onClick(position: Int, list: List<Any>) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mlist.visibility = View.GONE
                rid = (list[position] as MusicPlayList).id.toString()

                isNext = false
                initgeCi(rid)
            }
        })

    }



    override fun onRestart() {

        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer()
        }

        isOk = false

        super.onRestart()
    }

    fun pause() {
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
                isOk=false
                bo.setImageResource(R.drawable.zhanting)
            }else{
                isOk = true
                mediaPlayer?.start()
                bo.setImageResource(R.drawable.bofang)
            }

    }


    fun initPlayer(){

        liebiao.setOnClickListener {
            if (mlist.visibility == View.GONE){
                mlist.visibility = View.VISIBLE
            }else{
                mlist.visibility = View.GONE
            }
        }

        imgback.setOnClickListener{
            finish()
        }

        bo.setOnClickListener {
            pause()
        }

        //循环按钮点击事件
        xunhuan.setOnClickListener {
            when(playMode){
                "single" -> {
                    playMode = "cycle"
                    xunhuan.setImageResource(R.drawable.xunhuan)
                }
                "cycle" -> {
                    playMode = "single"
                    xunhuan.setImageResource(R.drawable.danqu)
                }
            }
        }

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            bo.setImageResource(R.drawable.zhanting)
            isOk = false

            if (isNext){

                //自动下一曲
                val musicDataList = AppConfig.MusicDataList
                for ((index,it) in musicDataList.withIndex()){
                    if (it.rid.toString().equals(rid)){
                        if (index == musicDataList.size-1){
                            if(isShow){
                                AestheticDialog.Builder(
                                    activity!!,
                                    DialogStyle.FLASH,
                                    DialogType.ERROR
                                )
                                    .setTitle("提示")
                                    .setMessage("没有下一曲了！")
                                    .show()
                            }
                        }else{
                            when(playMode){
                                "single" ->{
                                    mediaPlayer?.stop()
                                    mediaPlayer?.reset()
                                    rid = musicDataList[index+1].rid.toString()
                                    initgeCi(rid)
                                }
                                "cycle" ->{
                                    mediaPlayer?.stop()
                                    mediaPlayer?.reset()
                                    rid = musicDataList[index].rid.toString()
                                    initgeCi(rid)
                                }
                            }

                        }
                        break
                    }
                }

            }

            isNext = true

        }


        mediaPlayer?.setOnPreparedListener {

            isOk = true

            pause()
            jinDu.max = mediaPlayer?.duration!!

            val miao = mediaPlayer?.duration?.div(1000)
            val fen = miao?.div(60)
            val yu = miao?.rem(60)
            val long = "" + (fen.toString() + ":" + yu)
            end.setText(long)

            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (isOk){
                        if (mediaPlayer?.isPlaying == true){

                            activity?.runOnUiThread {
                                jinDu.progress = mediaPlayer?.currentPosition!!
                                val miao = mediaPlayer?.currentPosition?.div(1000)
                                val fen = miao?.div(60)
                                val yu = miao?.rem(60)
                                val long = "" + (fen.toString() + ":" + if(yu.toString().length == 1) "0"+yu.toString() else yu)
                                start.setText(long)
                            }

                        }

                    }

                }

            },0,1000)


            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (isOk){
                        if (mediaPlayer?.isPlaying == true){

                            activity?.runOnUiThread {
                                //动态歌词
                                val souMs = mediaPlayer?.currentPosition?.div(10)?.toInt()
                                Log.e("进度监听",souMs.toString())
                                    if(souMs == null){
                                        return@runOnUiThread
                                    }
                                if (souMs <= 0){
                                    return@runOnUiThread
                                }
                                var i = 0
                                var size = lrclist.size - 1
                                while (i <= size){
                                    var mid = (i + size)/2
                                    Log.e("歌词",(lrclist[mid].time.toDouble() * 1000 /10).toInt().toString())
                                    if ((lrclist[mid].time.toDouble() * 1000/10).toInt() == souMs){
                                        geCiList[mid].flag = true
                                        for ((index,it) in geCiList.withIndex()){
                                            if (index != mid){
                                                geCiList[index].flag = false
                                            }
                                        }
                                        geCiAdapter?.notifyDataSetChanged()
                                        if(mid >= 6 && mid <= (geCiList.size-6)){
                                            geCi.scrollToPosition(mid+5)
                                        }else if(mid >= 6){
                                            geCi.scrollToPosition(geCiList.size-1)
                                        }
                                        break
                                    }else if ((lrclist[mid].time.toDouble() * 1000 /10).toInt() > souMs!!){
                                        size = mid - 1
                                    }else if ((lrclist[mid].time.toDouble() * 1000 /10).toInt() < souMs){
                                        i = mid + 1
                                    }
                                }

                            }

                        }

                    }

                }

            },0,9)



        }

        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            //isOk = false
            false
        }

        jinDu.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    activity?.runOnUiThread {
                        val miao = progress?.div(1000)
                        val fen = miao?.div(60)
                        val yu = miao?.rem(60)
                        val long = "" + (fen.toString() + ":" + if(yu.toString().length == 1) "0"+yu.toString() else yu)
                        start.setText(long)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (isOk){
                    activity?.runOnUiThread {
                        mediaPlayer?.pause()
                    }
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (isOk){
                    activity?.runOnUiThread {
                        setWeiZhi(seekBar?.progress!!)
                        mediaPlayer?.start()
                    }
                }
            }

        })



        shang.setOnClickListener {
            isNext = false
            val musicDataList = AppConfig.MusicDataList
            for ((index,it) in musicDataList.withIndex()){
                if (it.rid.toString().equals(rid)){
                    if (index == 0){
                        if (isShow){
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("没有上一曲了！")
                                .show()
                        }
                    }else{
                        mediaPlayer?.stop()
                        mediaPlayer?.reset()
                        rid = musicDataList[index-1].rid.toString()
                        initgeCi(rid)
                    }

                    break
                }
            }
        }

        next.setOnClickListener {
            isNext = false
            val musicDataList = AppConfig.MusicDataList
            for ((index,it) in musicDataList.withIndex()){
                if (it.rid.toString().equals(rid)){
                    if (index == musicDataList.size-1){
                        if (isShow){
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("没有下一曲了！")
                                .show()
                        }
                    }else{
                        mediaPlayer?.stop()
                        mediaPlayer?.reset()
                        rid = musicDataList[index+1].rid.toString()
                        initgeCi(rid)
                    }
                    break
                }
            }
        }


    }

    fun setWeiZhi(position: Int){

        try {
            mediaPlayer?.seekTo(position)
        }catch (e: Exception){
            Log.e("播放器", e.toString())
        }

    }

    fun initgeCi(rid:String){
        Net.getPlay(activity!!,"http://m.kuwo.cn/newh5/singles/songinfoandlrc?musicId="+rid+"&httpsStatus=1&reqId=29b2b2f0-1448-11eb-822b-cd5aded7e379",
            object :
                NetCallBack {
                override fun callBack(doc: Connection.Response?) {
                    val data = doc?.body().toString()
                    if ("".equals(data)) {
                        activity?.runOnUiThread {
                            if(isShow){
                                AestheticDialog.Builder(
                                    activity!!,
                                    DialogStyle.FLASH,
                                    DialogType.ERROR
                                )
                                    .setTitle("提示")
                                    .setMessage("加载失败！请检查您的网络")
                                    .show()
                            }
                        }
                        return
                    }


                    Log.e("音乐输出", data)
                    val musicData = Gson().fromJson(data, Lyric::class.java)

                    if (musicData.data == null) {
                        AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                        )
                                .setTitle("警告")
                                .setMessage("歌词加载失败！请检查您的网络后重试")
                                .show()
                        return
                    }

                    activity?.runOnUiThread {
                        lrclist.clear()
                        lrclist.addAll(musicData.data.lrclist)
                        val songinfo = musicData.data.songinfo

                        geCiList.clear()
                        for (it in lrclist){
                            geCiList.add(GeCi(it.lineLyric,false))
                        }
                        geCiAdapter?.notifyDataSetChanged()
                        geCi.scrollToPosition(0)
                        val title = songinfo.songName + "-" + songinfo.artist
                        comTitle.setText(title)


                        //歌词加载成功在加载音乐
                        initPlay(rid)
                    }

                }

                override fun callError() {

                    activity?.runOnUiThread {

                        if (isShow){
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                        }
                    }
                }

            })

    }

    private fun initPlay(rid:String) {

        Net.getPlay(activity!!,"http://www.kuwo.cn/url?format=mp3&rid=" + rid + "&response=url&type=convert_url3&br=320kmp3&from=web&httpsStatus=1&reqId=ffce54d1-12a8-11eb-be82-f9f2120469ef",
            object :
                NetCallBack {
                override fun callBack(doc: Connection.Response?) {
                    val data = doc?.body().toString()
                    if ("".equals(data)) {
                        activity?.runOnUiThread {
                            if (isShow){
                                AestheticDialog.Builder(
                                    activity!!,
                                    DialogStyle.FLASH,
                                    DialogType.ERROR
                                )
                                    .setTitle("提示")
                                    .setMessage("资源加载失败！请检查您的网络")
                                    .show()
                            }
                        }
                        return
                    }


                    Log.e("音乐输出", data)
                    val musicData = Gson().fromJson(data, MusicPlay::class.java)

                    activity?.runOnUiThread {
                        val audioProxyOne = HttpProxyCacheUtil.getAudioProxyOne()
                        val proxyUrl = audioProxyOne.getProxyUrl(musicData.url)
                        try {
                            mediaPlayer?.setDataSource(proxyUrl)
                            mediaPlayer?.prepareAsync()
                        }catch (e:java.lang.Exception){

                        }

                    }

                }

                override fun callError() {
                    activity?.runOnUiThread {
                        if (isShow){
                            AestheticDialog.Builder(
                                activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载失败！请检查您的网络")
                                .show()
                        }
                    }
                }

            })
    }

    override fun onStart() {
        isOk = true
        isShow = true
        super.onStart()
    }

    override fun onStop() {
        isShow = false
        if (mediaPlayer?.isPlaying == true) {
            val intent = Intent(this, MusicPlayActivity::class.java)
            val activity1 = PendingIntent.getActivity(this, 0, intent, 0)
            val contentIntent = NotificationCompat.Builder(this, "music")
                .setContentTitle("永久影视")
                .setContentText("音乐播放中...")
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo))
                .setAutoCancel(true)
                .setContentIntent(activity1)
                .build()
            notificationManager?.notify(1, contentIntent)
        }
        super.onStop()
    }

    override fun onDestroy() {
        isOk = false
       // mediaPlayer?.stop()
        //mediaPlayer?.release()
        //mediaPlayer = null
        if (mediaPlayer?.isPlaying == true){
            val intent = Intent(this, MusicPlayActivity::class.java)
            val activity1 = PendingIntent.getActivity(this, 0, intent, 0)
            val contentIntent = NotificationCompat.Builder(this, "music")
                .setContentTitle("永久影视")
                .setContentText("音乐播放中...")
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo))
                .setAutoCancel(true)
                .setContentIntent(activity1)
                .build()
            notificationManager?.notify(1,contentIntent)
        }

        super.onDestroy()
    }
}



