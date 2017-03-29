package com.mbuenoferrer.popularmovies.tasks;

import com.mbuenoferrer.popularmovies.entities.Video;

import java.util.List;

public interface FetchVideoListTaskListener {
    public void onTaskPreExecute();
    public void onTaskPostExecute(List<Video> result);
}
