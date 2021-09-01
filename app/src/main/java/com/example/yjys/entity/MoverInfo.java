package com.example.yjys.entity;

import java.util.List;

public class MoverInfo {


    private String retcode;

    private String retmsg;

    private DataDTO data;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {

        private DataDTO.EpisodeDTO episode;

        private DataDTO.PlayListDTO playList;

        public EpisodeDTO getEpisode() {
            return episode;
        }

        public void setEpisode(EpisodeDTO episode) {
            this.episode = episode;
        }

        public PlayListDTO getPlayList() {
            return playList;
        }

        public void setPlayList(PlayListDTO playList) {
            this.playList = playList;
        }

        public static class EpisodeDTO {

            private String ceId;

            private String cmId;

            private String ceCode;

            private String cmCode;

            private String episodeId;

            private String name;

            private String num;

            private String duration;

            private String clipDuration;

            private String isvip;

            private String isProject;

            public String getCeId() {
                return ceId;
            }

            public void setCeId(String ceId) {
                this.ceId = ceId;
            }

            public String getCmId() {
                return cmId;
            }

            public void setCmId(String cmId) {
                this.cmId = cmId;
            }

            public String getCeCode() {
                return ceCode;
            }

            public void setCeCode(String ceCode) {
                this.ceCode = ceCode;
            }

            public String getCmCode() {
                return cmCode;
            }

            public void setCmCode(String cmCode) {
                this.cmCode = cmCode;
            }

            public String getEpisodeId() {
                return episodeId;
            }

            public void setEpisodeId(String episodeId) {
                this.episodeId = episodeId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getClipDuration() {
                return clipDuration;
            }

            public void setClipDuration(String clipDuration) {
                this.clipDuration = clipDuration;
            }

            public String getIsvip() {
                return isvip;
            }

            public void setIsvip(String isvip) {
                this.isvip = isvip;
            }

            public String getIsProject() {
                return isProject;
            }

            public void setIsProject(String isProject) {
                this.isProject = isProject;
            }
        }


        public static class PlayListDTO {

            private List<Mp4H264DTO> mp4H264;

            public List<Mp4H264DTO> getMp4H264() {
                return mp4H264;
            }

            public void setMp4H264(List<Mp4H264DTO> mp4H264) {
                this.mp4H264 = mp4H264;
            }

            public static class Mp4H264DTO {
                private String infohash;
                private String filename;
                private String filesize;
                private String definition;
                private String definitionCode;
                private String downloadable;
                private List<?> tags;

                public String getInfohash() {
                    return infohash;
                }

                public void setInfohash(String infohash) {
                    this.infohash = infohash;
                }

                public String getFilename() {
                    return filename;
                }

                public void setFilename(String filename) {
                    this.filename = filename;
                }

                public String getFilesize() {
                    return filesize;
                }

                public void setFilesize(String filesize) {
                    this.filesize = filesize;
                }

                public String getDefinition() {
                    return definition;
                }

                public void setDefinition(String definition) {
                    this.definition = definition;
                }

                public String getDefinitionCode() {
                    return definitionCode;
                }

                public void setDefinitionCode(String definitionCode) {
                    this.definitionCode = definitionCode;
                }

                public String getDownloadable() {
                    return downloadable;
                }

                public void setDownloadable(String downloadable) {
                    this.downloadable = downloadable;
                }

                public List<?> getTags() {
                    return tags;
                }

                public void setTags(List<?> tags) {
                    this.tags = tags;
                }
            }
        }
    }
}
