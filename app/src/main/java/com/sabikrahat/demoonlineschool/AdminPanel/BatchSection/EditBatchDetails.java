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
import com.sabikrahat.demoonlineschool.AdminPanel.AdminActivity;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.R;

import java.util.HashMap;

public class EditBatchDetails extends AppCompatActivity {

    private EditText classRoutine, otherLink, class_1_name, class_1_link,
            class_2_name, class_2_link, class_3_name, class_3_link, class_4_name, class_4_link,
            class_5_name, class_5_link, class_6_name, class_6_link, class_7_name, class_7_link,
            class_8_name, class_8_link, class_9_name, class_9_link, class_10_name, class_10_link;

    private Button save;

    private String BatchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_batch_details);

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

        classRoutine = findViewById(R.id.edit_class_routine);
        otherLink = findViewById(R.id.edit_other_website_link);
        class_1_name = findViewById(R.id.edit_class_1_name);
        class_1_link = findViewById(R.id.edit_class_1_link);
        class_2_name = findViewById(R.id.edit_class_2_name);
        class_2_link = findViewById(R.id.edit_class_2_link);
        class_3_name = findViewById(R.id.edit_class_3_name);
        class_3_link = findViewById(R.id.edit_class_3_link);
        class_4_name = findViewById(R.id.edit_class_4_name);
        class_4_link = findViewById(R.id.edit_class_4_link);
        class_5_name = findViewById(R.id.edit_class_5_name);
        class_5_link = findViewById(R.id.edit_class_5_link);
        class_6_name = findViewById(R.id.edit_class_6_name);
        class_6_link = findViewById(R.id.edit_class_6_link);
        class_7_name = findViewById(R.id.edit_class_7_name);
        class_7_link = findViewById(R.id.edit_class_7_link);
        class_8_name = findViewById(R.id.edit_class_8_name);
        class_8_link = findViewById(R.id.edit_class_8_link);
        class_9_name = findViewById(R.id.edit_class_9_name);
        class_9_link = findViewById(R.id.edit_class_9_link);
        class_10_name = findViewById(R.id.edit_class_10_name);
        class_10_link = findViewById(R.id.edit_class_10_link);
        save = findViewById(R.id.edit_savechanges);

        FirebaseDatabase.getInstance().getReference("BatchUrl").child(BatchName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BatchLink batchLink = snapshot.getValue(BatchLink.class);
                classRoutine.setText(batchLink.getClassRoutineDriveLink());
                otherLink.setText(batchLink.getOtherWebsiteLink());
                class_1_name.setText(batchLink.getClass_1_Name());
                class_1_link.setText(batchLink.getClass_1_Link());
                class_2_name.setText(batchLink.getClass_2_Name());
                class_2_link.setText(batchLink.getClass_2_Link());
                class_3_name.setText(batchLink.getClass_3_Name());
                class_3_link.setText(batchLink.getClass_3_Link());
                class_4_name.setText(batchLink.getClass_4_Name());
                class_4_link.setText(batchLink.getClass_4_Link());
                class_5_name.setText(batchLink.getClass_5_Name());
                class_5_link.setText(batchLink.getClass_5_Link());
                class_6_name.setText(batchLink.getClass_6_Name());
                class_6_link.setText(batchLink.getClass_6_Link());
                class_7_name.setText(batchLink.getClass_7_Name());
                class_7_link.setText(batchLink.getClass_7_Link());
                class_8_name.setText(batchLink.getClass_8_Name());
                class_8_link.setText(batchLink.getClass_8_Link());
                class_9_name.setText(batchLink.getClass_9_Name());
                class_9_link.setText(batchLink.getClass_9_Link());
                class_10_name.setText(batchLink.getClass_10_Name());
                class_10_link.setText(batchLink.getClass_10_Link());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditBatchDetails.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BatchUrl").child(BatchName);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("classRoutineDriveLink", classRoutine.getText().toString().trim());
                hashMap.put("otherWebsiteLink", otherLink.getText().toString().trim());
                hashMap.put("class_1_Name", class_1_name.getText().toString());
                hashMap.put("class_1_Link", class_1_link.getText().toString().trim());
                hashMap.put("class_2_Name", class_2_name.getText().toString());
                hashMap.put("class_2_Link", class_2_link.getText().toString().trim());
                hashMap.put("class_3_Name", class_3_name.getText().toString());
                hashMap.put("class_3_Link", class_3_link.getText().toString().trim());
                hashMap.put("class_4_Name", class_4_name.getText().toString());
                hashMap.put("class_4_Link", class_4_link.getText().toString().trim());
                hashMap.put("class_5_Name", class_5_name.getText().toString());
                hashMap.put("class_5_Link", class_5_link.getText().toString().trim());
                hashMap.put("class_6_Name", class_6_name.getText().toString());
                hashMap.put("class_6_Link", class_6_link.getText().toString().trim());
                hashMap.put("class_7_Name", class_7_name.getText().toString());
                hashMap.put("class_7_Link", class_7_link.getText().toString().trim());
                hashMap.put("class_8_Name", class_8_name.getText().toString());
                hashMap.put("class_8_Link", class_8_link.getText().toString().trim());
                hashMap.put("class_9_Name", class_9_name.getText().toString());
                hashMap.put("class_9_Link", class_9_link.getText().toString().trim());
                hashMap.put("class_10_Name", class_10_name.getText().toString());
                hashMap.put("class_10_Link", class_10_link.getText().toString().trim());

                databaseReference.updateChildren(hashMap);
                Toast.makeText(EditBatchDetails.this, "Batch details updated successfully.", Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(EditBatchDetails.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}