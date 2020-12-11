package com.sabikrahat.demoonlineschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.Webview.WebviewActivity;

public class ContactUsActivity extends AppCompatActivity {

    private ImageButton phone, facebook, websiteee, gmail;

    private String Phone, Facebook, Website, Gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        phone = findViewById(R.id.contactUsActivityPhoneImageButtonID);
        facebook = findViewById(R.id.contactUsActivityFacebookImageButtonID);
        websiteee = findViewById(R.id.contactUsActivityPersonalWebsiteImageButtonID);
        gmail = findViewById(R.id.contactUsctivityGmailImageButtonID);

        FirebaseDatabase.getInstance().getReference("ContactUsInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Phone = snapshot.child("phone").getValue().toString();
                Facebook = snapshot.child("facebook").getValue().toString();
                Website = snapshot.child("website").getValue().toString();
                Gmail = snapshot.child("gmail").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ContactUsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+88" + Phone));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUsActivity.this, WebviewActivity.class).putExtra("WebCode", Facebook));
            }
        });

        websiteee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUsActivity.this, WebviewActivity.class).putExtra("WebCode", Website));

            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                String subject = "Udvabon Contact Forum";
                String body = "";
                intent.putExtra(intent.EXTRA_SUBJECT, subject);
                intent.putExtra(intent.EXTRA_TEXT, body);
                intent.putExtra(intent.EXTRA_EMAIL, new String[]{Gmail});
                startActivity(Intent.createChooser(intent, " Queries "));
            }
        });
    }
}