package com.example.yjys.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutoType {

    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("iteratorEvents")
    private List<String> iteratorEvents;
    @SerializedName("label")
    private String label;
    @SerializedName("name")
    private String name;
    @SerializedName("defaultId")
    private String defaultId;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public List<String> getIteratorEvents() {
        return iteratorEvents;
    }

    public void setIteratorEvents(List<String> iteratorEvents) {
        this.iteratorEvents = iteratorEvents;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public static class DataDTO {
        @SerializedName("title")
        private String title;
        @SerializedName("id")
        private String id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
