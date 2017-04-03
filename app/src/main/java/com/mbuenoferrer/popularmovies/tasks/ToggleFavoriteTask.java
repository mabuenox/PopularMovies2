package com.mbuenoferrer.popularmovies.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.mbuenoferrer.popularmovies.data.MovieRepository;
import com.mbuenoferrer.popularmovies.data.db.FavoriteMovieColumns;
import com.mbuenoferrer.popularmovies.data.db.MoviesProvider;
import com.mbuenoferrer.popularmovies.entities.Movie;


public class ToggleFavoriteTask extends AsyncTask<Movie, Void, Void> {

    private ToggleFavoriteTaskListener callback;

    private Context context;

    public ToggleFavoriteTask(Context context, ToggleFavoriteTaskListener callback)
    {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Movie... params) {

        Uri uri = null;

        if (params.length == 0) {
            return null;
        }

        Movie movie = params[0];

        MovieRepository repository = new MovieRepository(context);

        try {
            repository.toggleFavorite(movie);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.onTaskPostExecute();
    }
}
