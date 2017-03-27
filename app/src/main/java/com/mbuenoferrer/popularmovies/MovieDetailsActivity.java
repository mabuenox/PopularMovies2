package com.mbuenoferrer.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbuenoferrer.popularmovies.entities.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "MOVIE";

    private TextView mTitleTextView;
    private TextView mSynopsisTextView;
    private TextView mRatingTextView;
    private TextView mReleaseDateTextView;
    private ImageView mMoviePosterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitleTextView = (TextView)findViewById(R.id.tv_title);
        mSynopsisTextView = (TextView)findViewById(R.id.tv_synopsis);
        mRatingTextView = (TextView)findViewById(R.id.tv_user_rating);
        mReleaseDateTextView = (TextView)findViewById(R.id.tv_release_date);
        mMoviePosterTextView = (ImageView)findViewById(R.id.iv_movie_poster);

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
        }
    }
}
