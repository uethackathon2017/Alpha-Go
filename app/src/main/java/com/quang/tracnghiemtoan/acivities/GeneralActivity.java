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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.PracticeReplyAdapter;
import com.quang.tracnghiemtoan.adapters.PracticeRightAnswerAdapter;
import com.quang.tracnghiemtoan.controllers.SQLiteDataController;
import com.quang.tracnghiemtoan.models.Problem;
import com.quang.tracnghiemtoan.views.MathJaxWebView;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralActivity extends AppCompatActivity {

    private ArrayList<String> titleExam = new ArrayList<>();
    private ArrayList<Problem> problems;
    private DrawerLayout drawer;
    private Button btnAnswer;
    private TextView tvCountTimer, tvName;
    private RecyclerView rvRightAnswer;
    private PracticeReplyAdapter replyAdapter;
    private FirebaseDatabase database;
    private DatabaseReference pointRef;
    private FirebaseUser user;
    private long currentPoint = 0;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.END);

        tvName = (TextView) findViewById(R.id.textViewName);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_solution);

        MathJaxWebView mathJaxWebView = (MathJaxWebView) findViewById(R.id.solution_webView);
        mathJaxWebView.getSettings().setJavaScriptEnabled(true);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        pointRef = database.getReference("Profile/" + user.getUid() + "/point");
        Toast.makeText(getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();
        pointRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentPoint = dataSnapshot.getValue(Integer.class);
//                if (currentPoint > 0)
                Toast.makeText(getApplicationContext(), currentPoint + "", Toast.LENGTH_LONG).show();
                tvName.setText("Thí sinh " + user.getDisplayName() + ". Điểm tích lũy: " + currentPoint);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SQLiteDataController sqLiteDataController = new SQLiteDataController(GeneralActivity.this);
        try {
            sqLiteDataController.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ArrayList<Problem>> arrayListExam = sqLiteDataController.getAllProblem();
        for (int i = 0; i < arrayListExam.size(); i++) {
            titleExam.add(arrayListExam.get(i).get(0).getQuestion());
        }

        problems = creatExam(arrayListExam);
        mathJaxWebView.setText(changeToString(problems));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(GeneralActivity.this);

        RecyclerView rvReply = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerViewReply);
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
        PracticeRightAnswerAdapter rightAnswerAdapter = new PracticeRightAnswerAdapter(problems);
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
                        for (int i = 0; i < 50; i++) {
                            if (strings[i] != null && strings[i].equals(problems.get(i).getRightAnswer()))
                                count++;
                        }
                        new MaterialDialog.Builder(GeneralActivity.this)
                                .title("Bạn đã trả lời đúng " + count + "/50 câu.")
                                .positiveText("OK")
                                .show();
                        if (currentPoint > 0) {
                            pointRef.setValue(currentPoint + count);
                        }
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
        //5400000
        new CountDownTimer(19000, 1000) {
            public void onTick(long millisUntilFinished) {
                long hour = (millisUntilFinished / 1000) / 60;
                long minute = (millisUntilFinished / 1000) % 60;
                if (hour < 10) {
                    if (minute < 10)
                        tvCountTimer.setText("Thời gian còn lại: 0" + hour + " : 0" + minute);
                    else tvCountTimer.setText("Thời gian còn lại: 0" + hour + " : " + minute);
                } else {
                    if (minute < 10)
                        tvCountTimer.setText("Thời gian còn lại: " + hour + " : 0" + minute);
                    else tvCountTimer.setText("Thời gian còn lại: " + hour + " : " + minute);
                }
            }

            public void onFinish() {
                tvCountTimer.setText("Hết giờ!");
                btnAnswer.setEnabled(false);
                String[] strings = replyAdapter.getListAnswer();
                for (int i = 0; i < 50; i++) {
                    if (strings[i] != null && strings[i].equals(problems.get(i).getRightAnswer()))
                        count++;
                }
                final MaterialDialog dialogwarning = new MaterialDialog.Builder(GeneralActivity.this)
                        .title("Hết thời gian. Bạn đã trả lời đúng " + count + "/50 câu.")
                        .positiveText("OK")
                        .contentGravity(GravityEnum.START)
                        .cancelable(false)
                        .show();
                final View positiveActionWarning = dialogwarning.getActionButton(DialogAction.POSITIVE);
                positiveActionWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogwarning.dismiss();
                        rvRightAnswer.setVisibility(View.VISIBLE);
                        drawer.openDrawer(Gravity.RIGHT);
                        if (currentPoint > 0) {
                            pointRef.setValue(currentPoint + count);
                        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
