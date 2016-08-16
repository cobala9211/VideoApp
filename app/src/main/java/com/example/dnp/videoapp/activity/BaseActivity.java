package com.example.dnp.videoapp.activity;

import android.support.v7.app.AppCompatActivity;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by dnp on 10/08/2016.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @AfterViews
    abstract void afterViews();
}
