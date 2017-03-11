package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.UserInfo;

import java.util.ArrayList;

/**
 * Created by QUANG on 3/12/2017.
 */

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private ArrayList<UserInfo> listUserInfo;
    View v;

    public RankAdapter(ArrayList<UserInfo> listUserInfo) {
        this.listUserInfo = listUserInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rank, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(listUserInfo.get(position).getName());
        holder.tvPoint.setText(String.valueOf(listUserInfo.get(position).getPoint() + " điểm"));
        String linkAvatar = "https://graph.facebook.com/" + listUserInfo.get(position).getIdFacebook() + "/picture?width=300";
        Glide.with(v.getContext())
                .load(linkAvatar)
                .into(holder.imvAvatar);
        if (position == 0)
            Glide.with(v.getContext())
                    .load(R.drawable.ic_nb1)
                    .into(holder.imvAchi);
        else if (position == 1)
            Glide.with(v.getContext())
                    .load(R.drawable.ic_nb2)
                    .into(holder.imvAchi);
        else if (position == 2)
            Glide.with(v.getContext())
                    .load(R.drawable.ic_nb3)
                    .into(holder.imvAchi);
        String s = (position + 1) + ". ";
        holder.tvRank.setText(s);
    }

    @Override
    public int getItemCount() {
        return listUserInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imvAvatar, imvAchi;
        private TextView tvName, tvPoint, tvRank;

        public ViewHolder(View itemView) {
            super(itemView);
            imvAvatar = (ImageView) itemView.findViewById(R.id.imageViewAvatar);
            imvAchi = (ImageView) itemView.findViewById(R.id.imageViewAchi);
            tvName = (TextView) itemView.findViewById(R.id.textViewName);
            tvPoint = (TextView) itemView.findViewById(R.id.textViewPoint);
            tvRank = (TextView) itemView.findViewById(R.id.textViewRank);
        }
    }
}
