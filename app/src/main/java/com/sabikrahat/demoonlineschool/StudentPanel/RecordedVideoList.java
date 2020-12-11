package com.sabikrahat.demoonlineschool.StudentPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.Adapter.RecordedVideoAdapter;
import com.sabikrahat.demoonlineschool.Model.RecordedVideo;
import com.sabikrahat.demoonlineschool.R;

import java.util.ArrayList;
import java.util.List;

public class RecordedVideoList extends AppCompatActivity {

    private EditText searchVideosHint;
    private RecyclerView recyclerView;

    private RecordedVideoAdapter recordedVideoAdapter;
    private List<RecordedVideo> mRecordedVideos;

    private String BatchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_video_list);

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
        BatchName = intent.getStringExtra("BatchName");

        searchVideosHint = findViewById(R.id.studentPanelSearchVideosEditText);
        recyclerView = findViewById(R.id.studentPanelSearchVideosRecyclerView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mRecordedVideos = new ArrayList<>();

        readVideos();

        searchVideosHint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchVideos(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void searchVideos(String text) {
        Query query = FirebaseDatabase.getInstance().getReference("BatchVideos").child(BatchName).orderByChild("videoDate")
                .startAt(text)
                .endAt(text + "\uf0ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRecordedVideos.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecordedVideo recordedVideo = snapshot.getValue(RecordedVideo.class);
                    assert recordedVideo != null;
                    assert recordedVideo != null;
                    mRecordedVideos.add(recordedVideo);
                }
                recordedVideoAdapter = new RecordedVideoAdapter(getApplicationContext(), mRecordedVideos);
                recyclerView.setAdapter(recordedVideoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecordedVideoList.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void readVideos() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BatchVideos").child(BatchName);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRecordedVideos.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecordedVideo recordedVideo = snapshot.getValue(RecordedVideo.class);
                    mRecordedVideos.add(recordedVideo);
                }
                recordedVideoAdapter = new RecordedVideoAdapter(getApplicationContext(), mRecordedVideos);
                recyclerView.setAdapter(recordedVideoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecordedVideoList.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}