package com.quang.tracnghiemtoan.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.acivities.ChatRoomActivity;
import com.quang.tracnghiemtoan.acivities.SelectPracticeActivity;
import com.quang.tracnghiemtoan.acivities.TestOnlineActivity;
import com.quang.tracnghiemtoan.acivities.VideoTutorialActivity;
import com.quang.tracnghiemtoan.adapters.MainAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private String[] main_text = new String[]{"Luyện tập", "Thi Online", "Đề thi các trường", "Học qua video", "Tin tức", "Phòng Chat"};
    private String[] main_tuc_ngu_text = new String[]{"(Luyện mãi thành tài, miệt mài tất giỏi)", "(Học hành vất vả kết quả ngọt bùi)", "(Học một biết mười)", "(Học để làm người)", "(Có cày có thóc, có học có chữ)", "(Có học, có khôn)"};

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        MainAdapter mainAdapter = new MainAdapter(main_text, main_tuc_ngu_text);
        recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
        recyclerView.setAdapter(mainAdapter);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 4:
                        transaction.replace(R.id.layout_content, new NewsFragment());
                        transaction.commit();
                        break;
                    case 0:
                        startActivity(new Intent(getContext(), SelectPracticeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), TestOnlineActivity.class));
                        break;
                    case 2:
                        transaction.replace(R.id.layout_content, new SchoolTestFragment());
                        transaction.commit();
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), VideoTutorialActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(), ChatRoomActivity.class));
                        break;
                }
            }
        });
        return v;
    }

}
