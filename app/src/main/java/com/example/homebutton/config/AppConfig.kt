package com.example.homebutton.config

import com.example.homebutton.entity.MusicList

class AppConfig {

    companion object{
        @JvmStatic
        val mobileUrl : String = "http://m.360kan.com/"
        @JvmStatic
        val pcUrl : String = "https://www.360kan.com"
        @JvmStatic
        var playUrl : String = "http://17kyun.com/api.php?url="
        @JvmStatic
        val umengKey :String = "5f71818e906ad811171727d3"
        @JvmStatic
        val TappId :String = "101904713"

        @JvmStatic
        val bugId : String = "3459c4fc1e"

        @JvmStatic
        var lineData  = mutableListOf<String>("http://jsap.attakids.com/?url=","https://jiexi.380k.com/?url=","http://17kyun.com/api.php?url=")

        @JvmStatic
        var shareTitle :String = "永久影视-免费看全网VIP电影"

        @JvmStatic
        var shareCount :String = "院线抢先，超前点播，VIP视频，付费视频，全部免费，永久影视-永久免费"

        @JvmStatic
        var shareUrl :String = "https://eonml.cn/post/30.html"

        @JvmStatic
        var shareImg :String = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603103963228&di=1cd693b24aacea35ca8bc896ab5a1d12&imgtype=0&src=http%3A%2F%2Fpx.thea.cn%2FPublic%2FUpload%2FUploadfiles%2Fimage%2F20190726%2F20190726145044_61360.jpg"

        @JvmStatic
        val caoList = mutableListOf<String>()

        @JvmStatic
        val domonUrl = "http://d.eonml.cn/api/yjys.txt"

        @JvmStatic
        var MusicDataList = mutableListOf<MusicList.DataBean.MusicListBean>()
    }
}