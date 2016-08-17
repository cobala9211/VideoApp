package com.example.dnp.videoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.model.VideoOnline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnp on 17/08/2016.
 */
public class VideoOnlineAdapter extends RecyclerView.Adapter {
    private List<VideoOnline> mListVideos = new ArrayList<>();

    public VideoOnlineAdapter(List<VideoOnline> lists) {
        this.mListVideos = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_video_online, parent, false);
        return new NavigateHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NavigateHolder navigateHolder = (NavigateHolder) holder;
        navigateHolder.mTvTitle.setText(mListVideos.get(position).getTitle());
        navigateHolder.mTvAuthor.setText(mListVideos.get(position).getAuthor());
        navigateHolder.mTvDate.setText(mListVideos.get(position).getDate());
        navigateHolder.mTvTimeVideo.setText(mListVideos.get(position).getTimeVideo());
        navigateHolder.mVideoView.setVideoPath(mListVideos.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return mListVideos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class NavigateHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final TextView mTvAuthor;
        private final TextView mTvDate;
        private final TextView mTvTimeVideo;
        private final VideoView mVideoView;

        public NavigateHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mVideoView = (VideoView) itemView.findViewById(R.id.videoView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
            mTvTimeVideo = (TextView) itemView.findViewById(R.id.tvTimeVideo);
        }
    }
}
