package com.example.dnp.videoapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.dnp.videoapp.fragment.VideoOnlineFragment;
import com.example.dnp.videoapp.fragment.VideoOnlineFragment_;
import com.example.dnp.videoapp.model.RowItem;
import com.example.dnp.videoapp.model.Users;
import com.example.dnp.videoapp.model.VideoOnline;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by dnp on 10/08/2016.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    public static final int FILE_SELECT_CODE = 0;
    public static final int PERCENT_PROGRESS = 100;
    private List<RowItem> mListRowItems = new ArrayList<>();
    private List<VideoOnline> mListVideoOnline = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @ViewById(R.id.toolbarHeader)
    Toolbar mToolbar;

    @ViewById(R.id.recycleViewMenu)
    RecyclerView mRecycleViewMenu;

    @ViewById(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.tvTitleHeader)
    TextView mTvTitleHeader;

    private void loadDataRecycler() {
        final String[] listTitle = getResources().getStringArray(R.array.main_array_row_title);
        final TypedArray listIcon = getResources().obtainTypedArray(R.array.main_array_row_icon);
        final int length = listIcon.length();
        for (int i = 0; i < length; i++) {
            mListRowItems.add(new RowItem(listIcon.getResourceId(i, -1), listTitle[i]));
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
                showFileChooser();
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

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(getString(R.string.main_constant_video_type_all_file_mp4));
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, getString(R.string.main_constant_file_choices_text)),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getResources().getString(R.string.main_toast_text_error_content),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initProgress() {
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle(getString(R.string.main_progressdialog_text_title_progressdialog));
        mProgressDialog.setMessage(getString(R.string.main_progressdialog_text_message_progressdialog));
        mProgressDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            final String path = geRealPathFromUri(MainActivity.this, data.getData());
            MediaPlayer mediaPlayer = MediaPlayer.create(this, Uri.parse(path));
            File file = new File(path);
            final String name = file.getName();
            final String duration = milliSecondsToTimer(mediaPlayer.getDuration());
            final String date = getDateSystem();
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(getString(R.string.main_firebase_linkbase_database_video_online));
            StorageReference fileVideo = storageReference.child(getString(R.string.main_constant_video_type_video) + name);
            UploadTask uploadTask = fileVideo.putFile(data.getData());
            initProgress();
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (PERCENT_PROGRESS * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    mProgressDialog.setMessage(getString(R.string.main_progressdialog_text_message_progressdialog_loading) + (int) progress + " %");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getDownloadUrl() != null) {
                        String url = taskSnapshot.getMetadata().getDownloadUrl().toString();
                        VideoOnline videoOnline = new VideoOnline(url, name, getString(R.string.main_firebase_object_user_author) + UUID.randomUUID().toString(), date, duration);
                        uploadVideoOnline(videoOnline);
                        loadDataVideoOnline();
                    }


                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
        }
    }

    private void uploadVideoOnline(VideoOnline videoOnline) {
        Firebase.setAndroidContext(this);
        Firebase root = new Firebase(getString(R.string.main_firebase_root_database_video_online));
        root.child(getString(R.string.main_firebase_object_user)).push().setValue(videoOnline);
    }

    private void loadDataVideoOnline() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final VideoOnlineFragment videoOnlineFragment = (VideoOnlineFragment) fragmentManager.findFragmentByTag(getString(R.string.video_online_fragment_content_text));
        Firebase.setAndroidContext(MainActivity.this);
        Firebase root = new Firebase(getString(R.string.main_firebase_root_database_video_online));
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListVideoOnline.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for (DataSnapshot object : data.getChildren()) {
                        mListVideoOnline.add(object.getValue(VideoOnline.class));
                        videoOnlineFragment.mVideoOnlineAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private String milliSecondsToTimer(long milliseconds) {
        return TimeUnit.MILLISECONDS.toHours(milliseconds) > 0 ? (new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault())).format(new Date(milliseconds)) : (new SimpleDateFormat("mm:ss",
                Locale.getDefault())).format(new Date(milliseconds));
    }

    public String geRealPathFromUri(Context context, Uri contentUri) {
        String path = null;
        if (contentUri.getScheme().equals(getString(R.string.main_constant_file_type_video_online))) {
            path = contentUri.getPath();
        } else if (contentUri.getScheme().equals(getString(R.string.main_constant_content_type_video_online))) {
            Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            String documentId = cursor.getString(0);
            documentId = documentId.substring(documentId.lastIndexOf(":") + 1);
            cursor.close();
            cursor = context.getContentResolver().query(
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Video.Media._ID + " = ? ", new String[]{documentId}, null);
            assert cursor != null;
            if (cursor.getCount() == 0) {
                path = getString(R.string.main_constant_file_path_text_error);
            } else {
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                cursor.close();
            }
        }
        return path;
    }

    private String getDateSystem() {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.main_simple_date_format_system_date), Locale.ENGLISH);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    @Override
    void afterViews() {
        RecyclerView.Adapter adapterNavigate;
        RecyclerView.LayoutManager layoutManager;
        ActionBarDrawerToggle actionBarDrawerToggle;
        setupToolBar();
        loadDataRecycler();
        mRecycleViewMenu.setHasFixedSize(true);
        adapterNavigate = new NavigateAdapter(mListRowItems, getUsers());
        mRecycleViewMenu.setAdapter(adapterNavigate);
        VideoOnlineFragment videoOnlineFragment = VideoOnlineFragment_.builder().build();
        videoOnlineFragment.initDataVideo(mListVideoOnline);
        initFragment(videoOnlineFragment, getString(R.string.video_online_fragment_content_text));
        final GestureDetector mGestureDetector = getGestureDetector();
        mRecycleViewMenu.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (childView != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mDrawerLayout.closeDrawers();
                    int position = recyclerView.getChildAdapterPosition(childView);
                    switch (position) {
                        case 1:
                            VideoOnlineFragment videoOnlineFragment = VideoOnlineFragment_.builder().build();
                            videoOnlineFragment.initDataVideo(mListVideoOnline);
                            break;
                    }
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

    private void initFragment(Fragment fragment, String content) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frHomeFragment, fragment, content);
        fragmentTransaction.commit();
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
