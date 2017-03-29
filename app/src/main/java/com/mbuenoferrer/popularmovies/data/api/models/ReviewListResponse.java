package com.mbuenoferrer.popularmovies.data.api.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewListResponse {

    @SerializedName("results")
    private List<ReviewListResult> results;


    public List<ReviewListResult> getResults() {
        return results;
    }
}
