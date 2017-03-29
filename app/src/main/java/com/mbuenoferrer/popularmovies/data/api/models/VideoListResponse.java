package com.mbuenoferrer.popularmovies.data.api.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListResponse {

    @SerializedName("results")
    private List<VideoListResult> results;


    public List<VideoListResult> getResults() {
        return results;
    }
}
