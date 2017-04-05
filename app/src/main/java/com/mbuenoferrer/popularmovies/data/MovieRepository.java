package com.mbuenoferrer.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.mbuenoferrer.popularmovies.BuildConfig;
import com.mbuenoferrer.popularmovies.data.api.TheMovieDBService;
import com.mbuenoferrer.popularmovies.data.api.models.MovieListResponse;
import com.mbuenoferrer.popularmovies.data.api.models.MovieListResult;
import com.mbuenoferrer.popularmovies.data.api.models.ReviewListResponse;
import com.mbuenoferrer.popularmovies.data.api.models.ReviewListResult;
import com.mbuenoferrer.popularmovies.data.api.models.VideoListResponse;
import com.mbuenoferrer.popularmovies.data.api.models.VideoListResult;
import com.mbuenoferrer.popularmovies.data.db.FavoriteMovieColumns;
import com.mbuenoferrer.popularmovies.data.db.MoviesProvider;
import com.mbuenoferrer.popularmovies.data.mappers.MovieMapper;
import com.mbuenoferrer.popularmovies.data.mappers.ReviewMapper;
import com.mbuenoferrer.popularmovies.data.mappers.VideoMapper;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.entities.Review;
import com.mbuenoferrer.popularmovies.entities.Video;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private Context context;

    private Retrofit retrofit;

    private String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public MovieRepository(Context context) {

        this.context = context;

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

    public List<Movie> getFavorites() throws IOException {

        Cursor cursor = context.getContentResolver().query(MoviesProvider.Movies.CONTENT_URI,
                null,
                null,
                null,
                null);

        return MovieMapper.map(cursor);
    }

    public List<Video> getVideos(int movieId) throws IOException {

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<VideoListResponse> call = theMovieDBService.getVideos(movieId, API_KEY);
        Response<VideoListResponse> videoListResponse = call.execute();
        List<VideoListResult> results = videoListResponse.body().getResults();

        return VideoMapper.map(results);
    }

    public List<Review> getReviews(int movieId) throws IOException {

        TheMovieDBService theMovieDBService = retrofit.create(TheMovieDBService.class);
        Call<ReviewListResponse> call = theMovieDBService.getReviews(movieId, API_KEY);
        Response<ReviewListResponse> reviewListResponse = call.execute();
        List<ReviewListResult> results = reviewListResponse.body().getResults();

        return ReviewMapper.map(results);
    }

    public void toggleFavorite(Movie movie) {

        boolean exists = existsFavorite(movie);

        if(exists){
            removeFavorite(movie);
        }
        else {
            addFavorite(movie);
        }
    }

    private void addFavorite(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteMovieColumns.ID, movie.getId());
        contentValues.put(FavoriteMovieColumns.TITLE, movie.getTitle());
        contentValues.put(FavoriteMovieColumns.POSTER, movie.getPoster());
        contentValues.put(FavoriteMovieColumns.SYNOPSIS, movie.getSynopsis());
        contentValues.put(FavoriteMovieColumns.RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(FavoriteMovieColumns.USER_RATING, movie.getUserRating());
        context.getContentResolver().insert(MoviesProvider.Movies.CONTENT_URI, contentValues);
    }

    private void removeFavorite(Movie movie){

        String stringId = Integer.toString(movie.getId());
        Uri uri = MoviesProvider.Movies.CONTENT_URI;
        //uri = uri.buildUpon().appendPath(stringId).build();

        context.getContentResolver().delete(uri, FavoriteMovieColumns.ID + "=" + movie.getId(), null);
    }

    public boolean existsFavorite(Movie movie) {
        boolean exists = false;

        Cursor cursor = context.getContentResolver().query(MoviesProvider.Movies.CONTENT_URI,
                null,
                FavoriteMovieColumns.ID + "=" + movie.getId(),
                null,
                null);

        List<Movie> matchesMovies = MovieMapper.map(cursor);

        if(!matchesMovies.isEmpty()) {
            exists = true;
        }

        return exists;

    }
}
