package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.quang.tracnghiemtoan.R;


/**
 * Created by PhungVanQuang on 3/10/2017.
 */

public class TestOnlineAnswerAdapter extends RecyclerView.Adapter<TestOnlineAnswerAdapter.ViewHolder> {

    private View v;
    private String[] listAnswer = new String[50];

    public TestOnlineAnswerAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_reply_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(position + 1 + ". ");
        holder.answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAnswer[position] = "A";
            }
        });
        holder.answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAnswer[position] = "B";
            }
        });
        holder.answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAnswer[position] = "C";
            }
        });
        holder.answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listAnswer[position] = "D";
            }
        });
    }


    @Override
    public int getItemCount() {
        return 50;
    }

    public String[] getListAnswer() {
        return listAnswer;
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
