package com.sabikrahat.demoonlineschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.Adapter.StudentShowListAdapter;
import com.sabikrahat.demoonlineschool.Model.User;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private TextView headings;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private List<User> mUsersList;
    private StudentShowListAdapter studentShowListAdapter;

    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

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
        value = intent.getStringExtra("batch");

        headings = findViewById(R.id.headingTextView);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.showListRecyclerView);

        headings.setText("All Student's of " + value + " Batch.");

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mUsersList = new ArrayList<>();
        studentShowListAdapter = new StudentShowListAdapter(this, mUsersList);
        recyclerView.setAdapter(studentShowListAdapter);

        readUserList();
    }

    private void readUserList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if ((user.getBatch()).equalsIgnoreCase(value) && (user.getAvailability()).equalsIgnoreCase("true")) {
                        mUsersList.add(user);
                    }
                }
                studentShowListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentListActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}