package com.quang.tracnghiemtoan.acivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.Variables;

public class ViewSchoolTestActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_school_test);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/viewer?url=" + Variables.schoolTest.getLinkTest());
    }
}
