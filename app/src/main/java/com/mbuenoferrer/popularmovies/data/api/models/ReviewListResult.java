package com.mbuenoferrer.popularmovies.data.api.models;

import com.google.gson.annotations.SerializedName;

public class ReviewListResult {

    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;


    public String getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }
    public String getUrl() {
        return url;
    }
}
