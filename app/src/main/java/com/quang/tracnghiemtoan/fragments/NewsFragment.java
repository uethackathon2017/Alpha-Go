package com.quang.tracnghiemtoan.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quang.tracnghiemtoan.R;

import static com.quang.tracnghiemtoan.R.id.swipeLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView webView;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news, container, false);


        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://tin.tuyensinh247.com/thi-dai-hoc-va-thi-thpt-quoc-gia-2017-e543.html");

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl("http://tin.tuyensinh247.com/thi-dai-hoc-va-thi-thpt-quoc-gia-2017-e543.html");
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        return v;
    }

}
