package com.example.dnp.videoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.model.NavigatationItem;
import com.example.dnp.videoapp.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnp on 10/08/2016.
 */
public class NavigatationAdapter extends RecyclerView.Adapter<NavigatationAdapter.NavigatationHodel> {

    private String mUserName;

    private int mUserProfile;

    private String mUserGmail;

    private Context mContext;

    public List<NavigatationItem> mListItem = new ArrayList<>();

    public NavigatationAdapter(List<NavigatationItem> list, String name, String email, int profile, Context context) {
        this.mListItem = list;
        this.mUserName = name;
        this.mUserGmail = email;
        this.mUserProfile = profile;
        this.mContext = context;
    }

    @Override
    public NavigatationHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.TYPE_ITEM) {
            View mViewItem = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_view_row, parent, false);
            NavigatationHodel mNavigatationHodel = new NavigatationHodel(mViewItem, viewType, parent.getContext());
            return mNavigatationHodel;
        } else if (viewType == Constant.TYPE_HEADER) {
            View mViewHeader = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_view_header, parent, false);
            NavigatationHodel mNavigatationHodel = new NavigatationHodel(mViewHeader, viewType, parent.getContext());
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
            holder.mTvUserName.setText(mUserName);
            holder.mTvUesrEmail.setText(mUserGmail);
            holder.mImgUserProfile.setImageResource(mUserProfile);
        }
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPostionHeader(position))
            return Constant.TYPE_HEADER;
        return Constant.TYPE_ITEM;
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

        private TextView mTvUesrEmail;

        private Context mContext;

        public NavigatationHodel(View itemView, int viewType, Context context) {
            super(itemView);
            mContext = context;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            if (viewType == Constant.TYPE_ITEM) {
                mTexView = (TextView) itemView.findViewById(R.id.tvRowText);
                mImgView = (ImageView) itemView.findViewById(R.id.imgRowIcon);
                mHolderId = Constant.TYPE_ITEM;

            } else {
                mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
                mTvUesrEmail = (TextView) itemView.findViewById(R.id.tvUserEmail);
                mImgUserProfile = (ImageView) itemView.findViewById(R.id.imgUserProfile);
                mHolderId = Constant.TYPE_HEADER;
            }
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

    }

}
