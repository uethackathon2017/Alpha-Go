package com.quang.tracnghiemtoan.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.acivities.ChatRoomActivity;
import com.quang.tracnghiemtoan.acivities.LoginActivity;
import com.quang.tracnghiemtoan.acivities.MainActivity;
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
    private View v;
    private FirebaseUser user;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_main, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        MainAdapter mainAdapter = new MainAdapter(main_text, main_tuc_ngu_text);
        recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
        recyclerView.setAdapter(mainAdapter);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isNetworkConnected())
                    switch (position) {
                        case 0:
                            if (user != null)
                                startActivity(new Intent(getContext(), SelectPracticeActivity.class));
                            else showDialogLogin();
                            break;
                        case 1:
                            if (user != null)
                                startActivity(new Intent(getContext(), TestOnlineActivity.class));
                            else showDialogLogin();
                            break;
                        case 2:
                            if (user != null) {
                                MainActivity.checkmainfragment = false;
                                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                                toolbar.setTitle("Tổng hợp đề thi các trường");
                                transaction.replace(R.id.layout_content, new SchoolTestFragment());
                                transaction.commit();
                            } else showDialogLogin();
                            break;
                        case 3:
                            startActivity(new Intent(getContext(), VideoTutorialActivity.class));
                            break;
                        case 4:
                            MainActivity.checkmainfragment = false;
                            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                            toolbar.setTitle("Tin tức");
                            transaction.replace(R.id.layout_content, new NewsFragment());
                            transaction.commit();
                            break;
                        case 5:
                            if (user != null)
                                startActivity(new Intent(getContext(), ChatRoomActivity.class));
                            else showDialogLogin();
                            break;
                    }
                else showNoInternetDialog();
            }
        });
        return v;
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Vui lòng kiểm tra kết nối internet!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        if (!getActivity().isFinishing()) builder.show();
    }

    private void showDialogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("Vui lòng đăng nhập để sử dụng chức năng này!");
        builder.setCancelable(false);
        builder.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        builder.setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        if (!getActivity().isFinishing()) builder.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
