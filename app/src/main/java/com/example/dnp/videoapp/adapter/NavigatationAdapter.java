package com.example.dnp.videoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnp.videoapp.model.NavigatationItem;
import com.example.dnp.videoapp.util.Constant;

import java.util.List;

/**
 * Created by dnp on 10/08/2016.
 */
public class NavigatationAdapter extends BaseAdapter {

    private Context mContext;
    private List<NavigatationItem> mListNavigatation;
    private LayoutInflater mLayoutInflater;

    public NavigatationAdapter(Context context, List<NavigatationItem> list) {
        this.mContext = context;
        this.mListNavigatation = list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mListNavigatation.size();
    }

    @Override
    public Object getItem(int position) {
        return mListNavigatation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return Constant.COUNT_TYPE_LIST;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constant.TYPE_LIST_USER;
        } else if (position == 3 || position == 8) {
            return Constant.TYPE_LIST_CONTEXT;
        } else {
            return Constant.TYPE_LIST_SETTING;
        }
    }

    /**
     * Holder
     * this class create static views item
     */
    public static class Holder {

        private ImageView mImgUser;

        private TextView mTvTitleUser;

        private TextView mTvCountVide0;
    }
}
