package com.sabikrahat.demoonlineschool.AdminPanel.BatchSection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.ApplicationActivity;
import com.sabikrahat.demoonlineschool.Model.RecordedVideo;
import com.sabikrahat.demoonlineschool.R;

import java.util.HashMap;

public class EditBatchVideoDetails extends AppCompatActivity {

    private EditText title, date, youtube_id;
    private Button save;

    private String vUid, batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_batch_video_details);

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

        title = findViewById(R.id.video_title);
        date = findViewById(R.id.video_date);
        youtube_id = findViewById(R.id.video_id);
        save = findViewById(R.id.save_chng);

        FirebaseDatabase.getInstance().getReference("BatchVideos").child(batch).child(vUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RecordedVideo recordedVideo = snapshot.getValue(RecordedVideo.class);

                title.setText(recordedVideo.getVideoTitle());
                date.setText(recordedVideo.getVideoDate());
                youtube_id.setText(recordedVideo.getVideoID());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditBatchVideoDetails.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BatchVideos").child(batch).child(vUid);

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("videoTitle", title.getText().toString());
                hashMap.put("videoDate", date.getText().toString());
                hashMap.put("videoID", youtube_id.getText().toString().trim());

                databaseReference.updateChildren(hashMap);
                Toast.makeText(EditBatchVideoDetails.this, "Batch Video details updated successfully.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditBatchVideoDetails.this, ApplicationActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}