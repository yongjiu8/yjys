package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZongYiPlayDto {

    @SerializedName("data")
    private DataDTO data;
    @SerializedName("errno")
    private Integer errno;
    @SerializedName("msg")
    private String msg;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("ent_id")
        private String entId;
        @SerializedName("title")
        private String title;
        @SerializedName("comment")
        private String comment;
        @SerializedName("upinfo")
        private Integer upinfo;
        @SerializedName("description")
        private String description;
        @SerializedName("moviecategory")
        private List<String> moviecategory;
        @SerializedName("director")
        private List<String> director;
        @SerializedName("pubdate")
        private String pubdate;
        @SerializedName("area")
        private List<String> area;
        @SerializedName("actor")
        private List<String> actor;
        @SerializedName("cdncover")
        private String cdncover;
        @SerializedName("total")
        private Integer total;
        @SerializedName("allupinfo")
        private AllupinfoDTO allupinfo;
        @SerializedName("tvstation")
        private List<String> tvstation;
        @SerializedName("rank")
        private Integer rank;
        @SerializedName("playlinks")
        private PlaylinksDTO playlinks;
        @SerializedName("defaultepisode")
        private List<DefaultepisodeDTO> defaultepisode;
        @SerializedName("defaultdatetags")
        private Object defaultdatetags;
        @SerializedName("playlinksdetail")
        private PlaylinksdetailDTO playlinksdetail;
        @SerializedName("playlink_sites")
        private List<String> playlinkSites;
        @SerializedName("vip")
        private Boolean vip;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEntId() {
            return entId;
        }

        public void setEntId(String entId) {
            this.entId = entId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Integer getUpinfo() {
            return upinfo;
        }

        public void setUpinfo(Integer upinfo) {
            this.upinfo = upinfo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getMoviecategory() {
            return moviecategory;
        }

        public void setMoviecategory(List<String> moviecategory) {
            this.moviecategory = moviecategory;
        }

        public List<String> getDirector() {
            return director;
        }

        public void setDirector(List<String> director) {
            this.director = director;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }

        public List<String> getActor() {
            return actor;
        }

        public void setActor(List<String> actor) {
            this.actor = actor;
        }

        public String getCdncover() {
            return cdncover;
        }

        public void setCdncover(String cdncover) {
            this.cdncover = cdncover;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public AllupinfoDTO getAllupinfo() {
            return allupinfo;
        }

        public void setAllupinfo(AllupinfoDTO allupinfo) {
            this.allupinfo = allupinfo;
        }

        public List<String> getTvstation() {
            return tvstation;
        }

        public void setTvstation(List<String> tvstation) {
            this.tvstation = tvstation;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public PlaylinksDTO getPlaylinks() {
            return playlinks;
        }

        public void setPlaylinks(PlaylinksDTO playlinks) {
            this.playlinks = playlinks;
        }

        public List<DefaultepisodeDTO> getDefaultepisode() {
            return defaultepisode;
        }

        public void setDefaultepisode(List<DefaultepisodeDTO> defaultepisode) {
            this.defaultepisode = defaultepisode;
        }

        public Object getDefaultdatetags() {
            return defaultdatetags;
        }

        public void setDefaultdatetags(Object defaultdatetags) {
            this.defaultdatetags = defaultdatetags;
        }

        public PlaylinksdetailDTO getPlaylinksdetail() {
            return playlinksdetail;
        }

        public void setPlaylinksdetail(PlaylinksdetailDTO playlinksdetail) {
            this.playlinksdetail = playlinksdetail;
        }


        public List<String> getPlaylinkSites() {
            return playlinkSites;
        }

        public void setPlaylinkSites(List<String> playlinkSites) {
            this.playlinkSites = playlinkSites;
        }

        public Boolean getVip() {
            return vip;
        }

        public void setVip(Boolean vip) {
            this.vip = vip;
        }

        public static class AllupinfoDTO {
            @SerializedName("qiyi")
            private String qiyi;
            @SerializedName("qq")
            private String qq;
            @SerializedName("youku")
            private String youku;

            public String getQiyi() {
                return qiyi;
            }

            public void setQiyi(String qiyi) {
                this.qiyi = qiyi;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getYouku() {
                return youku;
            }

            public void setYouku(String youku) {
                this.youku = youku;
            }
        }

        public static class PlaylinksDTO {
            @SerializedName("qiyi")
            private String qiyi;
            @SerializedName("qq")
            private String qq;
            @SerializedName("youku")
            private String youku;

            public String getQiyi() {
                return qiyi;
            }

            public void setQiyi(String qiyi) {
                this.qiyi = qiyi;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getYouku() {
                return youku;
            }

            public void setYouku(String youku) {
                this.youku = youku;
            }
        }

        public static class PlaylinksdetailDTO {

        }


        public static class DefaultepisodeDTO {
            @SerializedName("act")
            private String act;
            @SerializedName("api_id")
            private String apiId;
            @SerializedName("api_video_id")
            private String apiVideoId;
            @SerializedName("cdn_v_cover")
            private String cdnVCover;
            @SerializedName("createline")
            private String createline;
            @SerializedName("duration")
            private String duration;
            @SerializedName("id")
            private String id;
            @SerializedName("is_vip")
            private String isVip;
            @SerializedName("mini_url")
            private String miniUrl;
            @SerializedName("name")
            private String name;
            @SerializedName("period")
            private String period;
            @SerializedName("period_alias")
            private String periodAlias;
            @SerializedName("playlink_num")
            private String playlinkNum;
            @SerializedName("programUrl")
            private String programUrl;
            @SerializedName("pubdate")
            private String pubdate;
            @SerializedName("sort")
            private String sort;
            @SerializedName("swf")
            private String swf;
            @SerializedName("updateline")
            private String updateline;
            @SerializedName("url")
            private String url;
            @SerializedName("v_cover")
            private String vCover;

            public String getAct() {
                return act;
            }

            public void setAct(String act) {
                this.act = act;
            }

            public String getApiId() {
                return apiId;
            }

            public void setApiId(String apiId) {
                this.apiId = apiId;
            }

            public String getApiVideoId() {
                return apiVideoId;
            }

            public void setApiVideoId(String apiVideoId) {
                this.apiVideoId = apiVideoId;
            }

            public String getCdnVCover() {
                return cdnVCover;
            }

            public void setCdnVCover(String cdnVCover) {
                this.cdnVCover = cdnVCover;
            }

            public String getCreateline() {
                return createline;
            }

            public void setCreateline(String createline) {
                this.createline = createline;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsVip() {
                return isVip;
            }

            public void setIsVip(String isVip) {
                this.isVip = isVip;
            }

            public String getMiniUrl() {
                return miniUrl;
            }

            public void setMiniUrl(String miniUrl) {
                this.miniUrl = miniUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPeriod() {
                return period;
            }

            public void setPeriod(String period) {
                this.period = period;
            }

            public String getPeriodAlias() {
                return periodAlias;
            }

            public void setPeriodAlias(String periodAlias) {
                this.periodAlias = periodAlias;
            }

            public String getPlaylinkNum() {
                return playlinkNum;
            }

            public void setPlaylinkNum(String playlinkNum) {
                this.playlinkNum = playlinkNum;
            }

            public String getProgramUrl() {
                return programUrl;
            }

            public void setProgramUrl(String programUrl) {
                this.programUrl = programUrl;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getSwf() {
                return swf;
            }

            public void setSwf(String swf) {
                this.swf = swf;
            }

            public String getUpdateline() {
                return updateline;
            }

            public void setUpdateline(String updateline) {
                this.updateline = updateline;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getVCover() {
                return vCover;
            }

            public void setVCover(String vCover) {
                this.vCover = vCover;
            }
        }
    }
}
