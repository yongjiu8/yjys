package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZongYiDto {

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
            @SerializedName("cdnvcover")
            private String cdnvcover;
            @SerializedName("cover")
            private String cover;
            @SerializedName("director")
            private List<String> director;
            @SerializedName("id")
            private String id;
            @SerializedName("lastpubline")
            private Integer lastpubline;
            @SerializedName("lasttitle")
            private String lasttitle;
            @SerializedName("payment")
            private Boolean payment;
            @SerializedName("tag")
            private String tag;
            @SerializedName("title")
            private String title;
            @SerializedName("tvstation")
            private List<String> tvstation;
            @SerializedName("vip")
            private Boolean vip;

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

            public String getCdnvcover() {
                return cdnvcover;
            }

            public void setCdnvcover(String cdnvcover) {
                this.cdnvcover = cdnvcover;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public List<String> getDirector() {
                return director;
            }

            public void setDirector(List<String> director) {
                this.director = director;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getLastpubline() {
                return lastpubline;
            }

            public void setLastpubline(Integer lastpubline) {
                this.lastpubline = lastpubline;
            }

            public String getLasttitle() {
                return lasttitle;
            }

            public void setLasttitle(String lasttitle) {
                this.lasttitle = lasttitle;
            }

            public Boolean getPayment() {
                return payment;
            }

            public void setPayment(Boolean payment) {
                this.payment = payment;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<String> getTvstation() {
                return tvstation;
            }

            public void setTvstation(List<String> tvstation) {
                this.tvstation = tvstation;
            }

            public Boolean getVip() {
                return vip;
            }

            public void setVip(Boolean vip) {
                this.vip = vip;
            }
        }
    }
}
