package com.quang.tracnghiemtoan.acivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.barteksc.pdfviewer.PDFView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.Variables;

import java.io.File;
import java.io.InputStream;

public class ViewSchoolTestActivity extends AppCompatActivity {

    private PDFView pdfView;
    private ProgressDialog progressDialog;
    private FloatingActionButton btnSave, btnShare;
    private FloatingActionsMenu floatingActionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_school_test);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi...");
        progressDialog.show();
        btnSave = (FloatingActionButton) findViewById(R.id.button_Save);
        btnShare = (FloatingActionButton) findViewById(R.id.button_share);
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions_down);
        Ion.with(getApplicationContext())
                .load(Variables.schoolTest.getLinkTest())
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
                final String fileName = URLUtil.guessFileName(Variables.schoolTest.getLinkTest(), null, null);
                File file = new File(fileDir, fileName);
                progressDialog.show();
                Ion.with(getApplicationContext())
                        .load(Variables.schoolTest.getLinkTest())
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
                sendIntent.putExtra(Intent.EXTRA_TEXT, Variables.schoolTest.getLinkTest());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                floatingActionsMenu.collapse();
            }
        });
    }
}

