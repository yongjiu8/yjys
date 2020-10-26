package com.example.homebutton.faxianui

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.example.homebutton.BaseActivity
import com.example.homebutton.R
import com.example.homebutton.config.AppConfig
import com.example.homebutton.entity.Lyric
import com.example.homebutton.entity.MusicPlay
import com.example.homebutton.utils.Net
import com.example.homebutton.utils.NetCallBack
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
    var rid:String = ""
    var mediaPlayer:MediaPlayer? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_play)
        supportActionBar?.hide()

        activity = this

        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer()
        }
        rid = intent.getStringExtra("rid")
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN)
        initPlayer()
        initgeCi(rid)

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
            bo.setImageResource(R.drawable.zhanting)
        }else{
            mediaPlayer?.start()
            bo.setImageResource(R.drawable.bofang)
        }
    }


    fun initPlayer(){

        imgback.setOnClickListener{
            finish()
        }

        bo.setOnClickListener {
            pause()
        }

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            bo.setImageResource(R.drawable.zhanting)

            //自动下一曲
            val musicDataList = AppConfig.MusicDataList
            for ((index,it) in musicDataList.withIndex()){
                if (it.rid.toString().equals(rid)){
                    if (index == musicDataList.size-1){
                        AestheticDialog.Builder(
                            BaseActivity.activity!!,
                            DialogStyle.FLASH,
                            DialogType.ERROR
                        )
                            .setTitle("提示")
                            .setMessage("没有下一曲了！")
                            .show()
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


        mediaPlayer?.setOnPreparedListener {

            closeLoading()

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

                                //动态歌词
                                val currentPosition = mediaPlayer?.currentPosition?.div(1000)
                                Log.e("进度监听",currentPosition.toString())
                                for (it in lrclist){
                                    if (it.time.split(".")[0].toInt() == currentPosition?.toInt()){
                                        showCi.setText(it.lineLyric)
                                    }
                                }

                            }

                        }

                    }

                }

            },0,1000)



        }

        mediaPlayer?.setOnErrorListener { mp, what, extra ->
            isOk = false
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
            val musicDataList = AppConfig.MusicDataList
            for ((index,it) in musicDataList.withIndex()){
                if (it.rid.toString().equals(rid)){
                    if (index == 0){
                        AestheticDialog.Builder(
                            BaseActivity.activity!!,
                            DialogStyle.FLASH,
                            DialogType.ERROR
                        )
                            .setTitle("提示")
                            .setMessage("没有上一曲了！")
                            .show()
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
            val musicDataList = AppConfig.MusicDataList
            for ((index,it) in musicDataList.withIndex()){
                if (it.rid.toString().equals(rid)){
                    if (index == musicDataList.size-1){
                        AestheticDialog.Builder(
                            BaseActivity.activity!!,
                            DialogStyle.FLASH,
                            DialogType.ERROR
                        )
                            .setTitle("提示")
                            .setMessage("没有下一曲了！")
                            .show()
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
        loading(activity!!)
        Net.getPlay("http://m.kuwo.cn/newh5/singles/songinfoandlrc?musicId="+rid+"&httpsStatus=1&reqId=29b2b2f0-1448-11eb-822b-cd5aded7e379",
            object :
                NetCallBack {
                override fun callBack(doc: Connection.Response?) {
                    val data = doc?.body().toString()
                    if ("".equals(data)) {
                        return
                    }


                    Log.e("音乐输出", data)
                    val musicData = Gson().fromJson(data, Lyric::class.java)

                    BaseActivity.activity?.runOnUiThread {
                        lrclist.clear()
                        lrclist.addAll(musicData.data.lrclist)
                        val songinfo = musicData.data.songinfo

                        var lrc = ""
                        for (it in lrclist){
                            lrc+=it.lineLyric+"\n\t"
                        }
                        geCi.setText(lrc)

                        val title = songinfo.album + "-" + songinfo.artist
                        comTitle.setText(title)


                        //歌词加载成功在加载音乐
                        initPlay(rid)
                    }

                }

                override fun callError() {

                    BaseActivity.activity?.runOnUiThread {
                        BaseActivity.closeLoading()
                        AestheticDialog.Builder(
                            BaseActivity.activity!!,
                            DialogStyle.FLASH,
                            DialogType.ERROR
                        )
                            .setTitle("提示")
                            .setMessage("加载失败！请检查您的网络")
                            .show()
                    }
                }

            })

    }

    private fun initPlay(rid:String) {

        Net.getPlay("http://www.kuwo.cn/url?format=mp3&rid=" + rid + "&response=url&type=convert_url3&br=1024kmp3&from=web&t=1603180262044&httpsStatus=1&reqId=ffce54d1-12a8-11eb-be82-f9f2120469ef",
            object :
                NetCallBack {
                override fun callBack(doc: Connection.Response?) {
                    val data = doc?.body().toString()
                    if ("".equals(data)) {
                        return
                    }


                    Log.e("音乐输出", data)
                    val musicData = Gson().fromJson(data, MusicPlay::class.java)

                    BaseActivity.activity?.runOnUiThread {
                        try {
                            mediaPlayer?.setDataSource(musicData.url)
                            mediaPlayer?.prepareAsync()
                        }catch (e:java.lang.Exception){
                            Log.e("加载资源错误",e.toString())
                            AestheticDialog.Builder(
                                BaseActivity.activity!!,
                                DialogStyle.FLASH,
                                DialogType.ERROR
                            )
                                .setTitle("提示")
                                .setMessage("加载资源失败！请重试")
                                .show()
                        }

                    }

                }

                override fun callError() {
                    BaseActivity.activity?.runOnUiThread {
                        BaseActivity.closeLoading()
                        AestheticDialog.Builder(
                            BaseActivity.activity!!,
                            DialogStyle.FLASH,
                            DialogType.ERROR
                        )
                            .setTitle("提示")
                            .setMessage("加载失败！请检查您的网络")
                            .show()
                    }
                }

            })
    }

    override fun onStart() {
        isOk = true
        super.onStart()
    }

    override fun onDestroy() {
        isOk = false
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        super.onDestroy()
    }
}

