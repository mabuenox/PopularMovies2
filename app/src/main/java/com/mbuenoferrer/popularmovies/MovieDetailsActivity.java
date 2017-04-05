package com.mbuenoferrer.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mbuenoferrer.popularmovies.adapters.ReviewListAdapter;
import com.mbuenoferrer.popularmovies.adapters.VideoListAdapter;
import com.mbuenoferrer.popularmovies.data.db.FavoriteMovieColumns;
import com.mbuenoferrer.popularmovies.data.db.MoviesProvider;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.entities.Review;
import com.mbuenoferrer.popularmovies.entities.Video;
import com.mbuenoferrer.popularmovies.tasks.FetchIsFavoriteTask;
import com.mbuenoferrer.popularmovies.tasks.FetchIsFavoriteTaskListener;
import com.mbuenoferrer.popularmovies.tasks.FetchReviewListTask;
import com.mbuenoferrer.popularmovies.tasks.FetchReviewListTaskListener;
import com.mbuenoferrer.popularmovies.tasks.FetchVideoListTask;
import com.mbuenoferrer.popularmovies.tasks.FetchVideoListTaskListener;
import com.mbuenoferrer.popularmovies.tasks.ToggleFavoriteTask;
import com.mbuenoferrer.popularmovies.tasks.ToggleFavoriteTaskListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;

public class MovieDetailsActivity
        extends AppCompatActivity
        implements VideoListAdapter.VideoListAdapterOnClickListener, ReviewListAdapter.ReviewListAdapterOnClickListener {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    public static final String MOVIE_ID = "MOVIE";

    private TextView mTitleTextView;
    private TextView mSynopsisTextView;
    private TextView mRatingTextView;
    private TextView mReleaseDateTextView;
    private ImageView mMoviePosterTextView;
    private Button mFavoriteButton;

    private RecyclerView mVideoListRecyclerView;
    private VideoListAdapter mVideoListAdapter;
    private TextView mErrorMessageDisplayVideos;
    private ProgressBar mLoadingIndicatorVideos;

    private RecyclerView mReviewListRecyclerView;
    private ReviewListAdapter mReviewListAdapter;
    private TextView mErrorMessageDisplayReviews;
    private ProgressBar mLoadingIndicatorReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Movie
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);
        mRatingTextView = (TextView) findViewById(R.id.tv_user_rating);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mMoviePosterTextView = (ImageView) findViewById(R.id.iv_movie_poster);
        mFavoriteButton = (Button) findViewById(R.id.favorite_button);



        // Videos
        mVideoListRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);
        mErrorMessageDisplayVideos = (TextView) findViewById(R.id.tv_error_message_display_videos);
        mLoadingIndicatorVideos = (ProgressBar) findViewById(R.id.pb_loading_indicator_videos);

        // Reviews
        mReviewListRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        mErrorMessageDisplayReviews = (TextView) findViewById(R.id.tv_error_message_display_reviews);
        mLoadingIndicatorReviews = (ProgressBar) findViewById(R.id.pb_loading_indicator_reviews);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MOVIE_ID)) {
            final Movie movie = intentThatStartedThisActivity.getParcelableExtra(MOVIE_ID);
            mTitleTextView.setText(movie.getTitle());
            mSynopsisTextView.setText(movie.getSynopsis());
            mRatingTextView.setText(movie.getUserRating() + "" + R.string.slash_ten);
            mReleaseDateTextView.setText(movie.getReleaseDate());

            Picasso.with(mMoviePosterTextView.getContext())
                    .load(movie.getPoster())
                    .into(mMoviePosterTextView);

            loadFavoriteButton(movie);

            mFavoriteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        toggleFavorite(movie);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });

            // Configure recycler view for videos
            LinearLayoutManager videosLayoutManager = new LinearLayoutManager(this);
            mVideoListRecyclerView.setLayoutManager(videosLayoutManager);
            mVideoListRecyclerView.setHasFixedSize(true);
            mVideoListAdapter = new VideoListAdapter(this);
            mVideoListRecyclerView.setAdapter(mVideoListAdapter);
            loadVideosData(movie.getId());

            // Configure recycler view for reviews
            LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this);
            mReviewListRecyclerView.setLayoutManager(reviewsLayoutManager);
            mReviewListRecyclerView.setHasFixedSize(true);
            mReviewListAdapter = new ReviewListAdapter(this);
            mReviewListRecyclerView.setAdapter(mReviewListAdapter);
            loadReviewsData(movie.getId());
        }
    }

    private void loadFavoriteButton(Movie movie) {
        new FetchIsFavoriteTask(this, new FetchIsFavoriteTaskListener() {
            @Override
            public void onTaskPostExecute(Boolean result) {
                if (result != null) {
                    setFavoriteButtonText(result);
                }
            }
        }).execute(movie);
    }

    private void setFavoriteButtonText(boolean isFavorite) {
        if(isFavorite) {
            mFavoriteButton.setText(R.string.remove_from_favorites);
        } else {
            mFavoriteButton.setText(R.string.add_to_favorites);
        }
    }

    private void loadVideosData(int movieId) {
        new FetchVideoListTask(this, new FetchVideoListTaskListener() {
            @Override
            public void onTaskPreExecute() {
                mLoadingIndicatorVideos.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskPostExecute(List<Video> result) {
                mLoadingIndicatorVideos.setVisibility(View.INVISIBLE);
                if (result != null) {
                    showVideoList();
                    mVideoListAdapter.setVideosData(result);
                } else {
                    showVideosErrorMessage();
                }
            }
        }).execute(movieId);
    }

    private void showVideoList() {
        mErrorMessageDisplayVideos.setVisibility(View.INVISIBLE);
        mVideoListRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showVideosErrorMessage() {
        mVideoListRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplayVideos.setVisibility(View.VISIBLE);
    }

    @Override
    public void onVideoClick(Video video) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
        startActivity(intent);
    }


    private void loadReviewsData(int movieId) {
        new FetchReviewListTask(this, new FetchReviewListTaskListener() {
            @Override
            public void onTaskPreExecute() {
                mLoadingIndicatorReviews.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskPostExecute(List<Review> result) {
                mLoadingIndicatorReviews.setVisibility(View.INVISIBLE);
                if (result != null) {
                    showReviewList();
                    mReviewListAdapter.setReviewsData(result);
                } else {
                    showReviewsErrorMessage();
                }
            }
        }).execute(movieId);
    }

    private void showReviewList() {
        mErrorMessageDisplayReviews.setVisibility(View.INVISIBLE);
        mReviewListRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showReviewsErrorMessage() {
        mReviewListRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplayReviews.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReviewClick(Review review) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
        startActivity(intent);
    }


    private void toggleFavorite(Movie movie) {

        if(mFavoriteButton.getText().toString().equals(getText(R.string.add_to_favorites))) {
            setFavoriteButtonText(true);
        } else {
            setFavoriteButtonText(false);
        }

        new ToggleFavoriteTask(this, new ToggleFavoriteTaskListener() {
            @Override
            public void onTaskPostExecute() {
                Toast.makeText(getBaseContext(), R.string.favorite_saved, Toast.LENGTH_LONG).show();
            }
        }).execute(movie);
    }


}
