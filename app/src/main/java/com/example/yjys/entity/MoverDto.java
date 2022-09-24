package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoverDto {

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
        @SerializedName("current_page")
        private Integer currentPage;
        @SerializedName("movies")
        private List<MoviesDTO> movies;
        @SerializedName("total")
        private Integer total;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public List<MoviesDTO> getMovies() {
            return movies;
        }

        public void setMovies(List<MoviesDTO> movies) {
            this.movies = movies;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public static class MoviesDTO {
            @SerializedName("actor")
            private List<String> actor;
            @SerializedName("area")
            private List<String> area;
            @SerializedName("cdncover")
            private String cdncover;
            @SerializedName("comment")
            private String comment;
            @SerializedName("cover")
            private String cover;
            @SerializedName("description")
            private String description;
            @SerializedName("director")
            private List<String> director;
            @SerializedName("doubanscore")
            private String doubanscore;
            @SerializedName("id")
            private String id;
            @SerializedName("moviecategory")
            private List<String> moviecategory;
            @SerializedName("payment")
            private Boolean payment;
            @SerializedName("playlink_sites")
            private List<String> playlinkSites;
            @SerializedName("playlinks")
            private PlaylinksDTO playlinks;
            @SerializedName("pubdate")
            private String pubdate;
            @SerializedName("score")
            private Double score;
            @SerializedName("title")
            private String title;
            @SerializedName("vip")
            private Boolean vip;

            public List<String> getActor() {
                return actor;
            }

            public void setActor(List<String> actor) {
                this.actor = actor;
            }

            public List<String> getArea() {
                return area;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }

            public String getCdncover() {
                return cdncover;
            }

            public void setCdncover(String cdncover) {
                this.cdncover = cdncover;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<String> getDirector() {
                return director;
            }

            public void setDirector(List<String> director) {
                this.director = director;
            }

            public String getDoubanscore() {
                return doubanscore;
            }

            public void setDoubanscore(String doubanscore) {
                this.doubanscore = doubanscore;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<String> getMoviecategory() {
                return moviecategory;
            }

            public void setMoviecategory(List<String> moviecategory) {
                this.moviecategory = moviecategory;
            }

            public Boolean getPayment() {
                return payment;
            }

            public void setPayment(Boolean payment) {
                this.payment = payment;
            }

            public List<String> getPlaylinkSites() {
                return playlinkSites;
            }

            public void setPlaylinkSites(List<String> playlinkSites) {
                this.playlinkSites = playlinkSites;
            }

            public PlaylinksDTO getPlaylinks() {
                return playlinks;
            }

            public void setPlaylinks(PlaylinksDTO playlinks) {
                this.playlinks = playlinks;
            }

            public String getPubdate() {
                return pubdate;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Boolean getVip() {
                return vip;
            }

            public void setVip(Boolean vip) {
                this.vip = vip;
            }

            public static class PlaylinksDTO {
                @SerializedName("douyin")
                private String douyin;
                @SerializedName("imgo")
                private String imgo;
                @SerializedName("leshi")
                private String leshi;
                @SerializedName("m1905")
                private String m1905;
                @SerializedName("qiyi")
                private String qiyi;
                @SerializedName("qq")
                private String qq;
                @SerializedName("xigua")
                private String xigua;
                @SerializedName("youku")
                private String youku;

                public String getDouyin() {
                    return douyin;
                }

                public void setDouyin(String douyin) {
                    this.douyin = douyin;
                }

                public String getImgo() {
                    return imgo;
                }

                public void setImgo(String imgo) {
                    this.imgo = imgo;
                }

                public String getLeshi() {
                    return leshi;
                }

                public void setLeshi(String leshi) {
                    this.leshi = leshi;
                }

                public String getM1905() {
                    return m1905;
                }

                public void setM1905(String m1905) {
                    this.m1905 = m1905;
                }

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

                public String getXigua() {
                    return xigua;
                }

                public void setXigua(String xigua) {
                    this.xigua = xigua;
                }

                public String getYouku() {
                    return youku;
                }

                public void setYouku(String youku) {
                    this.youku = youku;
                }
            }
        }
    }
}
