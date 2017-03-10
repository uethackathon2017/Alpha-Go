package com.quang.tracnghiemtoan.acivities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.MessageAdapter;
import com.quang.tracnghiemtoan.adapters.UserOnlineAdapter;
import com.quang.tracnghiemtoan.models.Message;
import com.quang.tracnghiemtoan.models.UserOnline;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatRoomActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText edtMessage;
    private TextView tvNumberOnline, tvLeftName;
    private ImageView imvLeftAvatar;
    private FirebaseUser user;
    private RecyclerView rvMessage, rvUserOnline;
    private ProgressDialog progressDialog;
    private ArrayList<Message> listMessages;
    private MessageAdapter adapter;
    private String childName;
    private FirebaseDatabase database;
    private ArrayList<UserOnline> listUserOnline;
    private DatabaseReference myConnectionsRef, messageRef;
    private UserOnlineAdapter userOnlineAdapter;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_chat_room);

        findViewById();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        try {
            tvLeftName.setText(Profile.getCurrentProfile().getName());
            String linkAvatar = "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?width=500";
            Glide.with(ChatRoomActivity.this).load(linkAvatar).into(imvLeftAvatar);
        } catch (Exception e) {
            signOut();
        }

        user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        messageRef = database.getReference("chat/message");

        listMessages = new ArrayList<>();
        listUserOnline = new ArrayList<>();
        adapter = new MessageAdapter(listMessages);
        userOnlineAdapter = new UserOnlineAdapter(listUserOnline);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(layoutManager);
        rvMessage.setAdapter(adapter);
        rvUserOnline.setLayoutManager(new LinearLayoutManager(this));
        rvUserOnline.setAdapter(userOnlineAdapter);

        messageRef.limitToLast(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listMessages.add(dataSnapshot.getValue(Message.class));
                rvMessage.scrollToPosition(listMessages.size() - 1);
                adapter.notifyDataSetChanged();
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myConnectionsRef = database.getReference("chat/online");
        myConnectionsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                boolean isContain = false;
                UserOnline userOnline = dataSnapshot.getValue(UserOnline.class);
                for (int i = 0; i < listUserOnline.size(); i++) {
                    if (listUserOnline.get(i).getFacebookId().equals(userOnline.getFacebookId())) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) listUserOnline.add(userOnline);
                userOnlineAdapter.notifyDataSetChanged();
                String textNumberOnline = "Thành viên trực tuyến " + listUserOnline.size();
                tvNumberOnline.setText(textNumberOnline);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int pos = 0;
                UserOnline userOnline = dataSnapshot.getValue(UserOnline.class);
                for (int i = 0; i < listUserOnline.size(); i++) {
                    if (userOnline.getFacebookId().equals(listUserOnline.get(i).getFacebookId()))
                        pos = i;
                }
                if (listUserOnline.size() > pos)
                    listUserOnline.remove(pos);
                userOnlineAdapter.notifyDataSetChanged();
                String textNumberOnline = "Thành viên trực tuyến " + listUserOnline.size();
                tvNumberOnline.setText(textNumberOnline);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtMessage.getText().toString().trim();
                Profile currentProfile = Profile.getCurrentProfile();
                String userName = currentProfile.getName();
                String facebookId = currentProfile.getId();
                String firebaseId = user.getUid();
                Calendar c = Calendar.getInstance();
                long timeSend = c.getTime().getTime();
                if (message.length() > 0) {
                    edtMessage.setText("");
                    messageRef.push().setValue(new Message(userName, facebookId, firebaseId, message, timeSend));
                }
            }
        });
        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String tag = "@" + listMessages.get(position).getUserName();
                edtMessage.append(tag);
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        finish();
    }

    private void findViewById() {
        btnSend = (Button) findViewById(R.id.buttonSend);
        edtMessage = (EditText) findViewById(R.id.editTextMessage);
        tvNumberOnline = (TextView) findViewById(R.id.textViewNumberOnline);
        tvLeftName = (TextView) findViewById(R.id.textViewLeftName);
        imvLeftAvatar = (ImageView) findViewById(R.id.imageViewLeftAvatar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        rvMessage = (RecyclerView) findViewById(R.id.recyclerViewMessage);
        rvUserOnline = (RecyclerView) findViewById(R.id.recyclerViewOnline);
        progressDialog = new ProgressDialog(ChatRoomActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listMessages.size() > 0)
            rvMessage.scrollToPosition(listMessages.size() - 1);
        myConnectionsRef = database.getReference("chat/online");
        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean aBoolean = snapshot.getValue(Boolean.class);
                if (aBoolean) {
                    DatabaseReference con = myConnectionsRef.push();
                    childName = con.getKey();
                    assert user != null;
                    try {
                        UserOnline userOnline = new UserOnline(user.getUid(), Profile.getCurrentProfile().getId(), Profile.getCurrentProfile().getName());
                        con.setValue(userOnline);
                        con.onDisconnect().removeValue();
                    } catch (Exception e) {
                        signOut();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            FirebaseDatabase.getInstance().getReference("chat/online").child(childName).removeValue();
        } catch (Exception ignored) {

        }
        if (progressDialog.isShowing()) progressDialog.dismiss();
        listUserOnline.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(ChatRoomActivity.this);
        inflater.inflate(R.menu.chat_menu, menu);
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
