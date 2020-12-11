package com.sabikrahat.demoonlineschool.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.ApplicationActivity;
import com.sabikrahat.demoonlineschool.MainActivity;
import com.sabikrahat.demoonlineschool.R;

public class LoginActivity extends AppCompatActivity {

    private EditText Email, Password;
    private Button Login, ResendEmail;
    private TextView wantRegister, forgetPass;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Email = findViewById(R.id.loginEmailID);
        Password = findViewById(R.id.loginPasswordID);
        Login = findViewById(R.id.loginActivityLoginButtonID);
        wantRegister = findViewById(R.id.loginActivityRegisterTextID);
        forgetPass = findViewById(R.id.loginActivityResetPasswordID);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        wantRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void userLogin() {

        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString();

        //checking the validity of the email
        if (email.isEmpty()) {
            Email.setError("Enter an email address");
            Email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Enter a valid email address");
            Email.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
            Password.setError("Enter a password");
            Password.requestFocus();
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser mUser = mAuth.getCurrentUser();
                    // Sign in success, update UI with the signed-in user's information
                    //Cheak weather user is available
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if ((snapshot.child("availability").getValue()).equals("true")) {
                                progressDialog.dismiss();
                                finish();
                                Intent intent = new Intent(LoginActivity.this, ApplicationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //new activity's top will clear
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                Email.setText("");
                                Password.setText("");
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                Toast.makeText(LoginActivity.this, "Your id is disable. Please contact with admin panel.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}