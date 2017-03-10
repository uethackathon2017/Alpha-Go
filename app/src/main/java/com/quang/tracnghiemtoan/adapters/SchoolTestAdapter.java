package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.quang.tracnghiemtoan.models.SchoolTest;

import java.util.ArrayList;

/**
 * Created by QUANG on 3/10/2017.
 */

public class SchoolTestAdapter extends RecyclerView.Adapter<SchoolTestAdapter.Holder> {

    private ArrayList<SchoolTest> listSchoolTest;

    public SchoolTestAdapter(ArrayList<SchoolTest> listSchoolTest) {
        this.listSchoolTest = listSchoolTest;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listSchoolTest.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
