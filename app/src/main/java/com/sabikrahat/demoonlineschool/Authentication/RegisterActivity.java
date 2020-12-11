package com.sabikrahat.demoonlineschool.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sabikrahat.demoonlineschool.MainActivity;
import com.sabikrahat.demoonlineschool.Model.User;
import com.sabikrahat.demoonlineschool.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText Name, Phone, Email, Password, ConfirmPassword;
    private TextView alreadyRegistered;
    private Button cancel, signup;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.registerFullNameID);
        Phone = findViewById(R.id.registerPhoneID);
        Email = findViewById(R.id.registerEmailID);
        Password = findViewById(R.id.registerPasswordID);
        ConfirmPassword = findViewById(R.id.registerConfirmPasswordID);
        alreadyRegistered = findViewById(R.id.registerAlreadyAccountTextID);
        cancel = findViewById(R.id.registerCancelID);
        signup = findViewById(R.id.registerSignupID);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister();
            }
        });
    }

    private void userRegister() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString();
        String confirmPassword = ConfirmPassword.getText().toString();
        String name = Name.getText().toString();
        String phone = Phone.getText().toString();

        //checking the validity of the email
        if (email.isEmpty()) {
            Email.setError("Enter an email address");
            Email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

        if (confirmPassword.isEmpty()) {
            ConfirmPassword.setError("Enter a password");
            ConfirmPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            Password.setError("Minimum length of a password should be 6");
            Password.requestFocus();
            ConfirmPassword.setError("Minimum length of a password should be 6");
            ConfirmPassword.requestFocus();
            Password.setText("");
            ConfirmPassword.setText("");
            return;
        }

        if (confirmPassword.length() < 6) {
            Password.setError("Minimum length of a password should be 6");
            Password.requestFocus();
            ConfirmPassword.setError("Minimum length of a password should be 6");
            ConfirmPassword.requestFocus();
            Password.setText("");
            ConfirmPassword.setText("");
            return;
        }

        if (!password.equals(confirmPassword)) {
            Password.setError("Password doesn't match");
            Password.requestFocus();
            ConfirmPassword.setError("Password doesn't match");
            ConfirmPassword.requestFocus();
            Password.setText("");
            ConfirmPassword.setText("");
            return;
        }

        //checking the validity of the full name
        if (name.isEmpty()) {
            Name.setError("Enter your first name");
            Name.requestFocus();
            return;
        }

        //checking the validity of the phone number
        if (phone.isEmpty()) {
            Phone.setError("Enter your last name");
            Phone.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            Phone.setError("Enter a valid phone number");
            Phone.requestFocus();
            return;
        }

        if (phone.length() < 9 && phone.length() > 12) {
            Phone.setError("Enter a valid phone number");
            Phone.requestFocus();
            return;
        }
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Creating your Account. Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information

                    String imageUrl = "https://firebasestorage.googleapis.com/v0/b/abiskar-sabikrahat-72428.appspot.com/o/default_profile_pic.png?alt=media&token=0895a23f-b5b2-4dee-ade0-32d560815234";

                    Random rnd = new Random();
                    int tempId = rnd.nextInt(999999);
                    String rid = String.valueOf(tempId);

                    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy 'at' hh:mm a");
                    String currentDateandTime = sdf.format(new Date());

                    User user = new User(mAuth.getCurrentUser().getUid(), rid, currentDateandTime, "Guest", email, name, phone, imageUrl, "Guest", "true");

                    //Users information by UID
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(user);

                    //Users information by UID
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Rid");
                    ref.child(rid).setValue(user);

                    FirebaseUser mUser = mAuth.getCurrentUser();

                    //set display name
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    mUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    Email.setText("");
                    Password.setText("");
                    ConfirmPassword.setText("");
                    Name.setText("");
                    Phone.setText("");

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Register completed.", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "User is already Registered.", Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}