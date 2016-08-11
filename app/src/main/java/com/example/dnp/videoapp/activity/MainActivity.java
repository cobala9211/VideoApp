package com.example.dnp.videoapp.activity;

import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.adapter.NavigatationAdapter;
import com.example.dnp.videoapp.model.NavigatationItem;
import com.example.dnp.videoapp.util.Constant;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tbToolBar)
    Toolbar mToolbar;

    @ViewById(R.id.recycleView)
    RecyclerView mRecycleView;

    @ViewById(R.id.dlNavigatation)
    DrawerLayout mDlNavigatation;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private ActionBarDrawerToggle mDrawerToggle;

    private List<NavigatationItem> mListItem = new ArrayList<>();

    @AfterViews
    void afterViews() {
        setupToolBar();
        mRecycleView.setHasFixedSize(true);
        loadDataNavitation();
        mAdapter = new NavigatationAdapter(mListItem, Constant.USER_NAME,
                Constant.USER_EMAIL, Constant.USER_PROFILE, MainActivity.this);
        mRecycleView.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });

        mRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View mChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (mChildView != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mDlNavigatation.closeDrawers();
                    Toast.makeText(MainActivity.this, "The Item Clicked is: " + recyclerView.getChildPosition(mChildView), Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDlNavigatation, mToolbar, R.string.activity_actionbar_drawer_text_drawer_open,
                R.string.activity_actionbar_drawer_text_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };
        mDlNavigatation.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    private void loadDataNavitation() {
        for (int i = 0; i < Constant.ICONS.length; i++) {
            NavigatationItem mItem = new NavigatationItem(Constant.ICONS[i], Constant.TITLES[i]);
            mListItem.add(mItem);
        }
    }

    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_home));
            } else {
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_home));
            }
        }
    }
}
