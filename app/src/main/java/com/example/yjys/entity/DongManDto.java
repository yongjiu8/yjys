package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DongManDto {

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
            @SerializedName("cdncover")
            private String cdncover;
            @SerializedName("cdnvcover")
            private String cdnvcover;
            @SerializedName("comment")
            private String comment;
            @SerializedName("cover")
            private String cover;
            @SerializedName("id")
            private String id;
            @SerializedName("payment")
            private Boolean payment;
            @SerializedName("title")
            private String title;
            @SerializedName("total")
            private Integer total;
            @SerializedName("upinfo")
            private Integer upinfo;

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
        }
    }
}
