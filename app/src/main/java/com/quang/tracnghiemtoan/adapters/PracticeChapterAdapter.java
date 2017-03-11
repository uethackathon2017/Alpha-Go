package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quang.tracnghiemtoan.R;

/**
 * Created by PhungVanQuang on 3/6/2017.
 */

public class PracticeChapterAdapter extends RecyclerView.Adapter<PracticeChapterAdapter.ViewHolder> {

    private String[] main_text;
    private View v;
    private OnItemClickListener mItemClickListener;

    public PracticeChapterAdapter(String[] main_text) {
        this.main_text = main_text;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_practice_chapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMainText.setText(main_text[position]);
        switch (position) {
            case 0:
                holder.linearLayout.setBackgroundResource(R.drawable.bga);
                break;
            case 1:
                holder.linearLayout.setBackgroundResource(R.drawable.bgb);
                break;
            case 2:
                holder.linearLayout.setBackgroundResource(R.drawable.bgc);
                break;
            case 3:
                holder.linearLayout.setBackgroundResource(R.drawable.bgd);
                break;
            case 4:
                holder.linearLayout.setBackgroundResource(R.drawable.bge);
                break;
            case 5:
                holder.linearLayout.setBackgroundResource(R.drawable.bgf);
                break;
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return main_text.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvMainText;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMainText = (TextView) itemView.findViewById(R.id.text_main);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
