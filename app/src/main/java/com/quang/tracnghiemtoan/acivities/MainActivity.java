package com.quang.tracnghiemtoan.acivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.RankFragment;
import com.quang.tracnghiemtoan.fragments.MainFragment;
import com.quang.tracnghiemtoan.fragments.NewsFragment;
import com.quang.tracnghiemtoan.fragments.SchoolTestFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private android.support.v4.app.FragmentManager fragmentManager;
    private boolean isTest = true;
    private TextView tvDayLeft;
    private FirebaseUser user;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference isTestRef = database.getReference("IsTest");
        isTestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Integer.class) == 0) {
                    isTest = false;
                } else isTest = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            ImageView imvAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewAvatar);
            String linkAvatar = "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?width=300";
            Glide.with(MainActivity.this).load(linkAvatar).into(imvAvatar);
            TextView tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewName);
            tvName.setText(Profile.getCurrentProfile().getName());
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_content, new MainFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        tvDayLeft = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewDayLeft);
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, 22);
        thatDay.set(Calendar.MONTH, 5); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 2017);

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();

        long days = diff / (24 * 60 * 60 * 1000);
        tvDayLeft.setText(String.valueOf(days));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            toolbar.setTitle("Trang chủ");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_content, new MainFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (isNetworkConnected()) {
            if (id == R.id.nav_chat_room) {
                if (user != null)
                    startActivity(new Intent(MainActivity.this, ChatRoomActivity.class));
                else {
                    showDialogLogin();
                }
            } else if (id == R.id.nav_contest_online) {
                if (isTest) {
                    if (user != null)
                        startActivity(new Intent(MainActivity.this, TestOnlineActivity.class));
                    else showDialogLogin();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Đã hết thời gian thi. Vui lòng đợi lần thi sau!");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    builder.create();
                    if (!isFinishing()) builder.show();
                }
            } else if (id == R.id.nav_news) {
                toolbar.setTitle("Tin tức tuyển sinh");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content, new NewsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (id == R.id.nav_rank) {
                toolbar.setTitle("Bảng xếp hạng");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content, new RankFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (id == R.id.nav_practice) {
                if (user != null)
                    startActivity(new Intent(MainActivity.this, SelectPracticeActivity.class));
                else showDialogLogin();
            } else if (id == R.id.nav_school_test) {
                if (user != null) {
                    toolbar.setTitle("Tổng hợp đề thi các trường");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.layout_content, new SchoolTestFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else showDialogLogin();
            } else if (id == R.id.nav_video) {
                startActivity(new Intent(MainActivity.this, VideoTutorialActivity.class));
            } else if (id == R.id.nav_share) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share to Friends");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.app_introduce) + getPackageName());
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.app_name)));
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Vui lòng kiểm tra kết nối internet!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create();
            if (!isFinishing()) builder.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Vui lòng đăng nhập để sử dụng chức năng này!");
        builder.setCancelable(false);
        builder.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        builder.setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        if (!isFinishing()) builder.show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START);
        } else if (!(getActiveFragment() instanceof MainFragment)) {
            toolbar.setTitle("Trang chủ");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_content, new MainFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if ((getActiveFragment() instanceof MainFragment)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bạn có chắc chắn muốn thoát không?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            if (!isFinishing()) builder.show();
        } else super.onBackPressed();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public Fragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (Fragment) getSupportFragmentManager().findFragmentByTag(tag);
    }
}
