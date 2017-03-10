package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.UserOnline;

import java.util.ArrayList;

/**
 * Created by QUANG on 1/15/2017.
 */

public class UserOnlineAdapter extends RecyclerView.Adapter<UserOnlineAdapter.ViewHolder> {

    private ArrayList<UserOnline> listUserOnline;
    private View v;

    public UserOnlineAdapter(ArrayList<UserOnline> listUserOnline) {
        this.listUserOnline = listUserOnline;
    }

    @Override
    public UserOnlineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_online, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserOnlineAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(listUserOnline.get(position).getName());
        Glide.with(v.getContext()).load("https://graph.facebook.com/" + listUserOnline.get(position).getFacebookId() +
                "/picture?width=300").into(holder.imvAvatar);
    }

    @Override
    public int getItemCount() {
        return listUserOnline.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private ImageView imvAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.textViewName);
            imvAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
        }
    }
}
