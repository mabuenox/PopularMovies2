package com.mbuenoferrer.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mbuenoferrer.popularmovies.R;
import com.mbuenoferrer.popularmovies.entities.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    //private static final String TAG = VideoListAdapter.class.getSimpleName();

    private List<Video> mVideoList;

    private final VideoListAdapterOnClickListener mOnClickListener;

    public interface VideoListAdapterOnClickListener {
        void onVideoClick(Video video);
    }

    public VideoListAdapter(VideoListAdapterOnClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.video_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        holder.bind(video);
    }

    @Override
    public int getItemCount() {
        if (null == mVideoList) return 0;
        return mVideoList.size();
    }

    public void setVideosData(List<Video> videoList) {
        mVideoList = videoList;
        notifyDataSetChanged();
    }


    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView videoTitleTextView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoTitleTextView = (TextView) itemView.findViewById(R.id.tv_video_title);
            itemView.setOnClickListener(this);
        }

        void bind(Video video) {
            videoTitleTextView.setText(video.getName());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Video video = mVideoList.get(adapterPosition);
            mOnClickListener.onVideoClick(video);
        }
    }
}
