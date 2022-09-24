package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeZongYiDto {

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
        @SerializedName("total")
        private Integer total;
        @SerializedName("lists")
        private List<ListsDTO> lists;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<ListsDTO> getLists() {
            return lists;
        }

        public void setLists(List<ListsDTO> lists) {
            this.lists = lists;
        }

        public static class ListsDTO {
            @SerializedName("cat")
            private String cat;
            @SerializedName("ent_id")
            private String entId;
            @SerializedName("upinfo")
            private Integer upinfo;
            @SerializedName("publidate")
            private String publidate;
            @SerializedName("url")
            private String url;
            @SerializedName("actor")
            private List<String> actor;
            @SerializedName("title")
            private String title;
            @SerializedName("comment")
            private String comment;
            @SerializedName("pic_lists")
            private List<PicListsDTO> picLists;
            @SerializedName("total")
            private Integer total;
            @SerializedName("last")
            private Integer last;
            @SerializedName("duration")
            private Integer duration;
            @SerializedName("vip")
            private Boolean vip;
            @SerializedName("status")
            private String status;

            public String getCat() {
                return cat;
            }

            public void setCat(String cat) {
                this.cat = cat;
            }

            public String getEntId() {
                return entId;
            }

            public void setEntId(String entId) {
                this.entId = entId;
            }

            public Integer getUpinfo() {
                return upinfo;
            }

            public void setUpinfo(Integer upinfo) {
                this.upinfo = upinfo;
            }

            public String getPublidate() {
                return publidate;
            }

            public void setPublidate(String publidate) {
                this.publidate = publidate;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<String> getActor() {
                return actor;
            }

            public void setActor(List<String> actor) {
                this.actor = actor;
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

            public List<PicListsDTO> getPicLists() {
                return picLists;
            }

            public void setPicLists(List<PicListsDTO> picLists) {
                this.picLists = picLists;
            }

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public Integer getLast() {
                return last;
            }

            public void setLast(Integer last) {
                this.last = last;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }

            public Boolean getVip() {
                return vip;
            }

            public void setVip(Boolean vip) {
                this.vip = vip;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public static class PicListsDTO {
                @SerializedName("pic_type")
                private String picType;
                @SerializedName("url")
                private String url;

                public String getPicType() {
                    return picType;
                }

                public void setPicType(String picType) {
                    this.picType = picType;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
