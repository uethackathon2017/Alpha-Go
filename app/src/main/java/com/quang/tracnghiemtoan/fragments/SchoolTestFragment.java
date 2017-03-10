package com.quang.tracnghiemtoan.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.acivities.ViewSchoolTestActivity;
import com.quang.tracnghiemtoan.adapters.SchoolTestAdapter;
import com.quang.tracnghiemtoan.models.SchoolTest;
import com.quang.tracnghiemtoan.models.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolTestFragment extends Fragment {

    private RecyclerView rvSchoolTest;
    private SchoolTestAdapter adapter;
    private ArrayList<SchoolTest> listSchoolTest;
    private View v;

    public SchoolTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_school_test, container, false);
        rvSchoolTest = (RecyclerView) v.findViewById(R.id.recyclerViewSchoolTest);
        listSchoolTest = new ArrayList<>();
        adapter = new SchoolTestAdapter(listSchoolTest);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        rvSchoolTest.setLayoutManager(layoutManager);
        rvSchoolTest.setAdapter(adapter);
        new getData().execute("https://www.dropbox.com/s/p30d5gspenhdvel/data.json?dl=1");
        adapter.setOnItemClickListener(new SchoolTestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Variables.schoolTest = listSchoolTest.get(position);
                startActivity(new Intent(v.getContext(), ViewSchoolTestActivity.class));
            }
        });
        return v;
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
                JSONArray list = new JSONArray(s);
                for (int i = 0; i < list.length(); i++) {
                    JSONObject object = list.getJSONObject(i);
                    String title = object.getString("title");
                    String linkTest = object.getString("linktest");
                    String dateTest = object.getString("datetest");
                    String linkImage = object.getString("linkimage");
                    listSchoolTest.add(new SchoolTest(title, linkTest, dateTest, linkImage));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
