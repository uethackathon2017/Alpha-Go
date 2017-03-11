package com.quang.tracnghiemtoan.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quang.tracnghiemtoan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private ProgressDialog progressDialog;


    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Vui lòng đợi...");
        WebView webView = (WebView) v.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://tin.tuyensinh247.com/thi-dai-hoc-va-thi-thpt-quoc-gia-2017-e543.html");
        return v;
    }

}
