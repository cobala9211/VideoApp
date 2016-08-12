package com.example.dnp.videoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.model.NavigatationItem;
import com.example.dnp.videoapp.model.Users;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnp on 10/08/2016.
 */
public class NavigatationAdapter extends RecyclerView.Adapter<NavigatationAdapter.NavigatationHodel> {

    private static final String TAG = NavigatationAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final Users mUser;
    private List<NavigatationItem> mListItem = new ArrayList<>();

    public NavigatationAdapter(List<NavigatationItem> list, Users user) {
        this.mListItem = list;
        this.mUser = user;
    }

    @Override
    public NavigatationHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View mViewItem = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_view_row, parent, false);
            NavigatationHodel mNavigatationHodel = new NavigatationHodel(mViewItem, viewType);
            return mNavigatationHodel;
        } else if (viewType == TYPE_HEADER) {
            View mViewHeader = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_view_header, parent, false);
            NavigatationHodel mNavigatationHodel = new NavigatationHodel(mViewHeader, viewType);
            return mNavigatationHodel;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NavigatationHodel holder, int position) {
        if (holder.mHolderId == 1) {
            holder.mTexView.setText(mListItem.get(position).getTitle());
            holder.mImgView.setImageResource(mListItem.get(position).getUrl());
        } else {
            holder.mTvUserName.setText(mUser.getMUserName());
            holder.mTvUesrEmail.setText(mUser.getMUserGmail());
            holder.mImgUserProfile.setImageResource(mUser.getMUserProfile());
        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPostionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPostionHeader(int position) {
        return position == 0;
    }

    public static class NavigatationHodel extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private int mHolderId;
        private TextView mTexView;
        private ImageView mImgView;
        private ImageView mImgUserProfile;
        private TextView mTvUserName;
        private TextView mTvUesrEmail = null;

        public NavigatationHodel(View itemView, int viewType) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            if (viewType == TYPE_ITEM) {
                mTexView = (TextView) itemView.findViewById(R.id.tvRowText);
                mImgView = (ImageView) itemView.findViewById(R.id.imgRowIcon);
                mHolderId = TYPE_ITEM;
            } else {
                mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
                mTvUesrEmail = (TextView) itemView.findViewById(R.id.tvUserEmail);
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
