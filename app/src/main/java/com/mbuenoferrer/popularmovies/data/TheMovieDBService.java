package com.mbuenoferrer.popularmovies.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieDBService {

    @GET("movie/popular")
    Call<MovieListResponse> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieListResponse> getTopRated(@Query("api_key") String apiKey);

}
