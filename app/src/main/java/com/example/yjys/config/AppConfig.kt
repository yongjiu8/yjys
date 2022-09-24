package com.example.yjys.config

import com.example.yjys.entity.Line
import com.example.yjys.entity.MusicList

class AppConfig {

    companion object{
        @JvmStatic
        val homeBannerUrl = "https://api.web.360kan.com/v1/block?blockid=522"

        @JvmStatic
        val homeTvUrl = "https://api.web.360kan.com/v1/block?blockid=131&size=9"

        @JvmStatic
        val homeMoverUrl = "https://api.web.360kan.com/v1/block?blockid=90&size=9"

        @JvmStatic
        val homeZongYiUrl = "https://api.web.360kan.com/v1/block?blockid=43&size=9"

        @JvmStatic
        val homeDongManUrl = "https://api.web.360kan.com/v1/block?blockid=72&size=9"

        @JvmStatic
        val homeShaoErUrl = "https://api.web.360kan.com/v1/block?blockid=25&size=9"

        @JvmStatic
        val searchUrl : String = "https://api.so.360kan.com/index?force_v=1&from=&pageno=1&v_ap=1&tab=all"
        @JvmStatic
        val pcUrl : String = "https://api.web.360kan.com"
        @JvmStatic
        var playUrl : String = "http://17kyun.com/api.php?url="
        @JvmStatic
        val umengKey :String = "5f71818e906ad811171727d3"

        @JvmStatic
        val umAuthKey : String = "M/6egCygA+Y6kWFsDxO5Cad9DYL9uPX3l+JFJ/TAymBI3heZDRMZSfNLWC6lFpQRmOMqj35fM6ULWKzRtyAi3LQDeU5YRti1cBVx7T8bgrvwgFsclznOv0rcr5wEN8FW/dDT0Fq7T35rD3cisZ3XCDIDKPViXW5X/4hUpU4JwV1Ctieih/vVwf4wNZog76X882iqq0NyGtQXKfWsz3iu5rtiz57RcUPeBzNi2IkDj00X9rMx9AyLxxpO7iEL6oQC76XVJVMpEElBVyTHEJxsh2ISfmZmQlIiZZA4KOAf4cmDz4sm4NcoYA=="

        @JvmStatic
        val TappId :String = "101904713"

        @JvmStatic
        val bugId : String = "3459c4fc1e"

        @JvmStatic
        var lineData  = mutableListOf<Line>(
                Line("线路一","http://jsap.attakids.com/?url="),
                Line("线路二","https://jiexi.380k.com/?url="),
                Line("线路三","http://17kyun.com/api.php?url=")
        )

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