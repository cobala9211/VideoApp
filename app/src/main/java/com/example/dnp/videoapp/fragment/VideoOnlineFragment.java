package com.example.dnp.videoapp.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.dnp.videoapp.R;
import com.example.dnp.videoapp.adapter.VideoOnlineAdapter;
import com.example.dnp.videoapp.model.VideoOnline;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnp on 17/08/2016.
 */
@EFragment(R.layout.fragment_video_online)
public class VideoOnlineFragment extends BaseFragment {

    private List<VideoOnline> mListVideos = new ArrayList<>();
    public VideoOnlineAdapter mVideoOnlineAdapter;
    @ViewById(R.id.recyclerViewOnline)
    RecyclerView mRecyclerViewOnline;

    @Override
    void afterViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewOnline.setLayoutManager(linearLayoutManager);
        mRecyclerViewOnline.setHasFixedSize(true);
        mVideoOnlineAdapter = new VideoOnlineAdapter(mListVideos);
        mRecyclerViewOnline.setAdapter(mVideoOnlineAdapter);
        loadDataVideoOnline();
        final GestureDetector mGestureDetector = getGestureDetector();
        mRecyclerViewOnline.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                return childView != null && mGestureDetector.onTouchEvent(motionEvent);
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void loadDataVideoOnline() {
        Firebase.setAndroidContext(getContext());
        Firebase root = new Firebase(getString(R.string.main_firebase_root_database_video_online));
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListVideos.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for (DataSnapshot object : data.getChildren()) {
                        mListVideos.add(object.getValue(VideoOnline.class));
                        mVideoOnlineAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void initDataVideo(List<VideoOnline> lists) {
        this.mListVideos = lists;
    }

    private GestureDetector getGestureDetector() {
        return new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });
    }
}
