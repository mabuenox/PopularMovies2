package com.mbuenoferrer.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mbuenoferrer.popularmovies.R;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    //private static final String TAG = MovieListAdapter.class.getSimpleName();

    private List<Movie> mMovieList;

    private final MovieListAdapterOnClickListener mOnClickListener;

    public interface MovieListAdapterOnClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieListAdapter(MovieListAdapterOnClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.size();
    }

    public void setMoviesData(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            Picasso.with(posterImageView.getContext())
                    .load(movie.getPoster())
                    .into(posterImageView);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieList.get(adapterPosition);
            mOnClickListener.onMovieClick(movie);
        }
    }
}
