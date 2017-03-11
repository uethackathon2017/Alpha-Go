package com.quang.tracnghiemtoan.adapters;

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

    private ArrayList<Problem> listReplyAnswer;
    private View v;

    public PracticeReplyAdapter(ArrayList<Problem> listReplyAnswer) {
        this.listReplyAnswer = listReplyAnswer;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_reply_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText( (position + 1) + ". ");
        holder.answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answerA.getText().equals(listReplyAnswer.get(position).getRightAnswer())) {
                    holder.answerA.setTextColor(view.getResources().getColor(R.color.red));
                } else {
                    holder.answerC.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerB.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerA.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerD.setTextColor(v.getResources().getColor(R.color.black));
                }
            }
        });
        holder.answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answerB.getText().equals(listReplyAnswer.get(position).getRightAnswer())) {
                    holder.answerB.setTextColor(v.getResources().getColor(R.color.darkred));
                } else {
                    holder.answerC.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerB.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerA.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerD.setTextColor(v.getResources().getColor(R.color.black));
                }
            }
        });
        holder.answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answerC.getText().equals(listReplyAnswer.get(position).getRightAnswer())) {
                    holder.answerC.setTextColor(v.getResources().getColor(R.color.darkred));
                } else {
                    holder.answerC.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerB.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerA.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerD.setTextColor(v.getResources().getColor(R.color.black));
                }
            }
        });
        holder.answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.answerD.getText().equals(listReplyAnswer.get(position).getRightAnswer())) {
                    holder.answerD.setTextColor(v.getResources().getColor(R.color.darkred));
                } else {
                    holder.answerC.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerB.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerA.setTextColor(v.getResources().getColor(R.color.black));
                    holder.answerD.setTextColor(v.getResources().getColor(R.color.black));
                }
            }
        });
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
