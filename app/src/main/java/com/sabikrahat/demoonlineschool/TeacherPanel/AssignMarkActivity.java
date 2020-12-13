package com.sabikrahat.demoonlineschool.TeacherPanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sabikrahat.demoonlineschool.Model.AssignMark;
import com.sabikrahat.demoonlineschool.R;

public class AssignMarkActivity extends AppCompatActivity {

    private EditText title, mark, comment;
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

        title = findViewById(R.id.assignMarkTitleTextView);
        mark = findViewById(R.id.assignMarkTextView);
        comment = findViewById(R.id.assignMarkComment);
        assign = findViewById(R.id.assignMarksAssignButtonID);

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title = title.getText().toString();
                String Mark = mark.getText().toString();
                String Comment = comment.getText().toString();

                if (Mark.isEmpty()) {
                    mark.setError("Enter the mark");
                    mark.requestFocus();
                    return;
                }

                if (Title.isEmpty()) {
                    title.setError("Enter the title");
                    title.requestFocus();
                    return;
                }

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("StudentsMarks").child(Batch);
                AssignMark assignMark = new AssignMark(Title, Mark, Comment, true);
                databaseReference.child(Rid).setValue(assignMark);
                Toast.makeText(AssignMarkActivity.this, "Mark assigned", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(AssignMarkActivity.this, BatchStudentsShowActivity.class).putExtra("batch", Batch));
            }
        });


    }
}