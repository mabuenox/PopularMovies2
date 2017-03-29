package com.mbuenoferrer.popularmovies.data.api.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse {

    @SerializedName("results")
    private List<MovieListResult> results;


    public List<MovieListResult> getResults() {
        return results;
    }
}
