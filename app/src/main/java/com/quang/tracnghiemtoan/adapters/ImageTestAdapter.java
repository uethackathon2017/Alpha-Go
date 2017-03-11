package com.quang.tracnghiemtoan.adapters;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.ImageTest;

import java.util.ArrayList;

/**
 * Created by QUANG on 2/19/2017.
 */

public class ImageTestAdapter extends RecyclerView.Adapter<ImageTestAdapter.ViewHolder> {

    private ArrayList<ImageTest> listImageTest;
    private View v;
    private OnItemClickListener mItemClickListener;

    public ImageTestAdapter(ArrayList<ImageTest> listImageTest) {
        this.listImageTest = listImageTest;
    }

    @Override
    public ImageTestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_test, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageTestAdapter.ViewHolder holder, int position) {
        Glide.with(v.getContext())
                .load("http://graph.facebook.com/" + listImageTest.get(position).getId() + "/picture")
                .placeholder(new SizableColorDrawable(R.color.half_black, listImageTest.get(position).getWidth(), listImageTest.get(position).getHeight()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .dontAnimate()
                .thumbnail(0.1f)
                .into(holder.imv);
    }

    @Override
    public int getItemCount() {
        return listImageTest.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imv;

        public ViewHolder(View itemView) {
            super(itemView);
            imv = (ImageView) itemView.findViewById(R.id.imageView);
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

    public class SizableColorDrawable extends ColorDrawable {

        int mWidth = -1;

        int mHeight = -1;

        public SizableColorDrawable(int color, int width, int height) {
            super(color);

            mWidth = width;
            mHeight = height;
        }

        @Override
        public int getIntrinsicWidth() {
            return mWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mHeight;
        }
    }
}