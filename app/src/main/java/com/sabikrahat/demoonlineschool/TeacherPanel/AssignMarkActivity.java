package com.sabikrahat.demoonlineschool.TeacherPanel;

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
import com.sabikrahat.demoonlineschool.Model.AssignMark;
import com.sabikrahat.demoonlineschool.R;

public class AssignMarkActivity extends AppCompatActivity {

    private EditText title_1, mark_1, title_2, mark_2, title_3, mark_3, title_4, mark_4, title_5, mark_5,
            title_6, mark_6, title_7, mark_7, title_8, mark_8, title_9, mark_9, title_10, mark_10, comment;
    private Button assign;

    private String Batch, Rid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_mark);

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
        Rid = intent.getStringExtra("targetRID");
        Batch = intent.getStringExtra("targetBatch");

        title_1 = findViewById(R.id.title_1);
        mark_1 = findViewById(R.id.mark_1);
        title_2 = findViewById(R.id.title_2);
        mark_2 = findViewById(R.id.mark_2);
        title_3 = findViewById(R.id.title_3);
        mark_3 = findViewById(R.id.mark_3);
        title_4 = findViewById(R.id.title_4);
        mark_4 = findViewById(R.id.mark_4);
        title_5 = findViewById(R.id.title_5);
        mark_5 = findViewById(R.id.mark_5);
        title_6 = findViewById(R.id.title_6);
        mark_6 = findViewById(R.id.mark_6);
        title_7 = findViewById(R.id.title_7);
        mark_7 = findViewById(R.id.mark_7);
        title_8 = findViewById(R.id.title_8);
        mark_8 = findViewById(R.id.mark_8);
        title_9 = findViewById(R.id.title_9);
        mark_9 = findViewById(R.id.mark_9);
        title_10 = findViewById(R.id.title_10);
        mark_10 = findViewById(R.id.mark_10);
        comment = findViewById(R.id.comment);
        assign = findViewById(R.id.assignMarksAssignButtonID);

        FirebaseDatabase.getInstance().getReference("StudentsMarks").child(Batch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(Rid)) {
                    AssignMark assignMark = snapshot.child(Rid).getValue(AssignMark.class);
                    title_1.setText(assignMark.getTitle_1());
                    mark_1.setText(assignMark.getMark_1());
                    title_2.setText(assignMark.getTitle_2());
                    mark_2.setText(assignMark.getMark_2());
                    title_3.setText(assignMark.getTitle_3());
                    mark_3.setText(assignMark.getMark_3());
                    title_4.setText(assignMark.getTitle_4());
                    mark_4.setText(assignMark.getMark_4());
                    title_5.setText(assignMark.getTitle_5());
                    mark_5.setText(assignMark.getMark_5());
                    title_6.setText(assignMark.getTitle_6());
                    mark_6.setText(assignMark.getMark_6());
                    title_7.setText(assignMark.getTitle_7());
                    mark_7.setText(assignMark.getMark_7());
                    title_8.setText(assignMark.getTitle_8());
                    mark_8.setText(assignMark.getMark_8());
                    title_9.setText(assignMark.getTitle_9());
                    mark_9.setText(assignMark.getMark_9());
                    title_10.setText(assignMark.getTitle_10());
                    mark_10.setText(assignMark.getMark_10());
                    comment.setText(assignMark.getComment());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AssignMarkActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title_1 = title_1.getText().toString();
                String Mark_1 = mark_1.getText().toString();
                String Comment = comment.getText().toString();

                if (Mark_1.isEmpty()) {
                    mark_1.setError("Enter the mark");
                    mark_1.requestFocus();
                    return;
                }

                if (Title_1.isEmpty()) {
                    title_1.setError("Enter the title");
                    title_1.requestFocus();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("StudentsMarks").child(Batch);
                AssignMark assignMark = new AssignMark(Title_1, Mark_1, title_2.getText().toString(), mark_2.getText().toString(),
                        title_3.getText().toString(), mark_3.getText().toString(), title_4.getText().toString(), mark_4.getText().toString(),
                        title_5.getText().toString(), mark_5.getText().toString(), title_6.getText().toString(), mark_6.getText().toString(),
                        title_7.getText().toString(), mark_7.getText().toString(), title_8.getText().toString(), mark_8.getText().toString(),
                        title_9.getText().toString(), mark_9.getText().toString(), title_10.getText().toString(), mark_10.getText().toString(), Comment);
                databaseReference.child(Rid).setValue(assignMark);
                Toast.makeText(AssignMarkActivity.this, "Mark assigned", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(AssignMarkActivity.this, BatchStudentsShowActivity.class).putExtra("batch", Batch));
            }
        });
    }
}