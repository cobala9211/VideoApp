package com.example.dnp.videoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.model.RowItem;
import com.example.dnp.videoapp.model.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnp on 10/08/2016.
 */
public class NavigateAdapter extends RecyclerView.Adapter {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    private Users mUser;
    private List<RowItem> mListRowItem = new ArrayList<>();

    public NavigateAdapter(List<RowItem> list, Users user) {
        this.mListRowItem = list;
        this.mUser = user;
    }

    @Override
    public int getItemCount() {
        return mListRowItem.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mViewItem = LayoutInflater.from(parent.getContext()).inflate(
                viewType == TYPE_ITEM ? R.layout.item_list_view_row : R.layout.item_list_view_header,
                parent, false);
        if (viewType == TYPE_HEADER) {
            return new NavigateHolderHeader(mViewItem);
        } else if (viewType == TYPE_ITEM) {
            return new NavigateHolderRow(mViewItem);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            NavigateHolderHeader holderHeader = (NavigateHolderHeader) holder;
            holderHeader.mTvUserName.setText(mUser.getUserName());
            holderHeader.mTvUserEmail.setText(mUser.getEmail());
            holderHeader.mImgUserProfile.setImageResource(mUser.getUserProfile());
        } else {
            NavigateHolderRow holderRow = (NavigateHolderRow) holder;
            holderRow.mTexView.setText(mListRowItem.get(position).getTitle());
            holderRow.mImgView.setImageResource(mListRowItem.get(position).getUrl());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    /**
     * NavigateHolder : this is class create view holder to cyclerview
     */
    public static class NavigateHolderRow extends RecyclerView.ViewHolder {
        private final TextView mTexView;
        private final ImageView mImgView;

        public NavigateHolderRow(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mTexView = (TextView) itemView.findViewById(R.id.tvRowText);
            mImgView = (ImageView) itemView.findViewById(R.id.imgRowIcon);
        }
    }

    public static class NavigateHolderHeader extends RecyclerView.ViewHolder {

        private final ImageView mImgUserProfile;
        private final TextView mTvUserName;
        private final TextView mTvUserEmail;

        public NavigateHolderHeader(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvUserEmail = (TextView) itemView.findViewById(R.id.tvUserEmail);
            mImgUserProfile = (ImageView) itemView.findViewById(R.id.imgUserProfile);
        }
    }

}
