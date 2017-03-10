package com.quang.tracnghiemtoan.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.Problem;

import java.util.ArrayList;

/**
 * Created by PhungVanQuang on 3/10/2017.
 */

public class PracticeAnswerAdapter extends RecyclerView.Adapter<PracticeAnswerAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<Problem> listAnswer;

    public PracticeAnswerAdapter(Activity activity, ArrayList<Problem> listAnswer) {
        this.activity = activity;
        this.listAnswer = listAnswer;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_answer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(listAnswer.get(position).getRightAnswer());
    }

    @Override
    public int getItemCount() {
        return listAnswer.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.answer);
        }
    }
}
