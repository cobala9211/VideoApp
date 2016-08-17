package com.example.dnp.videoapp.fragment;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by dnp on 17/08/2016.
 */
@EFragment
public abstract class BaseFragment extends Fragment {

    @AfterViews
    abstract void afterViews();
}
