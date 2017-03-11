package com.quang.tracnghiemtoan.acivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.quang.tracnghiemtoan.R;

public class SelectPracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_practice);
        Button btnPractice = (Button) findViewById(R.id.buttonPractice);
        Button btnGeneral = (Button) findViewById(R.id.buttonGeneral);
        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectPracticeActivity.this, GeneralActivity.class));
            }
        });
        btnPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectPracticeActivity.this, PracticeActivity.class));
            }
        });
    }
}
