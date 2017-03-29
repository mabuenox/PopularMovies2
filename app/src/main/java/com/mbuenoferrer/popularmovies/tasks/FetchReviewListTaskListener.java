package com.mbuenoferrer.popularmovies.tasks;

import com.mbuenoferrer.popularmovies.entities.Review;
import com.mbuenoferrer.popularmovies.entities.Video;

import java.util.List;

public interface FetchReviewListTaskListener {
    public void onTaskPreExecute();
    public void onTaskPostExecute(List<Review> result);
}
