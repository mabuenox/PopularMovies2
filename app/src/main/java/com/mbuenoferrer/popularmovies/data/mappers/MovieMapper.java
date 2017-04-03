package com.mbuenoferrer.popularmovies.data.mappers;

import android.database.Cursor;

import com.mbuenoferrer.popularmovies.data.api.models.MovieListResult;
import com.mbuenoferrer.popularmovies.data.db.FavoriteMovieColumns;
import com.mbuenoferrer.popularmovies.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public static List<Movie> map(List<MovieListResult> results){
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

    public static List<Movie> map(Cursor cursor){
        List<Movie> movies = new ArrayList<>();

        if(cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Movie movie = new Movie(
                            cursor.getInt(FavoriteMovieColumns.ID_INDEX),
                            cursor.getString(FavoriteMovieColumns.TITLE_INDEX),
                            cursor.getString(FavoriteMovieColumns.POSTER_INDEX),
                            cursor.getString(FavoriteMovieColumns.SYNOPSIS_INDEX),
                            cursor.getString(FavoriteMovieColumns.RELEASE_DATE_INDEX),
                            cursor.getDouble(FavoriteMovieColumns.USER_RATING_INDEX));

                    movies.add(movie);
                }
            } finally {
                cursor.close();
            }
        }

        return movies;
    }
}
