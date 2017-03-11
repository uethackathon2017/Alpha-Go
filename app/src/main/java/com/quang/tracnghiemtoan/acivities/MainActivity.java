package com.quang.tracnghiemtoan.acivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.fragments.MainFragment;
import com.quang.tracnghiemtoan.fragments.NewsFragment;
import com.quang.tracnghiemtoan.fragments.SchoolTestFragment;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private android.support.v4.app.FragmentManager fragmentManager;
    private boolean isTest = true;
    private TextView tvDayLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        ImageView imvAvatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewAvatar);
        String linkAvatar = "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?width=300";
        Glide.with(MainActivity.this).load(linkAvatar).into(imvAvatar);
        TextView tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewName);
        tvName.setText(Profile.getCurrentProfile().getName());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_content, new MainFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        tvDayLeft = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewDayLeft);
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, 6);
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
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.layout_content, new MainFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (isNetworkConnected()) {
            if (id == R.id.nav_chat_room) {
                startActivity(new Intent(MainActivity.this, ChatRoomActivity.class));
            } else if (id == R.id.nav_contest_online) {
                if (isTest)
                    startActivity(new Intent(MainActivity.this, TestOnlineActivity.class));
                else {
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
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content, new NewsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (id == R.id.nav_practice) {
                startActivity(new Intent(MainActivity.this, SelectPracticeActivity.class));
            } else if (id == R.id.nav_school_test) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_content, new SchoolTestFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else if (id == R.id.nav_video) {
                startActivity(new Intent(MainActivity.this, VideoTutorialActivity.class));
            } else if (id == R.id.nav_share) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share to Friends");
                String s = getResources().getString(R.string.app_introduce) + getPackageName();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
