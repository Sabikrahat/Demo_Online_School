package com.sabikrahat.demoonlineschool.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabikrahat.demoonlineschool.Model.RecordedVideo;
import com.sabikrahat.demoonlineschool.R;
import com.sabikrahat.demoonlineschool.StudentPanel.YoutubeVideoShow;

import java.util.List;

public class RecordedVideoAdapter extends RecyclerView.Adapter<RecordedVideoAdapter.ViewHolder> {

    private Context mContext;
    private List<RecordedVideo> mRecordedVideos;

    public RecordedVideoAdapter(Context mContext, List<RecordedVideo> mRecordedVideos) {
        this.mContext = mContext;
        this.mRecordedVideos = mRecordedVideos;
    }

    @NonNull
    @Override
    public RecordedVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.record_item, parent, false);
        return new RecordedVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordedVideoAdapter.ViewHolder holder, int position) {
        RecordedVideo recordedVideo = mRecordedVideos.get(position);

        holder.title.setText(recordedVideo.getVideoTitle());
        holder.videoDate.setText("#" + recordedVideo.getVideoDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShowVideo = new Intent(mContext, YoutubeVideoShow.class);
                intentShowVideo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentShowVideo.putExtra("vUid", recordedVideo.getvUid());
                intentShowVideo.putExtra("batch", recordedVideo.getBatchName());
                intentShowVideo.putExtra("title", recordedVideo.getVideoTitle());
                intentShowVideo.putExtra("date", recordedVideo.getVideoDate());
                intentShowVideo.putExtra("videoID", recordedVideo.getVideoID());
                mContext.startActivity(intentShowVideo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecordedVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, videoDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.video_title_id);
            videoDate = itemView.findViewById(R.id.video_date);
        }
    }
}
