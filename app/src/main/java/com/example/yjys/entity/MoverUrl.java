package com.example.yjys.entity;

import java.util.List;

public class MoverUrl {


    private String retcode;
    private String retmsg;
    private List<CdnUrlsDTO> cdnUrls;

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

    public List<CdnUrlsDTO> getCdnUrls() {
        return cdnUrls;
    }

    public void setCdnUrls(List<CdnUrlsDTO> cdnUrls) {
        this.cdnUrls = cdnUrls;
    }

    public static class CdnUrlsDTO {
        private String vendor;
        private String url;

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
