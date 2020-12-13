package com.sabikrahat.demoonlineschool.AdminPanel.BatchSection;

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
import com.sabikrahat.demoonlineschool.AdminPanel.AdminActivity;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.R;

public class AddNewBatch extends AppCompatActivity {

    private EditText batchName, classRoutine, otherLink, class_1_name, class_1_link,
            class_2_name, class_2_link, class_3_name, class_3_link, class_4_name, class_4_link,
            class_5_name, class_5_link, class_6_name, class_6_link, class_7_name, class_7_link,
            class_8_name, class_8_link, class_9_name, class_9_link, class_10_name, class_10_link;

    private Button cancel, createBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_batch);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        batchName = findViewById(R.id.batch_name);
        classRoutine = findViewById(R.id.class_routine);
        otherLink = findViewById(R.id.other_website_link);
        class_1_name = findViewById(R.id.class_1_name);
        class_1_link = findViewById(R.id.class_1_link);
        class_2_name = findViewById(R.id.class_2_name);
        class_2_link = findViewById(R.id.class_2_link);
        class_3_name = findViewById(R.id.class_3_name);
        class_3_link = findViewById(R.id.class_3_link);
        class_4_name = findViewById(R.id.class_4_name);
        class_4_link = findViewById(R.id.class_4_link);
        class_5_name = findViewById(R.id.class_5_name);
        class_5_link = findViewById(R.id.class_5_link);
        class_6_name = findViewById(R.id.class_6_name);
        class_6_link = findViewById(R.id.class_6_link);
        class_7_name = findViewById(R.id.class_7_name);
        class_7_link = findViewById(R.id.class_7_link);
        class_8_name = findViewById(R.id.class_8_name);
        class_8_link = findViewById(R.id.class_8_link);
        class_9_name = findViewById(R.id.class_9_name);
        class_9_link = findViewById(R.id.class_9_link);
        class_10_name = findViewById(R.id.class_10_name);
        class_10_link = findViewById(R.id.class_10_link);
        cancel = findViewById(R.id.addBatchCancelButtonID);
        createBatch = findViewById(R.id.addBatchCreateBatchID);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        createBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BatchName = batchName.getText().toString();
                String ClassRoutine = classRoutine.getText().toString().trim();
                String OtherLink = otherLink.getText().toString().trim();
                String Class_1_Name = class_1_name.getText().toString();
                String Class_1_Link = class_1_link.getText().toString().trim();

                if (BatchName.isEmpty()) {
                    batchName.setError("Enter the batch name");
                    batchName.requestFocus();
                    return;
                }
                if (ClassRoutine.isEmpty()) {
                    classRoutine.setError("Enter the class routine url");
                    classRoutine.requestFocus();
                    return;
                }

                if (Class_1_Name.isEmpty()) {
                    class_1_name.setError("Enter a class name");
                    class_1_name.requestFocus();
                    return;
                }

                if (Class_1_Link.isEmpty()) {
                    class_1_link.setError("Enter the class join link");
                    class_1_link.requestFocus();
                    return;
                }

                BatchLink batchLink = new BatchLink(BatchName, ClassRoutine, OtherLink, Class_1_Name, Class_1_Link,
                        class_2_name.getText().toString(), class_2_link.getText().toString().trim(), class_3_name.getText().toString(), class_3_link.getText().toString().trim(),
                        class_4_name.getText().toString(), class_4_link.getText().toString().trim(), class_5_name.getText().toString(), class_5_link.getText().toString().trim(),
                        class_6_name.getText().toString(), class_6_link.getText().toString().trim(), class_7_name.getText().toString(), class_7_link.getText().toString().trim(),
                        class_8_name.getText().toString(), class_8_link.getText().toString().trim(), class_9_name.getText().toString(), class_9_link.getText().toString().trim(),
                        class_10_name.getText().toString(), class_10_link.getText().toString().trim());

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BatchUrl");
                databaseReference.child(BatchName).setValue(batchLink);
                Toast.makeText(AddNewBatch.this, BatchName + " new batch created.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNewBatch.this, AdminActivity.class));
                finish();
            }
        });
    }
}