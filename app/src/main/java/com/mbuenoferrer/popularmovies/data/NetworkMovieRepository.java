package com.mbuenoferrer.popularmovies.data;

import com.mbuenoferrer.popularmovies.BuildConfig;
import com.mbuenoferrer.popularmovies.data.mappers.MovieMapper;
import com.mbuenoferrer.popularmovies.data.mappers.VideoMapper;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.entities.Video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkMovieRepository {

    private Retrofit retrofit;

    private String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public NetworkMovieRepository() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public List<Movie> getPopular() throws IOException {

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<MovieListResponse> call = theMovieDBService.getPopular(API_KEY);
        Response<MovieListResponse> movieListResponse = call.execute();
        List<MovieListResult> results = movieListResponse.body().getResults();

        return MovieMapper.map(results);
    }

    public List<Movie> getTopRated() throws IOException {

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<MovieListResponse> call = theMovieDBService.getTopRated(API_KEY);
        Response<MovieListResponse> movieListResponse = call.execute();
        List<MovieListResult> results = movieListResponse.body().getResults();

        return MovieMapper.map(results);
    }

    public List<Video> getVideos(int movieId) throws IOException {

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<VideoListResponse> call = theMovieDBService.getVideos(movieId, API_KEY);
        Response<VideoListResponse> videoListResponse = call.execute();
        List<VideoListResult> results = videoListResponse.body().getResults();

        return VideoMapper.map(results);
    }


}
