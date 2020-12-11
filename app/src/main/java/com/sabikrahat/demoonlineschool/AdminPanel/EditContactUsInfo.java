package com.sabikrahat.demoonlineschool.AdminPanel;

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
import com.sabikrahat.demoonlineschool.R;

import java.util.HashMap;

public class EditContactUsInfo extends AppCompatActivity {

    private EditText phone, facebook, website, gmail;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_us_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        phone = findViewById(R.id.contact_phone);
        facebook = findViewById(R.id.contact_facebook);
        website = findViewById(R.id.contact_website);
        gmail = findViewById(R.id.contact_gmail);
        save = findViewById(R.id.contact_save_changes);

        FirebaseDatabase.getInstance().getReference("ContactUsInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phone.setText(snapshot.child("phone").getValue().toString());
                facebook.setText(snapshot.child("facebook").getValue().toString());
                website.setText(snapshot.child("website").getValue().toString());
                gmail.setText(snapshot.child("gmail").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditContactUsInfo.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ContactUsInfo");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("phone", phone.getText().toString().trim());
                hashMap.put("facebook", facebook.getText().toString().trim());
                hashMap.put("website", website.getText().toString().trim());
                hashMap.put("gmail", gmail.getText().toString().trim());

                databaseReference.updateChildren(hashMap);
                Toast.makeText(EditContactUsInfo.this, "Data updated successfully.", Toast.LENGTH_SHORT).show();

                finish();
                Intent intent = new Intent(EditContactUsInfo.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }
}