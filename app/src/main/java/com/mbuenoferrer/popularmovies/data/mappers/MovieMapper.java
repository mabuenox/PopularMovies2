package com.mbuenoferrer.popularmovies.data.mappers;

import com.mbuenoferrer.popularmovies.data.api.models.MovieListResult;
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
}
