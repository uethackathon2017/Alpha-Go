package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.VideoTutorial;

import java.util.ArrayList;

/**
 * Created by QUANG on 1/12/2017.
 */

public class VideoTutorialAdapter extends RecyclerView.Adapter<VideoTutorialAdapter.ViewHolder> {

    private ArrayList<VideoTutorial> listVideoTutorial;
    private View v;
    private OnItemClickListener mItemClickListener;

    public VideoTutorialAdapter(ArrayList<VideoTutorial> listVideoTutorial) {
        this.listVideoTutorial = listVideoTutorial;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitleVideo, tvDescriptionVideo;
        ImageView imvThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitleVideo = (TextView) itemView.findViewById(R.id.textViewTitleVideo);
            tvDescriptionVideo = (TextView) itemView.findViewById(R.id.textViewDescriptionVideo);
            imvThumbnail = (ImageView) itemView.findViewById(R.id.imageViewThumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video_tutorial, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTitleVideo.setText(listVideoTutorial.get(position).getTitleVideo());

        if (listVideoTutorial.get(position).getDescriptionVideo().length() < 50)
            holder.tvDescriptionVideo.setText(listVideoTutorial.get(position).getDescriptionVideo());
        else {
            String smallDescription = listVideoTutorial.get(position).getDescriptionVideo().substring(0, 47) + "...";
            holder.tvDescriptionVideo.setText(smallDescription);
        }
        Glide.with(v.getContext())
                .load(listVideoTutorial.get(position).getLinkThumbnail())
                .into(holder.imvThumbnail);
    }

    @Override
    public int getItemCount() {
        return listVideoTutorial.size();
    }
}



