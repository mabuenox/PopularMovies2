package com.mbuenoferrer.popularmovies.data;

import com.mbuenoferrer.popularmovies.BuildConfig;
import com.mbuenoferrer.popularmovies.R;
import com.mbuenoferrer.popularmovies.entities.Movie;

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

        return mapResults(results);
    }

    public List<Movie> getTopRated() throws IOException {

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<MovieListResponse> call = theMovieDBService.getTopRated(API_KEY);
        Response<MovieListResponse> movieListResponse = call.execute();
        List<MovieListResult> results = movieListResponse.body().getResults();

        return mapResults(results);
    }

    private List<Movie> mapResults(List<MovieListResult> results){
        List<Movie> movies = new ArrayList<>();

        for(MovieListResult result : results)
        {
            Movie movie = new Movie(result.getId(),
                    result.getTitle(),
                    result.getPosterPath(),
                    result.getOverview(),
                    result.getReleaseDate(),
                    result.getVoteAverage());

            movies.add(movie);
        }

        return movies;
    }
}
