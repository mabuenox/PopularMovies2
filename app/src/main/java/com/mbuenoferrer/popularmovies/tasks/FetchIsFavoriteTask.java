package com.mbuenoferrer.popularmovies.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.mbuenoferrer.popularmovies.data.MovieRepository;
import com.mbuenoferrer.popularmovies.entities.Movie;


public class FetchIsFavoriteTask extends AsyncTask<Movie, Void, Boolean> {

    private FetchIsFavoriteTaskListener callback;

    private Context context;

    public FetchIsFavoriteTask(Context context, FetchIsFavoriteTaskListener callback)
    {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Movie... params) {

        Boolean isFavorite = null;

        if (params.length == 0) {
            return null;
        }

        Movie movie = params[0];

        MovieRepository repository = new MovieRepository(context);

        try {
            isFavorite = repository.existsFavorite(movie);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return isFavorite;
    }

    @Override
    protected void onPostExecute(Boolean isFavorite) {
        callback.onTaskPostExecute(isFavorite);
    }
}
