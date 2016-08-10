package com.example.dnp.videoapp.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dnp.videoapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle mActionBarTonggle;

    private String[] arrItem = {"title user", "video online", "video offline", "chat room"};

    private ActionBar mActionbar;

    @ViewById(R.id.lvNavigatation)
    ListView mLvNavigatation;

    @ViewById(R.id.dlNavigatation)
    DrawerLayout mDlNavigatation;

    @AfterViews
    void afterViews() {
        mActionbar = getSupportActionBar();
        mLvNavigatation.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1, arrItem));

        mDlNavigatation.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);

        mDlNavigatation.setStatusBarBackgroundColor(getResources().getColor(R.color.color_actionbar_green));

        mActionBarTonggle = new ActionBarDrawerToggle(this
                , mDlNavigatation
                , R.string.activity_actionbar_drawer_text_drawer
                , R.string.activity_actionbar_drawer_text_drawer_open) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDlNavigatation.setDrawerListener(mActionBarTonggle);
        mActionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_add);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarTonggle.syncState();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mActionbar.setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.color_actionbar_green)));
        mActionbar.setDisplayHomeAsUpEnabled(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            boolean mIsdrawerleftOpen = mDlNavigatation.isDrawerOpen(mLvNavigatation);
            mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
            mActionbar.setDisplayHomeAsUpEnabled(true);
            if (!mIsdrawerleftOpen) {
                mDlNavigatation.openDrawer(GravityCompat.START);
            } else {
                mDlNavigatation.closeDrawer(mLvNavigatation);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
