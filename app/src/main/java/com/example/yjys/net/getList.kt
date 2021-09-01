package com.example.yjys.net

import com.example.yjys.entity.MusicList
import retrofit2.Call
import retrofit2.http.*


interface getList {

    @FormUrlEncoded
    @GET("http://www.kuwo.cn/api/www/bang/bang/musicList?pn=1&rn=30&httpsStatus=1&reqId=4c9685a0-12ac-11eb-b95d-ddb143e88229")
fun getList(@Query("bangId") bangId:String, @HeaderMap headers:Map<String, String>) : Call<MusicList>


}