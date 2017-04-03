package com.mbuenoferrer.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.mbuenoferrer.popularmovies.data.MovieRepository;
import com.mbuenoferrer.popularmovies.entities.Review;
import com.mbuenoferrer.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.util.List;


public class FetchReviewListTask extends AsyncTask<Integer, Void, List<Review>> {

    private FetchReviewListTaskListener callback;

    private Context context;

    public FetchReviewListTask(Context context, FetchReviewListTaskListener callback)
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
    protected List<Review> doInBackground(Integer... params) {

        List<Review> reviewList = null;

        if (params.length == 0) {
            return null;
        }

        if (!NetworkUtils.isOnline(context)) {
            return null;
        }

        int movieId = params[0];

        MovieRepository repository = new MovieRepository(context);

        try {
            reviewList = repository.getReviews(movieId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return reviewList;
    }

    @Override
    protected void onPostExecute(List<Review> reviewList) {
        callback.onTaskPostExecute(reviewList);
    }
}
