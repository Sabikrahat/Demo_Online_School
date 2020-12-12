package com.sabikrahat.demoonlineschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.Adapter.PostAdapter;
import com.sabikrahat.demoonlineschool.AdminPanel.AdminActivity;
import com.sabikrahat.demoonlineschool.Authentication.ForgetPasswordActivity;
import com.sabikrahat.demoonlineschool.Authentication.LoginActivity;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.Model.Post;
import com.sabikrahat.demoonlineschool.Model.User;
import com.sabikrahat.demoonlineschool.StudentPanel.StudentActivity;
import com.sabikrahat.demoonlineschool.TeacherPanel.TeacherActivity;
import com.sabikrahat.demoonlineschool.Webview.WebviewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ApplicationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View mView;
    private ImageView drawerProfileImage;
    private TextView drawerTextView;

    private FirebaseAuth mAuth;

    private ScrollView scrollView;
    private EditText postWritten;
    private Button postButton;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PostAdapter postAdapter;
    private List<Post> postLists;

    private String selectedBatch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.application_nav_view_ID);

        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mView = navigationView.getHeaderView(0);
        drawerProfileImage = mView.findViewById(R.id.drawer_profile);
        drawerTextView = mView.findViewById(R.id.drawer_profile_name);

        scrollView = findViewById(R.id.ScrollID);
        postWritten = findViewById(R.id.applicationPostEditTextID);
        postButton = findViewById(R.id.applicationPostButtonID);
        recyclerView = findViewById(R.id.applicationRecyclerViewID);
        progressBar = findViewById(R.id.progress_circular);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (!(user.getStatus().equals("Admin"))) {
                    //hide or show
                    Menu menu = navigationView.getMenu();
                    menu.findItem(R.id.admin_panel).setVisible(false);
                    scrollView.setVisibility(View.GONE);
                    postWritten.setVisibility(View.GONE);
                    postButton.setVisibility(View.GONE);
                    if ((user.getStatus().equals("Student")) || ((user.getStatus().equals("Guest")))) {
                        //hide or show
                        menu.findItem(R.id.teacher_panel).setVisible(false);
                    }
                }
                try {
                    Glide.with(ApplicationActivity.this).load(user.getImageURL()).into(drawerProfileImage);
                } catch (Exception e) {
                    drawerProfileImage.setImageResource(R.drawable.profile);
                }
                drawerTextView.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postWritten.getText().toString().equals("")) {
                    Toast.makeText(ApplicationActivity.this, "You can't post an empty content.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    PostContent();
                }
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplicationActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        postAdapter = new PostAdapter(getApplicationContext(), postLists);
        recyclerView.setAdapter(postAdapter);

        readPosts();
    }

    private void PostContent() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy 'at' hh:mm a");
        String currentDateandTime = sdf.format(new Date());

        String postid = reference.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("postid", postid);
        hashMap.put("dateTime", currentDateandTime);
        hashMap.put("description", postWritten.getText().toString());
        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.child(postid).setValue(hashMap);

        progressDialog.dismiss();
        postWritten.setText("");
        Toast.makeText(ApplicationActivity.this, "Posted", Toast.LENGTH_LONG).show();
    }

    private void readPosts() {
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    postLists.add(post);
                }
                postAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                item.setCheckable(false);
                break;

            case R.id.student_panel:
                //TODO: Student Panel
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (((user.getStatus()).equals("Student")) && ((user.getAvailability().equals("true")))) {

                            FirebaseDatabase.getInstance().getReference("BatchUrl").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        BatchLink batchLink = snapshot.getValue(BatchLink.class);
                                        if ((batchLink.getBatchName()).equalsIgnoreCase(user.getBatch())) {
                                            Intent intent = new Intent(ApplicationActivity.this, StudentActivity.class);
                                            intent.putExtra("batch", user.getBatch());
                                            startActivity(intent);
                                            return;
                                        }
                                    }
                                    Toast.makeText(ApplicationActivity.this, "Your batch " + user.getBatch() + " is no longer available.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else if ((((user.getStatus()).equals("Admin")) || ((user.getStatus()).equals("Teacher"))) && (user.getAvailability().equals("true"))) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationActivity.this);
                            View view = getLayoutInflater().inflate(R.layout.batch_selection_popup_layout, null);

                            Spinner batchSelectorSpinner = view.findViewById(R.id.popupBatchSelectionSpinnerID);
                            Button cancelButton = view.findViewById(R.id.popupBatchSelectionCancelButtonID);
                            Button goToButton = view.findViewById(R.id.popupBatchSelectionGotoButtonID);

                            List<String> batchSuggestions = new ArrayList<>();
                            batchSuggestions.add(0, "Choose a Batch");

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BatchUrl");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        BatchLink batchLink = snapshot.getValue(BatchLink.class);
                                        batchSuggestions.add(batchLink.getBatchName());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                            //Style and populate the spinner
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter(ApplicationActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

                            //dropdown layout style
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            batchSelectorSpinner.setAdapter(dataAdapter);

                            builder.setView(view);

                            final AlertDialog alertDialog = builder.create();
                            alertDialog.setCanceledOnTouchOutside(false);

                            batchSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedBatch = parent.getItemAtPosition(position).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Toast.makeText(ApplicationActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                                }
                            });

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });

                            goToButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (selectedBatch.equals("") || selectedBatch.equals("Choose a Batch")) {
                                        Toast.makeText(ApplicationActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Intent intent = new Intent(ApplicationActivity.this, StudentActivity.class);
                                    intent.putExtra("batch", selectedBatch);
                                    startActivity(intent);
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        } else if (((user.getStatus()).equals("Guest")) && (user.getAvailability().equals("true"))) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationActivity.this);
                            View view = getLayoutInflater().inflate(R.layout.guest_register_layout, null);

                            Button registerButton = view.findViewById(R.id.popupRegisterNowButtonID);
                            builder.setView(view);

                            final AlertDialog alertDialog = builder.create();
                            alertDialog.setCanceledOnTouchOutside(true);

                            registerButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FirebaseDatabase.getInstance().getReference("Registration").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            startActivity(new Intent(ApplicationActivity.this, WebviewActivity.class).putExtra("WebCode", snapshot.child("formLink").getValue().toString()));
                                            alertDialog.dismiss();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            alertDialog.show();
                        } else {
                            Toast.makeText(ApplicationActivity.this, "You're disable.Please contact with the community.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                item.setCheckable(false);
                break;

            case R.id.teacher_panel:
                //TODO: Teacher Panel
                DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Users");
                dataRef.child(mAuth.getCurrentUser().getUid()).child("status").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.getValue()).equals("Admin") || (snapshot.getValue()).equals("Teacher")) {
                            startActivity(new Intent(ApplicationActivity.this, TeacherActivity.class));
                        } else {
                            Toast.makeText(ApplicationActivity.this, "Sorry! You're not an Teacher or an Admin.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                item.setCheckable(false);
                break;

            case R.id.admin_panel:
                //TODO: Admin Panel
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child(mAuth.getCurrentUser().getUid()).child("status").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.getValue()).equals("Admin")) {
                            startActivity(new Intent(ApplicationActivity.this, AdminActivity.class));
                        } else {
                            Toast.makeText(ApplicationActivity.this, "Sorry! You're not an admin.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ApplicationActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                item.setCheckable(false);
                break;

            case R.id.my_profile:
                //TODO: My Profile
                Intent intentShowProfile = new Intent(ApplicationActivity.this, ShowProfile.class);
                intentShowProfile.putExtra("targetUID", mAuth.getCurrentUser().getUid());
                startActivity(intentShowProfile);

                item.setCheckable(false);
                break;

            case R.id.forgetPassword:
                //TODO: Reset Password
                finish();
                Intent intent_reset = new Intent(ApplicationActivity.this, ForgetPasswordActivity.class);
                intent_reset.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_reset);

                item.setCheckable(false);
                break;

            case R.id.logout:
                //TODO: User logout
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent_logout = new Intent(ApplicationActivity.this, LoginActivity.class);
                intent_logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_logout);

                item.setCheckable(false);
                break;

            case R.id.contact:
                //TODO: Contact Us
                startActivity(new Intent(ApplicationActivity.this, ContactUsActivity.class));

                item.setCheckable(false);
                break;

            case R.id.feedback:
                //TODO: Share
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                String subject = "আবিষ্কার mobile app";
                String body = "";
                intent.putExtra(intent.EXTRA_SUBJECT, subject);
                intent.putExtra(intent.EXTRA_TEXT, body);
                intent.putExtra(intent.EXTRA_EMAIL, new String[]{"sabikrahat72428@gmail.com", "abiskar.edu@gmail.com"});
                startActivity(Intent.createChooser(intent, "আবিষ্কার"));

                item.setCheckable(false);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    }).show();
        }
    }
}