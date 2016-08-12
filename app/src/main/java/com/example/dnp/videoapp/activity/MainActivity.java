package com.example.dnp.videoapp.activity;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.adapter.NavigatationAdapter;
import com.example.dnp.videoapp.model.NavigatationItem;
import com.example.dnp.videoapp.model.Users;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tbTitle)
    Toolbar mTbTitleView;

    @ViewById(R.id.recycleViewMenu)
    RecyclerView mRecycleViewMenu;

    @ViewById(R.id.dlNavigatation)
    DrawerLayout mDlNavigatation;

    @ViewById(R.id.tvTitleHeader)
    TextView mTvTitleHeader;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ActionBarDrawerToggle mDtViewMenu;
    private List<NavigatationItem> mListItem = null;
    private Users mUser = null;

    @AfterViews
    void afterViews() {
        setupToolBar();
        loadDataUser();
        loadDataNavitation();
        mRecycleViewMenu.setHasFixedSize(true);
        mAdapter = new NavigatationAdapter(mListItem, mUser);
        mRecycleViewMenu.setAdapter(mAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });

        mRecycleViewMenu.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        mRecycleViewMenu.setLayoutManager(mLayoutManager);
        mDtViewMenu = new ActionBarDrawerToggle(this, mDlNavigatation, mTbTitleView, R.string.activity_actionbar_drawer_text_drawer_open,
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
        mDlNavigatation.setDrawerListener(mDtViewMenu);
        mDtViewMenu.syncState();
    }

    private void loadDataNavitation() {
        mListItem = new ArrayList<>();
        final String[] mArrayTitle = this.getResources().getStringArray(R.array.activity_array_row_title);
        final TypedArray mArrayIcon = this.getResources().obtainTypedArray(R.array.activity_array_row_icon);
        final int mLength = mArrayIcon.length();
        for (int i = 0; i < mLength; i++) {
            NavigatationItem mItem = new NavigatationItem(
                    mArrayIcon.getResourceId(i, -1), mArrayTitle[i]);
            mListItem.add(mItem);
        }
    }

    private void loadDataUser() {
        final String mUserName = this.getResources().getString(R.string.activity_object_user_name);
        final String mUserEmail = this.getResources().getString(R.string.activity_recyclerview_text_header_user_email);
        final TypedArray mIdImage = this.getResources().obtainTypedArray(R.array.activity_array_user_profile);
        final int mUserProfile = mIdImage.getResourceId(0, -1);
        mUser = new Users(mUserName, mUserEmail, mUserProfile);
    }

    private void setupToolBar() {
        setSupportActionBar(mTbTitleView);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mTvTitleHeader.setText(getResources().getString(R.string.activity_toolbar_text_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_home));
            } else {
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_home));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnActionSetting:
                Toast.makeText(MainActivity.this, "action item menu setting", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnActionUpload:
                Toast.makeText(MainActivity.this, "action item menu upload", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_upload_toolbar, menu);
        return true;
    }
}
