package com.example.dnp.videoapp.activity;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
import com.example.dnp.videoapp.adapter.NavigateAdapter;
import com.example.dnp.videoapp.model.RowItem;
import com.example.dnp.videoapp.model.Users;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnp on 10/08/2016.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.toolbarHeader)
    Toolbar mToolbar;

    @ViewById(R.id.recycleViewMenu)
    RecyclerView mRecycleViewMenu;

    @ViewById(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.tvTitleHeader)
    TextView mTvTitleHeader;

    private List<RowItem> mListRowItem = new ArrayList<>();

    private void loadDataRecycler() {
        final String[] listTitle = getResources().getStringArray(R.array.main_array_row_title);
        final TypedArray listIcon = getResources().obtainTypedArray(R.array.main_array_row_icon);
        final int length = listIcon.length();
        for (int i = 0; i < length; i++) {
            mListRowItem.add(new RowItem(listIcon.getResourceId(i, -1), listTitle[i]));
        }
        listIcon.recycle();
    }

    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mTvTitleHeader.setText(getResources().getString(R.string.main_toolbar_text_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_home));
            } else {
                getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.ic_home));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuActionSetting:
                Toast.makeText(MainActivity.this, getString(R.string.main_menu_text_action_setting), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mnuActionUpload:
                Toast.makeText(MainActivity.this, getString(R.string.main_menu_text_action_upload), Toast.LENGTH_SHORT).show();
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

    @Override
    void afterViews() {
        RecyclerView.Adapter adapterNavigate;
        RecyclerView.LayoutManager layoutManager;
        ActionBarDrawerToggle actionBarDrawerToggle;
        setupToolBar();
        loadDataRecycler();
        mRecycleViewMenu.setHasFixedSize(true);
        adapterNavigate = new NavigateAdapter(mListRowItem, getUsers());
        mRecycleViewMenu.setAdapter(adapterNavigate);
        final GestureDetector mGestureDetector = getGestureDetector();
        mRecycleViewMenu.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (childView != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mDrawerLayout.closeDrawers();
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
        layoutManager = new LinearLayoutManager(this);
        mRecycleViewMenu.setLayoutManager(layoutManager);
        actionBarDrawerToggle = getActionBarDrawerToggle(mToolbar);
            mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private GestureDetector getGestureDetector() {
        return new GestureDetector(MainActivity.this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });
    }

    private ActionBarDrawerToggle getActionBarDrawerToggle(Toolbar toolbar) {
        return new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.main_actionbar_drawer_text_drawer_open,
                R.string.main_actionbar_drawer_text_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
    }

    private Users getUsers() {
        return new Users(getString(R.string.main_object_user_name),
                getString(R.string.main_object_user_email),
                getResources().obtainTypedArray(R.array.main_array_user_profile).getResourceId(0, -1));
    }
}
