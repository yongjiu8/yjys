package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDto {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("keywordData")
        private KeywordDataDTO keywordData;
        @SerializedName("longData")
        private LongDataDTO longData;
        @SerializedName("miniData")
        private Object miniData;
        @SerializedName("CondData")
        private Object condData;
        @SerializedName("SearchFilterData")
        private Object searchFilterData;
        @SerializedName("topPicData")
        private List<?> topPicData;
        @SerializedName("starData")
        private List<?> starData;
        @SerializedName("soHotData")
        private List<?> soHotData;
        @SerializedName("comm")
        private List<?> comm;
        @SerializedName("sid")
        private String sid;
        @SerializedName("cat")
        private String cat;
        @SerializedName("toplist")
        private List<ToplistDTO> toplist;
        @SerializedName("plan")
        private String plan;
        @SerializedName("sensitive")
        private Boolean sensitive;
        @SerializedName("rank")
        private List<?> rank;
        @SerializedName("adconf_svc")
        private List<?> adconfSvc;

        public KeywordDataDTO getKeywordData() {
            return keywordData;
        }

        public void setKeywordData(KeywordDataDTO keywordData) {
            this.keywordData = keywordData;
        }

        public LongDataDTO getLongData() {
            return longData;
        }

        public void setLongData(LongDataDTO longData) {
            this.longData = longData;
        }

        public Object getMiniData() {
            return miniData;
        }

        public void setMiniData(Object miniData) {
            this.miniData = miniData;
        }

        public Object getCondData() {
            return condData;
        }

        public void setCondData(Object condData) {
            this.condData = condData;
        }

        public Object getSearchFilterData() {
            return searchFilterData;
        }

        public void setSearchFilterData(Object searchFilterData) {
            this.searchFilterData = searchFilterData;
        }

        public List<?> getTopPicData() {
            return topPicData;
        }

        public void setTopPicData(List<?> topPicData) {
            this.topPicData = topPicData;
        }

        public List<?> getStarData() {
            return starData;
        }

        public void setStarData(List<?> starData) {
            this.starData = starData;
        }

        public List<?> getSoHotData() {
            return soHotData;
        }

        public void setSoHotData(List<?> soHotData) {
            this.soHotData = soHotData;
        }

        public List<?> getComm() {
            return comm;
        }

        public void setComm(List<?> comm) {
            this.comm = comm;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public List<ToplistDTO> getToplist() {
            return toplist;
        }

        public void setToplist(List<ToplistDTO> toplist) {
            this.toplist = toplist;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public Boolean getSensitive() {
            return sensitive;
        }

        public void setSensitive(Boolean sensitive) {
            this.sensitive = sensitive;
        }

        public List<?> getRank() {
            return rank;
        }

        public void setRank(List<?> rank) {
            this.rank = rank;
        }

        public List<?> getAdconfSvc() {
            return adconfSvc;
        }

        public void setAdconfSvc(List<?> adconfSvc) {
            this.adconfSvc = adconfSvc;
        }

        public static class KeywordDataDTO {
            @SerializedName("kw")
            private String kw;
            @SerializedName("origKw")
            private String origKw;
            @SerializedName("depend")
            private DependDTO depend;

            public String getKw() {
                return kw;
            }

            public void setKw(String kw) {
                this.kw = kw;
            }

            public String getOrigKw() {
                return origKw;
            }

            public void setOrigKw(String origKw) {
                this.origKw = origKw;
            }

            public DependDTO getDepend() {
                return depend;
            }

            public void setDepend(DependDTO depend) {
                this.depend = depend;
            }

            public static class DependDTO {
                @SerializedName("kw")
                private String kw;
                @SerializedName("type")
                private String type;

                public String getKw() {
                    return kw;
                }

                public void setKw(String kw) {
                    this.kw = kw;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }

        public static class LongDataDTO {
            @SerializedName("rows")
            private List<RowsDTO> rows;
            @SerializedName("countInfo")
            private CountInfoDTO countInfo;

            public List<RowsDTO> getRows() {
                return rows;
            }

            public void setRows(List<RowsDTO> rows) {
                this.rows = rows;
            }

            public CountInfoDTO getCountInfo() {
                return countInfo;
            }

            public void setCountInfo(CountInfoDTO countInfo) {
                this.countInfo = countInfo;
            }

            public static class CountInfoDTO {
                @SerializedName("showNum")
                private Integer showNum;
                @SerializedName("hideNum")
                private Integer hideNum;
                @SerializedName("hideCatsNum")
                private HideCatsNumDTO hideCatsNum;

                public Integer getShowNum() {
                    return showNum;
                }

                public void setShowNum(Integer showNum) {
                    this.showNum = showNum;
                }

                public Integer getHideNum() {
                    return hideNum;
                }

                public void setHideNum(Integer hideNum) {
                    this.hideNum = hideNum;
                }

                public HideCatsNumDTO getHideCatsNum() {
                    return hideCatsNum;
                }

                public void setHideCatsNum(HideCatsNumDTO hideCatsNum) {
                    this.hideCatsNum = hideCatsNum;
                }

                public static class HideCatsNumDTO {
                    @SerializedName("1")
                    private Integer $1;
                    @SerializedName("2")
                    private Integer $2;
                    @SerializedName("3")
                    private Integer $3;
                    @SerializedName("4")
                    private Integer $4;

                    public Integer get$1() {
                        return $1;
                    }

                    public void set$1(Integer $1) {
                        this.$1 = $1;
                    }

                    public Integer get$2() {
                        return $2;
                    }

                    public void set$2(Integer $2) {
                        this.$2 = $2;
                    }

                    public Integer get$3() {
                        return $3;
                    }

                    public void set$3(Integer $3) {
                        this.$3 = $3;
                    }

                    public Integer get$4() {
                        return $4;
                    }

                    public void set$4(Integer $4) {
                        this.$4 = $4;
                    }
                }
            }

            public static class RowsDTO {
                @SerializedName("cat_id")
                private String catId;
                @SerializedName("id")
                private String id;
                @SerializedName("en_id")
                private String enId;
                @SerializedName("cat_name")
                private String catName;
                @SerializedName("url")
                private String url;
                @SerializedName("cover")
                private String cover;
                @SerializedName("coverInfo")
                private CoverInfoDTO coverInfo;
                @SerializedName("titleTxt")
                private String titleTxt;
                @SerializedName("title")
                private String title;
                @SerializedName("titlealias")
                private String titlealias;
                @SerializedName("year")
                private String year;
                @SerializedName("description")
                private String description;
                @SerializedName("area")
                private List<String> area;
                @SerializedName("tag")
                private List<String> tag;
                @SerializedName("score")
                private String score;
                @SerializedName("qualityLv")
                private Integer qualityLv;
                @SerializedName("pos")
                private Integer pos;
                @SerializedName("actList")
                private List<String> actList;
                @SerializedName("dirList")
                private List<String> dirList;
                @SerializedName("actName")
                private String actName;
                @SerializedName("dirName")
                private String dirName;
                @SerializedName("vipSite")
                private List<String> vipSite;
                @SerializedName("vip")
                private Integer vip;
                @SerializedName("video_status")
                private String videoStatus;
                @SerializedName("outc")
                private String outc;
                @SerializedName("minilist")
                private List<?> minilist;
                @SerializedName("c")
                private String c;
                @SerializedName("seriesSite")
                private String seriesSite;
                @SerializedName("seriesPlaylinks")
                private List<?> seriesPlaylinks;
                @SerializedName("is_serial")
                private Integer isSerial;
                @SerializedName("playlinks_total")


                public String getCatId() {
                    return catId;
                }

                public void setCatId(String catId) {
                    this.catId = catId;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getEnId() {
                    return enId;
                }

                public void setEnId(String enId) {
                    this.enId = enId;
                }

                public String getCatName() {
                    return catName;
                }

                public void setCatName(String catName) {
                    this.catName = catName;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public CoverInfoDTO getCoverInfo() {
                    return coverInfo;
                }

                public void setCoverInfo(CoverInfoDTO coverInfo) {
                    this.coverInfo = coverInfo;
                }

                public String getTitleTxt() {
                    return titleTxt;
                }

                public void setTitleTxt(String titleTxt) {
                    this.titleTxt = titleTxt;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTitlealias() {
                    return titlealias;
                }

                public void setTitlealias(String titlealias) {
                    this.titlealias = titlealias;
                }

                public String getYear() {
                    return year;
                }

                public void setYear(String year) {
                    this.year = year;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<String> getArea() {
                    return area;
                }

                public void setArea(List<String> area) {
                    this.area = area;
                }

                public List<String> getTag() {
                    return tag;
                }

                public void setTag(List<String> tag) {
                    this.tag = tag;
                }

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
                    this.score = score;
                }

                public Integer getQualityLv() {
                    return qualityLv;
                }

                public void setQualityLv(Integer qualityLv) {
                    this.qualityLv = qualityLv;
                }

                public Integer getPos() {
                    return pos;
                }

                public void setPos(Integer pos) {
                    this.pos = pos;
                }

                public List<String> getActList() {
                    return actList;
                }

                public void setActList(List<String> actList) {
                    this.actList = actList;
                }

                public List<String> getDirList() {
                    return dirList;
                }

                public void setDirList(List<String> dirList) {
                    this.dirList = dirList;
                }

                public String getActName() {
                    return actName;
                }

                public void setActName(String actName) {
                    this.actName = actName;
                }

                public String getDirName() {
                    return dirName;
                }

                public void setDirName(String dirName) {
                    this.dirName = dirName;
                }

                public List<String> getVipSite() {
                    return vipSite;
                }

                public void setVipSite(List<String> vipSite) {
                    this.vipSite = vipSite;
                }

                public Integer getVip() {
                    return vip;
                }

                public void setVip(Integer vip) {
                    this.vip = vip;
                }

                public String getVideoStatus() {
                    return videoStatus;
                }

                public void setVideoStatus(String videoStatus) {
                    this.videoStatus = videoStatus;
                }

                public String getOutc() {
                    return outc;
                }

                public void setOutc(String outc) {
                    this.outc = outc;
                }

                public List<?> getMinilist() {
                    return minilist;
                }

                public void setMinilist(List<?> minilist) {
                    this.minilist = minilist;
                }

                public String getC() {
                    return c;
                }

                public void setC(String c) {
                    this.c = c;
                }

                public String getSeriesSite() {
                    return seriesSite;
                }

                public void setSeriesSite(String seriesSite) {
                    this.seriesSite = seriesSite;
                }

                public List<?> getSeriesPlaylinks() {
                    return seriesPlaylinks;
                }

                public void setSeriesPlaylinks(List<?> seriesPlaylinks) {
                    this.seriesPlaylinks = seriesPlaylinks;
                }

                public Integer getIsSerial() {
                    return isSerial;
                }

                public void setIsSerial(Integer isSerial) {
                    this.isSerial = isSerial;
                }


                public static class CoverInfoDTO {
                    @SerializedName("quality")
                    private String quality;
                    @SerializedName("duration")
                    private String duration;
                    @SerializedName("score")
                    private String score;

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public String getDuration() {
                        return duration;
                    }

                    public void setDuration(String duration) {
                        this.duration = duration;
                    }

                    public String getScore() {
                        return score;
                    }

                    public void setScore(String score) {
                        this.score = score;
                    }
                }


            }
        }

        public static class ToplistDTO {
            @SerializedName("name")
            private String name;
            @SerializedName("alias")
            private String alias;
            @SerializedName("list")
            private List<ListDTO> list;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public List<ListDTO> getList() {
                return list;
            }

            public void setList(List<ListDTO> list) {
                this.list = list;
            }

            public static class ListDTO {
                @SerializedName("title")
                private String title;
                @SerializedName("cover")
                private String cover;
                @SerializedName("url")
                private String url;
                @SerializedName("pv")
                private String pv;
                @SerializedName("c")
                private String c;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getPv() {
                    return pv;
                }

                public void setPv(String pv) {
                    this.pv = pv;
                }

                public String getC() {
                    return c;
                }

                public void setC(String c) {
                    this.c = c;
                }
            }
        }
    }
}
