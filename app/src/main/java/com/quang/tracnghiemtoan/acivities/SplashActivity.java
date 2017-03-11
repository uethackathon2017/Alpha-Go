package com.quang.tracnghiemtoan.acivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.quang.tracnghiemtoan.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = this.getSharedPreferences("setting", Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("intro", false)) {
            startActivity(new Intent(SplashActivity.this, TestMultiplePageActivity.class));
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 1000);
        }
    }
}
