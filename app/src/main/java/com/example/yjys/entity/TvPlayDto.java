package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvPlayDto {

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
        @SerializedName("comment")
        private String comment;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;
        @SerializedName("upinfo")
        private Integer upinfo;
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
        @SerializedName("rank")
        private Integer rank;
        @SerializedName("allupinfo")
        private AllupinfoDTO allupinfo;
        @SerializedName("playlinks")
        private PlaylinksDTO playlinks;
        @SerializedName("allepidetail")
        private AllepidetailDTO allepidetail;
        @SerializedName("playlinksdetail")
        private PlaylinksdetailDTO playlinksdetail;
        @SerializedName("playlink_sites")
        private List<String> playlinkSites;
        @SerializedName("shaoerrank")
        private Integer shaoerrank;
        @SerializedName("isshaoer")
        private Integer isshaoer;
        @SerializedName("is_shaoer")
        private Object isShaoer;
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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getUpinfo() {
            return upinfo;
        }

        public void setUpinfo(Integer upinfo) {
            this.upinfo = upinfo;
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

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public AllupinfoDTO getAllupinfo() {
            return allupinfo;
        }

        public void setAllupinfo(AllupinfoDTO allupinfo) {
            this.allupinfo = allupinfo;
        }

        public PlaylinksDTO getPlaylinks() {
            return playlinks;
        }

        public void setPlaylinks(PlaylinksDTO playlinks) {
            this.playlinks = playlinks;
        }

        public AllepidetailDTO getAllepidetail() {
            return allepidetail;
        }

        public void setAllepidetail(AllepidetailDTO allepidetail) {
            this.allepidetail = allepidetail;
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

        public Integer getShaoerrank() {
            return shaoerrank;
        }

        public void setShaoerrank(Integer shaoerrank) {
            this.shaoerrank = shaoerrank;
        }

        public Integer getIsshaoer() {
            return isshaoer;
        }

        public void setIsshaoer(Integer isshaoer) {
            this.isshaoer = isshaoer;
        }

        public Object getIsShaoer() {
            return isShaoer;
        }

        public void setIsShaoer(Object isShaoer) {
            this.isShaoer = isShaoer;
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

            public String getQiyi() {
                return qiyi;
            }

            public void setQiyi(String qiyi) {
                this.qiyi = qiyi;
            }
        }

        public static class PlaylinksDTO {
            @SerializedName("qiyi")
            private String qiyi;

            public String getQiyi() {
                return qiyi;
            }

            public void setQiyi(String qiyi) {
                this.qiyi = qiyi;
            }
        }

        public static class AllepidetailDTO {
            @SerializedName("qiyi")
            private List<QiyiDTO> qiyi;

            @SerializedName("qq")
            private List<QiyiDTO> qq;

            @SerializedName("imgo")
            private List<QiyiDTO> imgo;

            @SerializedName("leshi")
            private List<QiyiDTO> leshi;

            @SerializedName("cntv")
            private List<QiyiDTO> cntv;

            @SerializedName("sohu")
            private List<QiyiDTO> sohu;

            @SerializedName("youku")
            private List<QiyiDTO> youku;

            @SerializedName("xigua")
            private List<QiyiDTO> xigua;

            @SerializedName("m1905")
            private List<QiyiDTO> m1905;

            @SerializedName("pptv")
            private List<QiyiDTO> pptv;

            @SerializedName("levp")
            private List<QiyiDTO> levp;

            @SerializedName("douyin")
            private List<QiyiDTO> douyin;

            public List<QiyiDTO> getLevp() {
                return levp;
            }

            public void setLevp(List<QiyiDTO> levp) {
                this.levp = levp;
            }

            public List<QiyiDTO> getDouyin() {
                return douyin;
            }

            public void setDouyin(List<QiyiDTO> douyin) {
                this.douyin = douyin;
            }

            public List<QiyiDTO> getPptv() {
                return pptv;
            }

            public void setPptv(List<QiyiDTO> pptv) {
                this.pptv = pptv;
            }

            public List<QiyiDTO> getM1905() {
                return m1905;
            }

            public void setM1905(List<QiyiDTO> m1905) {
                this.m1905 = m1905;
            }

            public List<QiyiDTO> getXigua() {
                return xigua;
            }

            public void setXigua(List<QiyiDTO> xigua) {
                this.xigua = xigua;
            }

            public List<QiyiDTO> getQq() {
                return qq;
            }

            public void setQq(List<QiyiDTO> qq) {
                this.qq = qq;
            }

            public List<QiyiDTO> getImgo() {
                return imgo;
            }

            public void setImgo(List<QiyiDTO> imgo) {
                this.imgo = imgo;
            }

            public List<QiyiDTO> getLeshi() {
                return leshi;
            }

            public void setLeshi(List<QiyiDTO> leshi) {
                this.leshi = leshi;
            }

            public List<QiyiDTO> getCntv() {
                return cntv;
            }

            public void setCntv(List<QiyiDTO> cntv) {
                this.cntv = cntv;
            }

            public List<QiyiDTO> getSohu() {
                return sohu;
            }

            public void setSohu(List<QiyiDTO> sohu) {
                this.sohu = sohu;
            }

            public List<QiyiDTO> getYouku() {
                return youku;
            }

            public void setYouku(List<QiyiDTO> youku) {
                this.youku = youku;
            }

            public List<QiyiDTO> getQiyi() {
                return qiyi;
            }

            public void setQiyi(List<QiyiDTO> qiyi) {
                this.qiyi = qiyi;
            }

            public static class QiyiDTO {
                @SerializedName("id")
                private String id;
                @SerializedName("playlink_num")
                private String playlinkNum;
                @SerializedName("url")
                private String url;
                @SerializedName("v_cover")
                private String vCover;
                @SerializedName("is_vip")
                private String isVip;
                @SerializedName("api_id")
                private String apiId;
                @SerializedName("api_video_id")
                private String apiVideoId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPlaylinkNum() {
                    return playlinkNum;
                }

                public void setPlaylinkNum(String playlinkNum) {
                    this.playlinkNum = playlinkNum;
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

                public String getIsVip() {
                    return isVip;
                }

                public void setIsVip(String isVip) {
                    this.isVip = isVip;
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
            }
        }

        public static class PlaylinksdetailDTO {
            @SerializedName("qiyi")
            private QiyiDTO qiyi;

            public QiyiDTO getQiyi() {
                return qiyi;
            }

            public void setQiyi(QiyiDTO qiyi) {
                this.qiyi = qiyi;
            }

            public static class QiyiDTO {
                @SerializedName("id")
                private String id;
                @SerializedName("site")
                private String site;
                @SerializedName("status")
                private String status;
                @SerializedName("default_url")
                private String defaultUrl;
                @SerializedName("api_id")
                private String apiId;
                @SerializedName("api_video_id")
                private String apiVideoId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getSite() {
                    return site;
                }

                public void setSite(String site) {
                    this.site = site;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getDefaultUrl() {
                    return defaultUrl;
                }

                public void setDefaultUrl(String defaultUrl) {
                    this.defaultUrl = defaultUrl;
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
            }
        }
    }
}
