package com.quang.tracnghiemtoan.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quang.tracnghiemtoan.R;
import com.quang.tracnghiemtoan.models.Message;

import java.text.DateFormat;
import java.util.ArrayList;


/**
 * Created by QUANG on 6/22/2016.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<Message> listMessages;
    private FirebaseUser user;
    private View v;
    private OnItemClickListener mItemClickListener;

    public MessageAdapter(ArrayList<Message> listMessages) {
        this.listMessages = listMessages;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_right, parent, false);
        else if (viewType == 1)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_left, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) {
            holder.tvUsername = (TextView) holder.itemView.findViewById(R.id.textViewUsername);
            holder.tvTimeSend = (TextView) holder.itemView.findViewById(R.id.textViewTimeSend);
            holder.imvAvatar = (ImageView) holder.itemView.findViewById(R.id.imageViewAvatar);
            holder.tvUsername.setText(listMessages.get(position).getUserName());
            DateFormat format = DateFormat.getDateTimeInstance();
            holder.tvTimeSend.setText(format.format(listMessages.get(position).getTimeSend()));
            String linkAvatar = "https://graph.facebook.com/" + listMessages.get(position).getFacebookId() + "/picture?width=300";
            Glide.with(v.getContext()).load(linkAvatar).into(holder.imvAvatar);
            holder.tvUsername.setOnClickListener(holder);
            holder.imvAvatar.setOnClickListener(holder);
        }
        holder.tvMessage.setText(listMessages.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        assert user != null;
        if (user.getUid().equals(listMessages.get(position).getFirebaseId())) {
            return 0;
        } else
            return 1;
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUsername, tvTimeSend, tvMessage;
        ImageView imvAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
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

}
