package com.mbuenoferrer.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mbuenoferrer.popularmovies.R;
import com.mbuenoferrer.popularmovies.entities.Review;
import com.mbuenoferrer.popularmovies.entities.Video;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {

    //private static final String TAG = VideoListAdapter.class.getSimpleName();

    private List<Review> mReviewList;

    private final ReviewListAdapterOnClickListener mOnClickListener;

    public interface ReviewListAdapterOnClickListener {
        void onReviewClick(Review review);
    }

    public ReviewListAdapter(ReviewListAdapterOnClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = mReviewList.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        if (null == mReviewList) return 0;
        return mReviewList.size();
    }

    public void setReviewsData(List<Review> reviewList) {
        mReviewList = reviewList;
        notifyDataSetChanged();
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView reviewAuthorTextView;
        TextView reviewContentTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            reviewAuthorTextView = (TextView) itemView.findViewById(R.id.tv_review_author);
            reviewContentTextView = (TextView) itemView.findViewById(R.id.tv_review_content);
            itemView.setOnClickListener(this);
        }

        void bind(Review review) {
            reviewAuthorTextView.setText(review.getAuthor());
            reviewContentTextView.setText(review.getContent());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Review review = mReviewList.get(adapterPosition);
            mOnClickListener.onReviewClick(review);
        }
    }
}
