package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvDto {


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
            @SerializedName("cdncover")
            private String cdncover;
            @SerializedName("comment")
            private String comment;
            @SerializedName("cover")
            private String cover;
            @SerializedName("description")
            private String description;
            @SerializedName("id")
            private String id;
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
            @SerializedName("total")
            private Integer total;
            @SerializedName("upinfo")
            private Integer upinfo;

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public Integer getUpinfo() {
                return upinfo;
            }

            public void setUpinfo(Integer upinfo) {
                this.upinfo = upinfo;
            }

            public static class PlaylinksDTO {
                @SerializedName("leshi")
                private String leshi;

                public String getLeshi() {
                    return leshi;
                }

                public void setLeshi(String leshi) {
                    this.leshi = leshi;
                }
            }
        }
    }
}
