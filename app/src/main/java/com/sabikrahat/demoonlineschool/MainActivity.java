package com.sabikrahat.demoonlineschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.Authentication.LoginActivity;
import com.sabikrahat.demoonlineschool.Authentication.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    private Animation topAnimation, bottomAnimation;

    private ImageView logo;
    private TextView textView;
    private Button loginButton, registerButton;

    private static int SPLASH_SCREEN = 1000;

    private FirebaseUser mUser;

    public static final String channelID = "sabikrahat";
    public static final String channelName = "Demo Online School";
    public static final String channelDescription = "Udvabon Notification Pop-up.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for notification (additional settings for SDK > Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelDescription);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        //Animation initialize
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //widget initialize
        logo = findViewById(R.id.logo);
        textView = findViewById(R.id.textViewID);
        loginButton = findViewById(R.id.loginButtonID);
        registerButton = findViewById(R.id.registerButtonID);

        logo.setAnimation(topAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //check user logged in or not
                if (mUser != null) {
                    //check user is available or disable
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if ((snapshot.child("availability").getValue()).equals("true")) {
                                startActivity(new Intent(MainActivity.this, ApplicationActivity.class));
                                finish();
                            } else {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(MainActivity.this, "Session expired!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                textView.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                loginButton.setAnimation(bottomAnimation);
                registerButton.setVisibility(View.VISIBLE);
                registerButton.setAnimation(bottomAnimation);
            }
        }, SPLASH_SCREEN);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}