package com.mbuenoferrer.popularmovies.data;

import com.google.gson.annotations.SerializedName;

public class VideoListResult {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("key")
    private String key;

    @SerializedName("site")
    private String site;


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getKey() {
        return key;
    }
    public String getSite() {
        return site;
    }
}
