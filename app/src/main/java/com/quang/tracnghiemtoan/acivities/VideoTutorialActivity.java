package com.quang.tracnghiemtoan.acivities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.pedrovgs.DraggableView;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.adapters.VideoTutorialAdapter;
import com.quang.tracnghiemtoan.fragments.VideoFragment;
import com.quang.tracnghiemtoan.models.Variables;
import com.quang.tracnghiemtoan.models.VideoTutorial;
import com.quang.tracnghiemtoan.untils.StringUntils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class VideoTutorialActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ArrayList<VideoTutorial> listVideoTutorial;
    private VideoTutorialAdapter adapter;
    private RecyclerView rvVideoTutorial;
    private VideoFragment videoFragment;
    private DraggableView draggableView;
    private TextView tvTitle, tvDescription;
    private ProgressDialog progressDialog;
    private ArrayList<VideoTutorial> listFilterVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById();

        listVideoTutorial = new ArrayList<>();

        adapter = new VideoTutorialAdapter(listVideoTutorial);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVideoTutorial.setLayoutManager(layoutManager);
        rvVideoTutorial.setAdapter(adapter);

        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              for (int i = 0; i < 10; i++) {
                                  new getData().execute(Variables.LINK_PLAYLIST + Variables.PAGE_TOKEN[i]);
                              }

                          }
                      }

        );

        //Click item of recyclerView
        adapter.setOnItemClickListener(new VideoTutorialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                VideoTutorial videoTutorial = listVideoTutorial.get(position);
                tvTitle.setText(videoTutorial.getTitleVideo());
                tvDescription.setText(videoTutorial.getDescriptionVideo());
                videoFragment.setVideoId(videoTutorial.getIdVideo());
                draggableView.setVisibility(View.VISIBLE);
                draggableView.maximize();
            }
        });
    }
    //Init data

    private void findViewById() {
        rvVideoTutorial = (RecyclerView) findViewById(R.id.recyclerViewVideoTutorial);
        draggableView = (DraggableView) findViewById(R.id.draggable_view);
        draggableView.setVisibility(View.GONE);
        draggableView.setClickToMaximizeEnabled(true);
        videoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.player);
        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvDescription = (TextView) findViewById(R.id.textViewDescription);
        progressDialog = new ProgressDialog(VideoTutorialActivity.this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        SearchView searchView;
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setQueryHint(getString(R.string.search_input_key_word));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    //Search View


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        listFilterVideo = new ArrayList<>();
        for (int i = 0; i < listVideoTutorial.size(); i++) {
            if (listVideoTutorial.get(i).toString().contains(StringUntils.unAccentAndLower(newText)))
                listFilterVideo.add(listVideoTutorial.get(i));
        }
        rvVideoTutorial.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoTutorialAdapter(listFilterVideo);
        rvVideoTutorial.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new VideoTutorialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                VideoTutorial videoTutorial = listFilterVideo.get(position);
                tvTitle.setText(videoTutorial.getTitleVideo());
                tvDescription.setText(videoTutorial.getDescriptionVideo());
                draggableView.setVisibility(View.INVISIBLE);
                videoFragment.pause();
                videoFragment.setVideoId(videoTutorial.getIdVideo());
                draggableView.setVisibility(View.VISIBLE);
                draggableView.maximize();
            }
        });
        return true;
    }

    private static String getContentFromURL(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    //Get Data from Youtube Playlist

    private class getData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return getContentFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject root = new JSONObject(s);

                JSONArray listVideo = root.getJSONArray(getString(R.string.json_youtube_items));
                for (int i = 0; i < listVideo.length(); i++) {
                    JSONObject snippet = listVideo.getJSONObject(i).getJSONObject(getString(R.string.json_youtube_snippet));
                    String title = snippet.getString(getString(R.string.json_youtube_title));
                    String description = snippet.getString(getString(R.string.json_youtube_description));
                    String thumbnail = snippet.getJSONObject(getString(R.string.json_youtube_thumbnails)).
                            getJSONObject(getString(R.string.json_youtube_medium)).getString(getString(R.string.json_youtbe_url));
                    String videoId = snippet.getJSONObject(getString(R.string.json_youtube_resourceId)).
                            getString(getString(R.string.json_youtube_videoId));
                    VideoTutorial videoTutorial = new VideoTutorial(title, description, videoId, thumbnail);
                    listVideoTutorial.add(videoTutorial);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (progressDialog.isShowing()) progressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }
}
