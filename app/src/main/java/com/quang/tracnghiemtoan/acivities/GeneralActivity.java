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
import android.widget.Button;
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

        arrayListExam = sqLiteDataController.getAllProblem();
        for (int i = 0; i < arrayListExam.size(); i++) {
            titleExam.add(arrayListExam.get(i).get(0).getQuestion());
        }

        problems = creatExam(arrayListExam);
        mathJaxWebView.setText(changeToString(problems));
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
                final MaterialDialog materialDialog = new MaterialDialog.Builder(GeneralActivity.this)
                        .title("Bạn có muốn nộp bài không?")
                        .positiveText("Có")
                        .negativeText("Không")
                        .show();
                View positiveAction = materialDialog.getActionButton(DialogAction.POSITIVE);
                positiveAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rvRightAnswer.setVisibility(View.VISIBLE);
                        btnAnswer.setEnabled(false);
                        materialDialog.dismiss();
                        String[] strings = replyAdapter.getListAnswer();
                        int count = 0;
                        for (int i = 0; i < 50; i++) {
                            if (strings[i].equals(problems.get(i).getRightAnswer())) count++;
                        }
                        new MaterialDialog.Builder(GeneralActivity.this)
                                .title("Bạn đã trả lời đúng " + count + "/50 câu.")
                                .positiveText("OK")
                                .show();
                    }
                });
            }
        });
        showDate();
    }

    public String changeToString(ArrayList<Problem> problems) {
        String s = "";
        for (int i = 0; i < problems.size(); i++) {
            s = s + "</br>" + "<u><b>Câu " + (i + 1) + ":</b></u> " + problems.get(i).getQuestion() + "</br>";
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

    public ArrayList<Problem> creatExam(ArrayList<ArrayList<Problem>> arrayListExam) {
        ArrayList<Problem> problemArrayList = new ArrayList<>();
        problemArrayList = randomExam(arrayListExam.get(0), 11, problemArrayList);
        problemArrayList = randomExam(arrayListExam.get(1), 8, problemArrayList);
        problemArrayList = randomExam(arrayListExam.get(2), 8, problemArrayList);
        problemArrayList = randomExam(arrayListExam.get(3), 10, problemArrayList);
        problemArrayList = randomExam(arrayListExam.get(4), 6, problemArrayList);
        problemArrayList = randomExam(arrayListExam.get(5), 7, problemArrayList);
        java.util.Collections.shuffle(problemArrayList);
        return problemArrayList;
    }

    public ArrayList<Problem> randomExam(ArrayList<Problem> arrayList, int count, ArrayList<Problem> arrayList2) {
        java.util.Collections.shuffle(arrayList);
        for (int i = 0; i < count; i++) {
            arrayList2.add(arrayList.get(i));
        }
        return arrayList2;
    }
}
