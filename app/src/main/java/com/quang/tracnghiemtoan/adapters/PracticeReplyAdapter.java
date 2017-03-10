package com.quang.tracnghiemtoan.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.Problem;

import java.util.ArrayList;


/**
 * Created by PhungVanQuang on 3/10/2017.
 */

public class PracticeReplyAdapter extends RecyclerView.Adapter<PracticeReplyAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<Problem> listReplyAnswer;

    public PracticeReplyAdapter(Activity activity, ArrayList<Problem> listReplyAnswer) {
        this.activity = activity;
        this.listReplyAnswer = listReplyAnswer;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_reply_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText("Câu " + (position + 1) + ":");
    }

    @Override
    public int getItemCount() {
        return listReplyAnswer.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RadioButton answerA;
        RadioButton answerB;
        RadioButton answerC;
        RadioButton answerD;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.count_answer);
            answerA = (RadioButton) itemView.findViewById(R.id.answerA);
            answerB = (RadioButton) itemView.findViewById(R.id.answerB);
            answerC = (RadioButton) itemView.findViewById(R.id.answerC);
            answerD = (RadioButton) itemView.findViewById(R.id.answerD);
        }
    }
}
