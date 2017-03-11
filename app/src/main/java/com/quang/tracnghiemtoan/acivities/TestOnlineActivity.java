package com.quang.tracnghiemtoan.acivities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.ImageTestAdapter;
import com.quang.tracnghiemtoan.adapters.TestOnlineAnswerAdapter;
import com.quang.tracnghiemtoan.models.ImageTest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestOnlineActivity extends AppCompatActivity {

    private ArrayList<ImageTest> listImageTest;
    private ImageTestAdapter adapter;
    private TestOnlineAnswerAdapter answerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_online);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_test_online);

        RecyclerView rvAnswer = (RecyclerView) navigationView.getHeaderView(0).findViewById(R.id.recyclerView);
        rvAnswer.setLayoutManager(new LinearLayoutManager(this));

        answerAdapter = new TestOnlineAnswerAdapter();
        rvAnswer.setAdapter(answerAdapter);
        answerAdapter.notifyDataSetChanged();

        listImageTest = new ArrayList<>();
        adapter = new ImageTestAdapter(listImageTest);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rvImageTest = (RecyclerView) findViewById(R.id.recyclerViewImageTest);
        rvImageTest.setLayoutManager(layoutManager);
        rvImageTest.setAdapter(adapter);
        rvImageTest.setHasFixedSize(true);
        rvImageTest.setItemViewCacheSize(1000);

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "745323575630461/photos?fields=height,width,link",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject root = response.getJSONObject();
                        try {
                            JSONArray array = root.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                int height = object.getInt("height");
                                int width = object.getInt("width");
                                String link = object.getString("link");
                                String id = object.getString("id");
                                ImageTest imageTest = new ImageTest(height, width, link, id);
                                listImageTest.add(imageTest);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

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
}
