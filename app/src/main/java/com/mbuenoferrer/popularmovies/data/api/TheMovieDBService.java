package com.mbuenoferrer.popularmovies.data.api;

import com.mbuenoferrer.popularmovies.data.api.models.MovieListResponse;
import com.mbuenoferrer.popularmovies.data.api.models.ReviewListResponse;
import com.mbuenoferrer.popularmovies.data.api.models.VideoListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDBService {

    @GET("movie/popular")
    Call<MovieListResponse> getPopular(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieListResponse> getTopRated(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoListResponse> getVideos(@Path("id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewListResponse> getReviews(@Path("id") int movieId, @Query("api_key") String apiKey);

}
