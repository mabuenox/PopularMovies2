package com.mbuenoferrer.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.mbuenoferrer.popularmovies.data.NetworkMovieRepository;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.enums.MovieListSort;
import com.mbuenoferrer.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;


public class FetchMovieListTask extends AsyncTask<MovieListSort, Void, List<Movie>> {

    private FetchMovieListTaskListener callback;

    private Context context;

    public FetchMovieListTask(Context context, FetchMovieListTaskListener callback)
    {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onTaskPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(MovieListSort... params) {

        List<Movie> movieList = null;

        if (params.length == 0) {
            return null;
        }

        if (!NetworkUtils.isOnline(context)) {
            return null;
        }

        MovieListSort sortBy = params[0];

        NetworkMovieRepository repository = new NetworkMovieRepository();

        try {
            switch (sortBy)
            {
                case POPULAR:
                    movieList = repository.getPopular();
                    break;
                case TOP_RATED:
                    movieList = repository.getTopRated();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return movieList;
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        callback.onTaskPostExecute(movieList);
    }
}
