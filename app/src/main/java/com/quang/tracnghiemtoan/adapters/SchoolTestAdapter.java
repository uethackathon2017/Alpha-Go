package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.SchoolTest;

import java.util.ArrayList;

/**
 * Created by QUANG on 3/10/2017.
 */

public class SchoolTestAdapter extends RecyclerView.Adapter<SchoolTestAdapter.Holder> {

    private ArrayList<SchoolTest> listSchoolTest;
    private View v;
    private OnItemClickListener mItemClickListener;

    public SchoolTestAdapter(ArrayList<SchoolTest> listSchoolTest) {
        this.listSchoolTest = listSchoolTest;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_school_test, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tvTitle.setText(listSchoolTest.get(position).getTitle());
        holder.tvDate.setText(listSchoolTest.get(position).getDateTest());
        Glide.with(v.getContext())
                .load(listSchoolTest.get(position).getLinkImage())
                .into(holder.imvIcon);
    }

    @Override
    public int getItemCount() {
        return listSchoolTest.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imvIcon;
        private TextView tvTitle, tvDate;

        public Holder(View itemView) {
            super(itemView);
            imvIcon = (ImageView) itemView.findViewById(R.id.imageViewIcon);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            tvDate = (TextView) itemView.findViewById(R.id.textViewDate);
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
