package com.sabikrahat.demoonlineschool.StudentPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sabikrahat.demoonlineschool.AdminPanel.BatchSection.EditBatchVideoDetails;
import com.sabikrahat.demoonlineschool.ApplicationActivity;
import com.sabikrahat.demoonlineschool.R;

public class YoutubeVideoShow extends AppCompatActivity {

    private String vUid, batch, title, date, videoId, Status;
    private TextView details, textView;
    private Button edit_buton, delete_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_show);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        vUid = intent.getStringExtra("vUid");
        batch = intent.getStringExtra("batch");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        videoId = intent.getStringExtra("videoID");

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        details = findViewById(R.id.textDetails);
        edit_buton = findViewById(R.id.edit_details);
        delete_video = findViewById(R.id.delete_video);
        textView = findViewById(R.id.textViewID);

        details.setText(title + "   #" + batch + " (" + date + ")");

        try {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Url expired! Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ((snapshot.getValue().toString()).equalsIgnoreCase("Admin")) {
                    edit_buton.setVisibility(View.VISIBLE);
                    delete_video.setVisibility(View.VISIBLE);
                } else {
                    edit_buton.setVisibility(View.GONE);
                    delete_video.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(YoutubeVideoShow.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        edit_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditVideo = new Intent(YoutubeVideoShow.this, EditBatchVideoDetails.class);
                intentEditVideo.putExtra("vUid", vUid);
                intentEditVideo.putExtra("batch", batch);
                startActivity(intentEditVideo);
                finish();
            }
        });

        delete_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(YoutubeVideoShow.this);
                builder.setMessage("Are you sure you want to Delete this Video?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(new Intent(YoutubeVideoShow.this, ApplicationActivity.class));
                                FirebaseDatabase.getInstance().getReference("BatchVideos").child(batch).child(vUid).removeValue();
                                Toast.makeText(YoutubeVideoShow.this, "Video Deleted!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).show();
            }
        });
    }
}