package com.mbuenoferrer.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mbuenoferrer.popularmovies.adapters.MovieListAdapter;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.enums.MovieListSort;
import com.mbuenoferrer.popularmovies.tasks.FetchMovieListTaskListener;
import com.mbuenoferrer.popularmovies.tasks.FetchMovieListTask;
import com.mbuenoferrer.popularmovies.utils.DimenUtils;

import java.util.List;

public class MovieListActivity extends AppCompatActivity
        implements MovieListAdapter.MovieListAdapterOnClickListener {

    private final String SORT_STATE_KEY = "SORT_STATE";
    private final String SAVED_LAYOUT_MANAGER_KEY = "SAVED_LAYOUT_MANAGER";

    private Parcelable mLayoutManagerSavedState;

    private int mCurrentSort;

    private RecyclerView mMovieListRecyclerView;
    private MovieListAdapter mMovieListAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mMovieListRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // Configure recycler view
        int numberOfColumns = DimenUtils.calculateNoOfColumns(getBaseContext(), 180);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        mMovieListRecyclerView.setLayoutManager(layoutManager);
        mMovieListRecyclerView.setHasFixedSize(false);
        mMovieListAdapter = new MovieListAdapter(this);
        mMovieListRecyclerView.setAdapter(mMovieListAdapter);

        if (savedInstanceState != null) {
            mCurrentSort = savedInstanceState.getInt(SORT_STATE_KEY);
            //mLayoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER_KEY);
        } else {
            mCurrentSort = MovieListSort.POPULAR;
        }

        loadMoviesData(mCurrentSort);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SORT_STATE_KEY, mCurrentSort);
        outState.putParcelable(SAVED_LAYOUT_MANAGER_KEY, mMovieListRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSort = savedInstanceState.getInt(SORT_STATE_KEY);
        mLayoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER_KEY);
    }

    private void loadMoviesData(int sortBy) {

        mCurrentSort = sortBy;

        new FetchMovieListTask(this, new FetchMovieListTaskListener() {
            @Override
            public void onTaskPreExecute() {
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskPostExecute(List<Movie> result) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (result != null) {
                    showMovieList();
                    mMovieListAdapter.setMoviesData(result);
                    if (mLayoutManagerSavedState != null) {
                        mMovieListRecyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
                    }
                } else {
                    showErrorMessage();
                }
            }
        }).execute(sortBy);
    }

    private void showMovieList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mMovieListRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mMovieListRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedId = item.getItemId();
        if (selectedId == R.id.action_order_popular) {
            loadMoviesData(MovieListSort.POPULAR);
        } else if (selectedId == R.id.action_order_top_rated) {
            loadMoviesData(MovieListSort.TOP_RATED);
        } else if (selectedId == R.id.action_show_favorites) {
            loadMoviesData(MovieListSort.FAVORITES);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieDetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(MovieDetailsActivity.MOVIE_ID, movie);
        startActivity(intentToStartDetailActivity);
    }

}
