package com.quang.tracnghiemtoan.acivities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.PracticeReplyAdapter;
import com.quang.tracnghiemtoan.adapters.PracticeRightAnswerAdapter;
import com.quang.tracnghiemtoan.controllers.SQLiteDataController;
import com.quang.tracnghiemtoan.models.Problem;
import com.quang.tracnghiemtoan.views.MathJaxWebView;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralActivity extends AppCompatActivity {

    private ArrayList<ArrayList<Problem>> arrayListExam;
    private MathJaxWebView mathJaxWebView;
    private SQLiteDataController sqLiteDataController;
    private ArrayList<String> titleExam = new ArrayList<>();
    private ArrayList<Problem> problems;
    private Spinner spnSolution;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Button btnAnswer;
    private TextView tvCountTimer;
    private RecyclerView rvReply;
    private RecyclerView rvRightAnswer;
    private PracticeReplyAdapter replyAdapter;
    private PracticeRightAnswerAdapter rightAnswerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.END);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_solution);

        mathJaxWebView = (MathJaxWebView) findViewById(R.id.solution_webView);
        mathJaxWebView.getSettings().setJavaScriptEnabled(true);

        sqLiteDataController = new SQLiteDataController(GeneralActivity.this);
        try {
            sqLiteDataController.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrayListExam = sqLiteDataController.getAllExam();
        for (int i = 0; i < arrayListExam.size(); i++) {
            titleExam.add(arrayListExam.get(i).get(0).getQuestion());
        }


        problems = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(GeneralActivity.this);

        rvReply = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerViewReply);
        rvReply.setHasFixedSize(true);
        rvReply.setLayoutManager(manager);
        rvReply.setItemAnimator(new DefaultItemAnimator());
        replyAdapter = new PracticeReplyAdapter(problems);
        rvReply.setAdapter(replyAdapter);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(GeneralActivity.this);
        rvRightAnswer = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerViewRightAnswer);
        rvRightAnswer.setHasFixedSize(true);
        rvRightAnswer.setLayoutManager(manager2);
        rvRightAnswer.setItemAnimator(new DefaultItemAnimator());
        rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
        rvRightAnswer.setAdapter(rightAnswerAdapter);

        tvCountTimer = (TextView) findViewById(R.id.tv_counttimmer);
        btnAnswer = (Button) navigationView.getHeaderView(0).findViewById(R.id.buttonAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvRightAnswer.setVisibility(View.VISIBLE);
            }
        });


        spnSolution = (Spinner) findViewById(R.id.list_solution);
        ArrayAdapter<String> karant_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, titleExam);
        spnSolution.setAdapter(karant_adapter);

        spnSolution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mathJaxWebView.setText(changeToString(arrayListExam.get(i)));
                replyAdapter = new PracticeReplyAdapter(arrayListExam.get(i));
                problems = arrayListExam.get(i);
                problems.remove(0);
                rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
                rvRightAnswer.setAdapter(rightAnswerAdapter);
                rvReply.setAdapter(replyAdapter);
                showDate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        showDate();
    }

    public String changeToString(ArrayList<Problem> problems) {
        String s = "";
        s = s + "<br>" + problems.get(0).getQuestion() + "\n" + "<br>";
        for (int i = 1; i < problems.size(); i++) {
            s = s + "\n" + "<br>" + "Câu " + i + ": " + problems.get(i).getQuestion() + "\n" + "<br>";
        }
        return s;
    }

    public void showDate() {
        new CountDownTimer(5400000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountTimer.setText((millisUntilFinished / 1000) / 60 + ":" + ((millisUntilFinished / 1000) % 60));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvCountTimer.setText("Hết Giờ");
                final MaterialDialog dialogwarning = new MaterialDialog.Builder(GeneralActivity.this)
                        .title("Hết thời gian. Mời bạn xem lại bài làm.")
                        .positiveText("OK")
                        .contentGravity(GravityEnum.START)
                        .show();
                final View positiveActionWarning = dialogwarning.getActionButton(DialogAction.POSITIVE);
                positiveActionWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogwarning.cancel();
                        tvCountTimer.setVisibility(View.GONE);
                        btnAnswer.setVisibility(View.VISIBLE);
                        rvRightAnswer.setVisibility(View.VISIBLE);
                        drawer.openDrawer(Gravity.LEFT);
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(GeneralActivity.this);
        inflater.inflate(R.menu.practice_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.buttonOnline) {
            drawer.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }
}
