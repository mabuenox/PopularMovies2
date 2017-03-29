package com.mbuenoferrer.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbuenoferrer.popularmovies.adapters.MovieListAdapter;
import com.mbuenoferrer.popularmovies.adapters.VideoListAdapter;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.entities.Video;
import com.mbuenoferrer.popularmovies.enums.MovieListSort;
import com.mbuenoferrer.popularmovies.tasks.FetchMovieListTask;
import com.mbuenoferrer.popularmovies.tasks.FetchMovieListTaskListener;
import com.mbuenoferrer.popularmovies.tasks.FetchVideoListTask;
import com.mbuenoferrer.popularmovies.tasks.FetchVideoListTaskListener;
import com.mbuenoferrer.popularmovies.utils.DimenUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements VideoListAdapter.VideoListAdapterOnClickListener {

    public static final String MOVIE_ID = "MOVIE";

    private TextView mTitleTextView;
    private TextView mSynopsisTextView;
    private TextView mRatingTextView;
    private TextView mReleaseDateTextView;
    private ImageView mMoviePosterTextView;

    private RecyclerView mVideoListRecyclerView;
    private VideoListAdapter mVideoListAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitleTextView = (TextView)findViewById(R.id.tv_title);
        mSynopsisTextView = (TextView)findViewById(R.id.tv_synopsis);
        mRatingTextView = (TextView)findViewById(R.id.tv_user_rating);
        mReleaseDateTextView = (TextView)findViewById(R.id.tv_release_date);
        mMoviePosterTextView = (ImageView)findViewById(R.id.iv_movie_poster);

        mVideoListRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MOVIE_ID)) {
            Movie movie = intentThatStartedThisActivity.getParcelableExtra(MOVIE_ID);
            mTitleTextView.setText(movie.getTitle());
            mSynopsisTextView.setText(movie.getSynopsis());
            mRatingTextView.setText(movie.getUserRating() + "/10");
            mReleaseDateTextView.setText(movie.getReleaseDate());

            Picasso.with(mMoviePosterTextView.getContext())
                    .load(movie.getPoster())
                    .into(mMoviePosterTextView);

            // Configure recycler view
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mVideoListRecyclerView.setLayoutManager(layoutManager);
            mVideoListRecyclerView.setHasFixedSize(true);
            mVideoListAdapter = new VideoListAdapter(this);
            mVideoListRecyclerView.setAdapter(mVideoListAdapter);

            loadVideosData(movie.getId());
        }
    }

    private void loadVideosData(int movieId) {
        new FetchVideoListTask(this, new FetchVideoListTaskListener() {
            @Override
            public void onTaskPreExecute() {
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskPostExecute(List<Video> result) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (result != null) {
                    showMovieList();
                    mVideoListAdapter.setVideosData(result);
                } else {
                    showErrorMessage();
                }
            }
        }).execute(movieId);
    }

    private void showMovieList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mVideoListRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mVideoListRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onVideoClick(Video video) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
        startActivity(intent);
    }
}
