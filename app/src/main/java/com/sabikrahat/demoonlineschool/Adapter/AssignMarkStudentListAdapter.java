package com.sabikrahat.demoonlineschool.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sabikrahat.demoonlineschool.Model.User;
import com.sabikrahat.demoonlineschool.R;
import com.sabikrahat.demoonlineschool.ShowProfile;
import com.sabikrahat.demoonlineschool.TeacherPanel.AssignMarkActivity;

import java.util.List;

public class AssignMarkStudentListAdapter extends RecyclerView.Adapter<AssignMarkStudentListAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public AssignMarkStudentListAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public AssignMarkStudentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.showlist_item, parent, false);
        return new AssignMarkStudentListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignMarkStudentListAdapter.ViewHolder holder, int position) {
        final User user = mUsers.get(position);

        holder.name.setText(user.getName());
        holder.rid.setText("#" + user.getRid());
        try {
            Glide.with(mContext).load(user.getImageURL()).into(holder.image_profile);
        } catch (Exception e) {
            holder.image_profile.setImageResource(R.drawable.profile);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AssignMarkActivity.class);
                intent.putExtra("targetRID", user.getRid());
                intent.putExtra("targetBatch", user.getBatch());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_profile;
        private TextView name, rid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            name = itemView.findViewById(R.id.username_item);
            rid = itemView.findViewById(R.id.rid_number);
        }
    }
}
