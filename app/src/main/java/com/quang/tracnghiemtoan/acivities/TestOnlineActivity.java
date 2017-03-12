package com.quang.tracnghiemtoan.acivities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.TestOnlineAnswerAdapter;

import java.io.File;
import java.io.InputStream;

public class TestOnlineActivity extends AppCompatActivity {

    private TestOnlineAnswerAdapter answerAdapter;
    private DrawerLayout drawer;
    private PDFView pdfView;
    private ProgressDialog progressDialog;
    private FloatingActionButton btnSave, btnShare;
    private FloatingActionsMenu floatingActionsMenu;
    private final String LINK_DE_THI = "https://www.dropbox.com/s/woisfn43sjf3vw4/DETHIONLINE.pdf?dl=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_online);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.END);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_test_online);

        RecyclerView rvAnswer = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerView);
        rvAnswer.setLayoutManager(new LinearLayoutManager(this));

        answerAdapter = new TestOnlineAnswerAdapter();
        rvAnswer.setAdapter(answerAdapter);
        answerAdapter.notifyDataSetChanged();

        pdfView = (PDFView) findViewById(R.id.pdfView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
        progressDialog.show();
        btnSave = (FloatingActionButton) findViewById(R.id.button_Save);
        btnShare = (FloatingActionButton) findViewById(R.id.button_share);
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions_down);
        Ion.with(getApplicationContext())
                .load(LINK_DE_THI)
                .asInputStream().setCallback(new FutureCallback<InputStream>() {
            @Override
            public void onCompleted(Exception e, InputStream result) {
                pdfView.fromStream(result).load();
                progressDialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                final String fileName = URLUtil.guessFileName(LINK_DE_THI, null, null);
                File file = new File(fileDir, fileName);
                progressDialog.show();
                Ion.with(getApplicationContext())
                        .load(LINK_DE_THI)
                        .write(file).setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Lưu thành công " + fileName, Toast.LENGTH_LONG).show();
                    }
                });
                floatingActionsMenu.collapse();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, LINK_DE_THI);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                floatingActionsMenu.collapse();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final DatabaseReference mRef = database.getReference("TestOnline/" + user.getUid());
        DatabaseReference isTestRef = database.getReference("IsTest");
        isTestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Integer.class) == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TestOnlineActivity.this);
                    builder.setMessage("Đã hết thời gian thi. Kết quả của bạn đã được ghi lại!");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String answerTestOnline = "";
                            String[] listAnswer = answerAdapter.getListAnswer();
                            for (int i = 0; i < 50; i++) {
                                answerTestOnline += (i + 1);
                                if (listAnswer[i] == null) answerTestOnline += " ";
                                else answerTestOnline += listAnswer[i];
                            }
                            mRef.push().setValue(answerTestOnline);
                            finish();
                        }
                    });
                    builder.create();
                    builder.setCancelable(false);
                    if (!isFinishing()) builder.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button btnSubmit = (Button) navigationView.getHeaderView(0).findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerTestOnline = "";
                String[] listAnswer = answerAdapter.getListAnswer();
                for (int i = 0; i < 50; i++) {
                    answerTestOnline += (i + 1);
                    if (listAnswer[i] == null) answerTestOnline += " ";
                    else answerTestOnline += listAnswer[i];
                }
                mRef.push().setValue(answerTestOnline);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(TestOnlineActivity.this);
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else super.onBackPressed();
    }
}
