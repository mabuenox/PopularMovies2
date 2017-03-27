package com.mbuenoferrer.popularmovies.tasks;

import com.mbuenoferrer.popularmovies.entities.Movie;

import java.util.List;

public interface FetchMovieListTaskListener {
    public void onTaskPreExecute();
    public void onTaskPostExecute(List<Movie> result);
}
