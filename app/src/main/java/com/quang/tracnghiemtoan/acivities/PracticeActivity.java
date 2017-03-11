package com.quang.tracnghiemtoan.acivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.PracticeChapterAdapter;
import com.quang.tracnghiemtoan.adapters.PracticeReplyAdapter;
import com.quang.tracnghiemtoan.adapters.PracticeRightAnswerAdapter;
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
    private Toolbar toolbar;
    private Menu menu;
    private Button btnAnswer;
    private RecyclerView rvAnswerReply;
    private RecyclerView rvAnswer;
    private PracticeReplyAdapter replyAdapter;
    private PracticeRightAnswerAdapter rightAnswerAdapter;
    private DrawerLayout drawer;
    private LinearLayout practicechoosechapter;

    private String[] main_text = new String[]{"Hàm số", "Hình học không gian", "Mặt tròn xoay", "Hàm mũ logarit", "Số phức", "Tích phân", "Các phương pháp giải nhanh"};

    private boolean checkpracticechoosechapter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_practice);

        practicechoosechapter = (LinearLayout) findViewById(R.id.practice_choose_chapter);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_pratice_chapter);
        PracticeChapterAdapter chapterAdapter = new PracticeChapterAdapter(main_text);
        recyclerView.setLayoutManager(new GridLayoutManager(PracticeActivity.this, 1));
        recyclerView.setAdapter(chapterAdapter);


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

        rvAnswerReply = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerViewReply);
        rvAnswerReply.setHasFixedSize(true);
        rvAnswerReply.setLayoutManager(manager);
        rvAnswerReply.setItemAnimator(new DefaultItemAnimator());
        replyAdapter = new PracticeReplyAdapter(problems);
        rvAnswerReply.setAdapter(replyAdapter);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(PracticeActivity.this);
        rvAnswer = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerViewRightAnswer);
        rvAnswer.setHasFixedSize(true);
        rvAnswer.setLayoutManager(manager2);
        rvAnswer.setItemAnimator(new DefaultItemAnimator());
        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
        rvAnswer.setAdapter(rightAnswerAdapter);

        btnAnswer = (Button) navigationView.getHeaderView(0).findViewById(R.id.buttonAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvAnswer.setVisibility(View.VISIBLE);
            }
        });

        chapterAdapter.setOnItemClickListener(new PracticeChapterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_HAMSO);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case 1:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_HINHHOCKG);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case 2:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_MATTRONXOAY);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case 3:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_MULOGARIT);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case 4:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_SOPHUC);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case 5:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_TICHPHAN);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                    case 6:
                        menu.getItem(0).setVisible(true);
                        checkpracticechoosechapter = false;
                        practicechoosechapter.setVisibility(View.GONE);
                        mathJaxWebView.setVisibility(View.VISIBLE);
                        problems = sqLiteDataController.getAllProblem(Constant.KIND_PHUONGPHAP);
                        replyAdapter = new PracticeReplyAdapter(problems);
                        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                        rvAnswer.setAdapter(rightAnswerAdapter);
                        rvAnswerReply.setAdapter(replyAdapter);
                        rvAnswer.setVisibility(View.GONE);
                        mathJaxWebView.setText(changeToString(problems));
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(PracticeActivity.this);
        this.menu = menu;
        inflater.inflate(R.menu.practice_menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.buttonOnline) {
            drawer.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    public String changeToString(ArrayList<Problem> problems) {
        String s = "";
        for (int i = 0; i < problems.size(); i++) {
            s = s + "\n" + "<br>" + "Câu " + (i + 1) + ": " + problems.get(i).getQuestion() + "\n" + "<br>";
        }
        return s;
    }

    @Override
    public void onBackPressed() {
        if (!checkpracticechoosechapter) {
            checkpracticechoosechapter = true;
            practicechoosechapter.setVisibility(View.VISIBLE);
            mathJaxWebView.setVisibility(View.GONE);
            menu.getItem(0).setVisible(false);
        } else {
            super.onBackPressed();
        }
    }
}
