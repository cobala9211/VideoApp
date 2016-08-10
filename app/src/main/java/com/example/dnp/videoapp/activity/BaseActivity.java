package com.example.dnp.videoapp.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import static com.example.dnp.videoapp.R.color.color_actionbar_green;

/**
 * Created by dnp on 10/08/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
    }

    /**
     * SET actionbar, set display home icon, title actionbar
     * set color actionbar
     */
    public void setActionBar() {
        ActionBar mActionbar = getSupportActionBar();
        assert mActionbar != null;
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(color_actionbar_green)));
    }
}
