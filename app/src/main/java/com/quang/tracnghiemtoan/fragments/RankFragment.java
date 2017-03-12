package com.quang.tracnghiemtoan.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.RankAdapter;
import com.quang.tracnghiemtoan.models.UserInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {
    private RecyclerView rvRank;
    private ArrayList<UserInfo> listUserInfo;
    private RankAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private SwipeRefreshLayout swipeRefreshLayout;

    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rank, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        listUserInfo = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Profile");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                listUserInfo.add(userInfo);
                Comparator<UserInfo> comparator = new Comparator<UserInfo>() {
                    @Override
                    public int compare(UserInfo t1, UserInfo t2) {
                        int a = t1.getPoint();
                        int b = t2.getPoint();
                        return b - a;
                    }
                };
                Collections.sort(listUserInfo, comparator);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(false);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        rvRank = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapter = new RankAdapter(listUserInfo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvRank.setLayoutManager(layoutManager);
        rvRank.setAdapter(adapter);
        return v;
    }

}
