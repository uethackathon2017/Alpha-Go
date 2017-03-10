package com.quang.tracnghiemtoan.acivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.PracticeAnswerAdapter;
import com.quang.tracnghiemtoan.adapters.PracticeReplyAdapter;
import com.quang.tracnghiemtoan.constants.Constant;
import com.quang.tracnghiemtoan.controllers.SQLiteDataController;
import com.quang.tracnghiemtoan.models.Problem;
import com.quang.tracnghiemtoan.views.MathJaxWebView;

import java.io.IOException;
import java.util.ArrayList;

public class PracticeActivity extends AppCompatActivity {

    private MathJaxWebView mathJaxWebView;
    private SQLiteDataController sqLiteDataController;
    private ArrayList<Problem> problems;
    private Spinner spnPractice;
    private Toolbar toolbar;
    private Button btnAnswer;
    private RecyclerView rvAnswerReply;
    private RecyclerView rvAnswer;
    private PracticeReplyAdapter replyAdapter;
    private PracticeAnswerAdapter answerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        sqLiteDataController = new SQLiteDataController(PracticeActivity.this);
        mathJaxWebView = (MathJaxWebView) findViewById(R.id.practice_webView);
        mathJaxWebView.getSettings().setJavaScriptEnabled(true);
        try {
            sqLiteDataController.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        problems = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(PracticeActivity.this);

        rvAnswerReply = (RecyclerView) navigationView.getHeaderView(1).findViewById(R.id.recyclerViewReply);
        rvAnswerReply.setHasFixedSize(true);
        rvAnswerReply.setLayoutManager(manager);
        rvAnswerReply.setItemAnimator(new DefaultItemAnimator());
        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
        rvAnswerReply.setAdapter(replyAdapter);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(PracticeActivity.this);
        rvAnswer = (RecyclerView) navigationView.getHeaderView(1).findViewById(R.id.recyclerViewAnswer);
        rvAnswer.setHasFixedSize(true);
        rvAnswer.setLayoutManager(manager2);
        rvAnswer.setItemAnimator(new DefaultItemAnimator());
        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
        rvAnswer.setAdapter(answerAdapter);

        btnAnswer = (Button) findViewById(R.id.button_dapan);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvAnswer.setVisibility(View.VISIBLE);
            }
        });

        spnPractice = (Spinner) findViewById(R.id.list_practice);
        spnPractice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (String.valueOf(spnPractice.getSelectedItem().toString())) {
                    case "Bấm Để Chọn Nội Dung":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_PHUONGPHAP);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.GONE);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Hàm Số":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_HAMSO);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Hình Học Không Gian":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_HINHHOCKG);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Mặt Tròn Xoay":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_MATTRONXOAY);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Hàm Mũ Logarit":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_MULOGARIT);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Số Phức":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_SOPHUC);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Tích Phân":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_TICHPHAN);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case "Các Phương Pháp Giải Nhanh":
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_PHUONGPHAP);
                        replyAdapter = new PracticeReplyAdapter(PracticeActivity.this, problems);
                        answerAdapter = new PracticeAnswerAdapter(PracticeActivity.this, problems);
                        rvAnswer.setAdapter(answerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        btnAnswer.setVisibility(View.VISIBLE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public String changeToString(ArrayList<Problem> problems) {
        String s = "";
        for (int i = 0; i < problems.size(); i++) {
            s = s + "\n" + "<br>" + "Câu " + (i + 1) + ": " + problems.get(i).getQuestion() + "\n" + "<br>";
        }
        return s;
    }
}
