package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quang.tracnghiemtoan.R;

/**
 * Created by PhungVanQuang on 3/6/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private String[] main_text;
    private String[] main_tuc_ngu_text;
    private View v;
    private OnItemClickListener mItemClickListener;

    public MainAdapter(String[] main_text, String[] main_tuc_ngu_text) {
        this.main_text = main_text;
        this.main_tuc_ngu_text = main_tuc_ngu_text;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvMainText.setText(main_text[position]);
        holder.tvTucNgu.setText(main_tuc_ngu_text[position]);
//        int[] androidColors = v.getResources().getIntArray(R.array.androidcolors);
//        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        holder.tvMainText.setBackgroundColor(randomAndroidColor);
//        holder.tvTucNgu.setBackgroundColor(randomAndroidColor);
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

        private TextView tvMainText, tvTucNgu;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMainText = (TextView) itemView.findViewById(R.id.text_main);
            tvTucNgu = (TextView) itemView.findViewById(R.id.tuc_ngu_text);
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
