package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoverPlayDto {


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
        @SerializedName("doubanscore")
        private String doubanscore;
        @SerializedName("rank")
        private Integer rank;
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

        public String getDoubanscore() {
            return doubanscore;
        }

        public void setDoubanscore(String doubanscore) {
            this.doubanscore = doubanscore;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
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

        public static class PlaylinksdetailDTO {
            @SerializedName("qiyi")
            private QiyiDTO qiyi;

            @SerializedName("qq")
            private QiyiDTO qq;

            @SerializedName("imgo")
            private QiyiDTO imgo;

            @SerializedName("leshi")
            private QiyiDTO leshi;

            @SerializedName("cntv")
            private QiyiDTO cntv;

            @SerializedName("sohu")
            private QiyiDTO sohu;

            @SerializedName("youku")
            private QiyiDTO youku;

            @SerializedName("xigua")
            private QiyiDTO xigua;

            @SerializedName("m1905")
            private QiyiDTO m1905;

            @SerializedName("pptv")
            private QiyiDTO pptv;

            @SerializedName("levp")
            private QiyiDTO levp;

            @SerializedName("douyin")
            private QiyiDTO douyin;

            public QiyiDTO getLevp() {
                return levp;
            }

            public void setLevp(QiyiDTO levp) {
                this.levp = levp;
            }

            public QiyiDTO getDouyin() {
                return douyin;
            }

            public void setDouyin(QiyiDTO douyin) {
                this.douyin = douyin;
            }

            public QiyiDTO getPptv() {
                return pptv;
            }

            public void setPptv(QiyiDTO pptv) {
                this.pptv = pptv;
            }

            public QiyiDTO getM1905() {
                return m1905;
            }

            public void setM1905(QiyiDTO m1905) {
                this.m1905 = m1905;
            }

            public QiyiDTO getQq() {
                return qq;
            }

            public void setQq(QiyiDTO qq) {
                this.qq = qq;
            }

            public QiyiDTO getImgo() {
                return imgo;
            }

            public void setImgo(QiyiDTO imgo) {
                this.imgo = imgo;
            }

            public QiyiDTO getLeshi() {
                return leshi;
            }

            public void setLeshi(QiyiDTO leshi) {
                this.leshi = leshi;
            }

            public QiyiDTO getCntv() {
                return cntv;
            }

            public void setCntv(QiyiDTO cntv) {
                this.cntv = cntv;
            }

            public QiyiDTO getSohu() {
                return sohu;
            }

            public void setSohu(QiyiDTO sohu) {
                this.sohu = sohu;
            }

            public QiyiDTO getYouku() {
                return youku;
            }

            public void setYouku(QiyiDTO youku) {
                this.youku = youku;
            }

            public QiyiDTO getXigua() {
                return xigua;
            }

            public void setXigua(QiyiDTO xigua) {
                this.xigua = xigua;
            }

            public QiyiDTO getQiyi() {
                return qiyi;
            }

            public void setQiyi(QiyiDTO qiyi) {
                this.qiyi = qiyi;
            }




            public static class QiyiDTO {
                @SerializedName("act")
                private String act;
                @SerializedName("api_id")
                private String apiId;
                @SerializedName("api_video_id")
                private String apiVideoId;
                @SerializedName("cdn_h_cover")
                private String cdnHCover;
                @SerializedName("cdn_v_cover")
                private String cdnVCover;
                @SerializedName("createline")
                private String createline;
                @SerializedName("default_url")
                private String defaultUrl;
                @SerializedName("description")
                private String description;
                @SerializedName("dir")
                private String dir;
                @SerializedName("duration")
                private String duration;
                @SerializedName("ent_id")
                private String entId;
                @SerializedName("free_upinfo")
                private Integer freeUpinfo;
                @SerializedName("gaea_flag")
                private String gaeaFlag;
                @SerializedName("h_cover")
                private String hCover;
                @SerializedName("id")
                private String id;
                @SerializedName("mini_url")
                private String miniUrl;
                @SerializedName("pageurl")
                private String pageurl;
                @SerializedName("programUrl")
                private String programUrl;
                @SerializedName("publine")
                private String publine;
                @SerializedName("quality")
                private String quality;
                @SerializedName("site")
                private String site;
                @SerializedName("site_source")
                private String siteSource;
                @SerializedName("sort")
                private String sort;
                @SerializedName("status")
                private String status;
                @SerializedName("swf")
                private String swf;
                @SerializedName("temp_vip")
                private Integer tempVip;
                @SerializedName("title")
                private String title;
                @SerializedName("total")
                private String total;
                @SerializedName("updateline")
                private String updateline;
                @SerializedName("upinfo")
                private String upinfo;
                @SerializedName("v_cover")
                private String vCover;
                @SerializedName("wheshow")
                private String wheshow;

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

                public String getCdnHCover() {
                    return cdnHCover;
                }

                public void setCdnHCover(String cdnHCover) {
                    this.cdnHCover = cdnHCover;
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

                public String getDefaultUrl() {
                    return defaultUrl;
                }

                public void setDefaultUrl(String defaultUrl) {
                    this.defaultUrl = defaultUrl;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getDir() {
                    return dir;
                }

                public void setDir(String dir) {
                    this.dir = dir;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getEntId() {
                    return entId;
                }

                public void setEntId(String entId) {
                    this.entId = entId;
                }

                public Integer getFreeUpinfo() {
                    return freeUpinfo;
                }

                public void setFreeUpinfo(Integer freeUpinfo) {
                    this.freeUpinfo = freeUpinfo;
                }

                public String getGaeaFlag() {
                    return gaeaFlag;
                }

                public void setGaeaFlag(String gaeaFlag) {
                    this.gaeaFlag = gaeaFlag;
                }

                public String getHCover() {
                    return hCover;
                }

                public void setHCover(String hCover) {
                    this.hCover = hCover;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMiniUrl() {
                    return miniUrl;
                }

                public void setMiniUrl(String miniUrl) {
                    this.miniUrl = miniUrl;
                }

                public String getPageurl() {
                    return pageurl;
                }

                public void setPageurl(String pageurl) {
                    this.pageurl = pageurl;
                }

                public String getProgramUrl() {
                    return programUrl;
                }

                public void setProgramUrl(String programUrl) {
                    this.programUrl = programUrl;
                }

                public String getPubline() {
                    return publine;
                }

                public void setPubline(String publine) {
                    this.publine = publine;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getSite() {
                    return site;
                }

                public void setSite(String site) {
                    this.site = site;
                }

                public String getSiteSource() {
                    return siteSource;
                }

                public void setSiteSource(String siteSource) {
                    this.siteSource = siteSource;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getSwf() {
                    return swf;
                }

                public void setSwf(String swf) {
                    this.swf = swf;
                }

                public Integer getTempVip() {
                    return tempVip;
                }

                public void setTempVip(Integer tempVip) {
                    this.tempVip = tempVip;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTotal() {
                    return total;
                }

                public void setTotal(String total) {
                    this.total = total;
                }

                public String getUpdateline() {
                    return updateline;
                }

                public void setUpdateline(String updateline) {
                    this.updateline = updateline;
                }

                public String getUpinfo() {
                    return upinfo;
                }

                public void setUpinfo(String upinfo) {
                    this.upinfo = upinfo;
                }

                public String getVCover() {
                    return vCover;
                }

                public void setVCover(String vCover) {
                    this.vCover = vCover;
                }

                public String getWheshow() {
                    return wheshow;
                }

                public void setWheshow(String wheshow) {
                    this.wheshow = wheshow;
                }
            }


        }
    }
}
