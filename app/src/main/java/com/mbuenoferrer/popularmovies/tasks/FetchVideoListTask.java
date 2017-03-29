package com.mbuenoferrer.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.mbuenoferrer.popularmovies.data.NetworkMovieRepository;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.entities.Video;
import com.mbuenoferrer.popularmovies.enums.MovieListSort;
import com.mbuenoferrer.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;


public class FetchVideoListTask extends AsyncTask<Integer, Void, List<Video>> {

    private FetchVideoListTaskListener callback;

    private Context context;

    public FetchVideoListTask(Context context, FetchVideoListTaskListener callback)
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
    protected List<Video> doInBackground(Integer... params) {

        List<Video> videoList = null;

        if (params.length == 0) {
            return null;
        }

        if (!NetworkUtils.isOnline(context)) {
            return null;
        }

        int movieId = params[0];

        NetworkMovieRepository repository = new NetworkMovieRepository();

        try {
            videoList = repository.getVideos(movieId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return videoList;
    }

    @Override
    protected void onPostExecute(List<Video> videoList) {
        callback.onTaskPostExecute(videoList);
    }
}
