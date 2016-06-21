package com.henry.freakcompany.model;

import java.util.List;

/**
 * Created by henry on 2016/6/15.
 */
public class GanHuoResponse extends BaseResponseModel {

    /**
     * _id : 575ec5db421aa9297197ca9d
     * createdAt : 2016-06-13T22:40:27.551Z
     * desc : 又一个关于 Agera 的裤子👖 用法和 RxBus 基本一样的 Agera Event Bus
     * publishedAt : 2016-06-14T11:52:47.320Z
     * source : web
     * type : Android
     * url : https://github.com/drakeet/agera-event-bus
     * used : true
     * who : drakeet
     */

    private List<GanHuoModel> results;

    public List<GanHuoModel> getResults() {
        return results;
    }

    public void setResults(List<GanHuoModel> results) {
        this.results = results;
    }

    public static class GanHuoModel {

        public static final String DATE_STYLE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";     // 返回的日期格式

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
