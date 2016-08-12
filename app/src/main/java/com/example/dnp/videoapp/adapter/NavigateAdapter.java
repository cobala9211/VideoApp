package com.example.dnp.videoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class NavigateAdapter extends RecyclerView.Adapter<NavigateAdapter.NavigateHolder> {

    public static final String TAG = NavigateAdapter.class.getSimpleName();
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;
    private Users mUser;
    private List<RowItem> mListRowItem = new ArrayList<>();

    public NavigateAdapter(List<RowItem> list, Users user) {
        this.mListRowItem = list;
        this.mUser = user;
    }

    @Override
    public NavigateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mViewItem = LayoutInflater.from(parent.getContext()).inflate(
                viewType == TYPE_ITEM ? R.layout.item_list_view_row : R.layout.item_list_view_header, parent, false);
        return new NavigateHolder(mViewItem, viewType);
    }

    @Override
    public void onBindViewHolder(NavigateHolder holder, int position) {
        if (holder.mHolderId == 1) {
            holder.mTexView.setText(mListRowItem.get(position).getTitle());
            holder.mImgView.setImageResource(mListRowItem.get(position).getUrl());
        } else {
            holder.mTvUserName.setText(mUser.getUserName());
            holder.mTvUserEmail.setText(mUser.getEmail());
            holder.mImgUserProfile.setImageResource(mUser.getUserProfile());
        }
    }

    @Override
    public int getItemCount() {
        return mListRowItem.size();
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
    public static class NavigateHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final int mHolderId;
        private TextView mTexView;
        private ImageView mImgView;
        private ImageView mImgUserProfile;
        private TextView mTvUserName;
        private TextView mTvUserEmail;

        public NavigateHolder(View itemView, int viewType) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            if (viewType == TYPE_ITEM) {
                mTexView = (TextView) itemView.findViewById(R.id.tvRowText);
                mImgView = (ImageView) itemView.findViewById(R.id.imgRowIcon);
                mHolderId = TYPE_ITEM;
            } else {
                mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
                mTvUserEmail = (TextView) itemView.findViewById(R.id.tvUserEmail);
                mImgUserProfile = (ImageView) itemView.findViewById(R.id.imgUserProfile);
                mHolderId = TYPE_HEADER;
            }
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
        }

    }

}
