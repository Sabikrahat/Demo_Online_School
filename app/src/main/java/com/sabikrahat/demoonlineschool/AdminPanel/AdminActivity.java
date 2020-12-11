package com.sabikrahat.demoonlineschool.AdminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.AdminPanel.BatchSection.AddNewBatch;
import com.sabikrahat.demoonlineschool.AdminPanel.BatchSection.EditBatchDetails;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.Model.RecordedVideo;
import com.sabikrahat.demoonlineschool.Model.User;
import com.sabikrahat.demoonlineschool.R;
import com.sabikrahat.demoonlineschool.ShowProfile;
import com.sabikrahat.demoonlineschool.StudentListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AdminActivity extends AppCompatActivity {

    private Button addAdmin, removeAdmin, addTeacher, addStudent, addBatch, editBatch, deleteBatch,
            disableUser, enableUser, searchUserByID, searchStudentsByBatch, searchAllUsers,
            addRecordedVideos, editContactUs, editRegistrationLink;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String selectedBatch = "", selectedBatch_2 = "", selectedBatch_3 = "", selectedBatch_4 = "", selectedBatch_5 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        addAdmin = findViewById(R.id.adminPanelAddAdminID);
        removeAdmin = findViewById(R.id.adminPanelRemoveAdminID);
        addTeacher = findViewById(R.id.adminPanelAddTeacherID);
        addStudent = findViewById(R.id.adminPanelAddStudent);
        addBatch = findViewById(R.id.adminPanelAddBatch);
        editBatch = findViewById(R.id.adminPanelEditBatch);
        deleteBatch = findViewById(R.id.adminPanelDeleteBatch);
        disableUser = findViewById(R.id.adminPanelDisableUser);
        enableUser = findViewById(R.id.adminPanelEnableUser);
        searchUserByID = findViewById(R.id.adminPanelSearchUserByID);
        searchStudentsByBatch = findViewById(R.id.adminPanelSearchStudentsByBatch);
        searchAllUsers = findViewById(R.id.adminPanelSearchAllUsers);
        addRecordedVideos = findViewById(R.id.adminPanelAddRecordedVideos);
        editContactUs = findViewById(R.id.adminPanelEditContactUS);
        editRegistrationLink = findViewById(R.id.adminPanelEditRegistrationLink);

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_custom_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupUserID);
                Button cancelButton = view.findViewById(R.id.popupCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupGotoButtonID);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                goToButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_id = user_id.getText().toString().trim();

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("status", "Admin");
                        hashMap.put("batch", "Admin");

                        databaseReference.updateChildren(hashMap);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("status", "Admin");
                                hashMap.put("batch", "Admin");

                                ref.updateChildren(hashMap);

                                alertDialog.dismiss();
                                Toast.makeText(AdminActivity.this, temp_id + " added to the admin list.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        removeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_custom_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupUserID);
                Button cancelButton = view.findViewById(R.id.popupCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupGotoButtonID);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                goToButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_id = user_id.getText().toString().trim();

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("status", "Guest");
                        hashMap.put("batch", "Guest");

                        databaseReference.updateChildren(hashMap);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("status", "Guest");
                                hashMap.put("batch", "Guest");

                                ref.updateChildren(hashMap);

                                alertDialog.dismiss();
                                Toast.makeText(AdminActivity.this, temp_id + " removed fro the admin list and become a guest user.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_custom_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupUserID);
                Button cancelButton = view.findViewById(R.id.popupCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupGotoButtonID);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                goToButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_id = user_id.getText().toString().trim();

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("status", "Teacher");
                        hashMap.put("batch", "Teacher");

                        databaseReference.updateChildren(hashMap);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("status", "Teacher");
                                hashMap.put("batch", "Teacher");

                                ref.updateChildren(hashMap);

                                alertDialog.dismiss();
                                Toast.makeText(AdminActivity.this, temp_id + " added to the teacher list.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_add_student_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupAddStudentidID);
                Spinner batchNameSpinner = view.findViewById(R.id.popupAddStudentBatchNameID);
                Button cancelButton = view.findViewById(R.id.popupAddStudentCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupAddStudentGotoButtonID);

                List<String> batchSuggestions = new ArrayList<>();
                batchSuggestions.add(0, "Choose Student's Batch");

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
                        Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                //Style and populate the spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

                //dropdown layout style
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                batchNameSpinner.setAdapter(dataAdapter);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                batchNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
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
                        String temp_id = user_id.getText().toString().trim();

                        if (selectedBatch.equals("")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (selectedBatch.equals("Choose Student's Batch")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("status", "Student");
                        hashMap.put("batch", selectedBatch);

                        databaseReference.updateChildren(hashMap);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);

                                DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                                HashMap<String, Object> hashMap_1 = new HashMap<>();
                                hashMap_1.put("status", "Student");
                                hashMap_1.put("batch", selectedBatch);

                                newRef.updateChildren(hashMap_1);
                                alertDialog.dismiss();
                                Toast.makeText(AdminActivity.this, temp_id + " added to the Student list. Batch: " + selectedBatch, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        addBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddNewBatch.class));
            }
        });

        editBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
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
                        Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                //Style and populate the spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

                //dropdown layout style
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                batchSelectorSpinner.setAdapter(dataAdapter);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                batchSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch_4 = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
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
                        if (selectedBatch_4.equals("")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (selectedBatch_4.equals("Choose a Batch")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(AdminActivity.this, EditBatchDetails.class);
                        intent.putExtra("BatchName", selectedBatch_4);
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        deleteBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
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
                        Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                //Style and populate the spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

                //dropdown layout style
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                batchSelectorSpinner.setAdapter(dataAdapter);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                batchSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch_5 = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
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
                        if (selectedBatch_5.equals("") || selectedBatch_5.equals("Choose a Batch")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FirebaseDatabase.getInstance().getReference("BatchUrl").child(selectedBatch_5).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseDatabase.getInstance().getReference("BatchVideos").child(selectedBatch_5).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AdminActivity.this, selectedBatch_5 + " deleted successfully.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        disableUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_custom_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupUserID);
                Button cancelButton = view.findViewById(R.id.popupCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupGotoButtonID);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                goToButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_id = user_id.getText().toString().trim();

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("availability", "false");

                        databaseReference.updateChildren(hashMap);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("availability", "false");

                                ref.updateChildren(hashMap);

                                alertDialog.dismiss();
                                Toast.makeText(AdminActivity.this, temp_id + " user is disable now.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        enableUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_custom_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupUserID);
                Button cancelButton = view.findViewById(R.id.popupCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupGotoButtonID);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                goToButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_id = user_id.getText().toString().trim();

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("availability", "true");

                        databaseReference.updateChildren(hashMap);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("availability", "true");

                                ref.updateChildren(hashMap);

                                alertDialog.dismiss();
                                Toast.makeText(AdminActivity.this, temp_id + " user is enable now.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        searchUserByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_custom_popup_layout, null);

                final EditText user_id = view.findViewById(R.id.popupUserID);
                Button cancelButton = view.findViewById(R.id.popupCancelButtonID);
                Button goToButton = view.findViewById(R.id.popupGotoButtonID);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                goToButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_id = user_id.getText().toString().trim();

                        if (temp_id.isEmpty()) {
                            user_id.setError("Enter the user id");
                            user_id.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rid").child(temp_id);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);

                                Intent intentShowProfile = new Intent(AdminActivity.this, ShowProfile.class);
                                intentShowProfile.putExtra("targetUID", user.getUid());
                                startActivity(intentShowProfile);

                                alertDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        searchStudentsByBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
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
                        Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                //Style and populate the spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

                //dropdown layout style
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                batchSelectorSpinner.setAdapter(dataAdapter);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                batchSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch_2 = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
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
                        if (selectedBatch_2.equals("")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (selectedBatch_2.equals("Choose a Batch")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(AdminActivity.this, StudentListActivity.class);
                        intent.putExtra("batch", selectedBatch_2);
                        startActivity(intent);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        searchAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, SeeAllUsersActivity.class);
                startActivity(intent);
            }
        });

        addRecordedVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_add_video_popup_layout, null);

                Spinner batchNameSpinner = view.findViewById(R.id.popupRecordedVideosBatchSelectionSpinnerID);
                EditText videoTitle = view.findViewById(R.id.popupRecordedVideoTitleID);
                EditText videoDate = view.findViewById(R.id.popupRecordedVideoDateID);
                EditText youtubeID = view.findViewById(R.id.popupRecordedVideoYoutubeLinkID);
                Button cancelButton = view.findViewById(R.id.popupRecordedVideoCancelButtonID);
                Button addVideo = view.findViewById(R.id.popupRecordedVideoAddButtonID);

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
                        Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                //Style and populate the spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(AdminActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

                //dropdown layout style
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                batchNameSpinner.setAdapter(dataAdapter);

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                batchNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedBatch_3 = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                addVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = videoTitle.getText().toString().trim();
                        String date = videoDate.getText().toString().trim();
                        String videoID = youtubeID.getText().toString().trim();

                        if (title.isEmpty()) {
                            videoTitle.setError("Enter a title");
                            videoTitle.requestFocus();
                            return;
                        }

                        if (date.isEmpty()) {
                            videoDate.setError("Enter the date");
                            videoDate.requestFocus();
                            return;
                        }

                        if (videoID.isEmpty()) {
                            youtubeID.setError("Enter the youtube video id");
                            youtubeID.requestFocus();
                            return;
                        }
                        if (selectedBatch_3.equals("")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (selectedBatch_3.equals("Choose a Batch")) {
                            Toast.makeText(AdminActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String temp_uid = UUID.randomUUID().toString();
                        RecordedVideo recordedVideo = new RecordedVideo(selectedBatch_3, temp_uid, title, date, videoID);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BatchVideos");
                        databaseReference.child(selectedBatch_3).child(temp_uid).setValue(recordedVideo);

                        alertDialog.dismiss();
                        Toast.makeText(AdminActivity.this, "A new video add to this " + selectedBatch_3, Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

        editContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, EditContactUsInfo.class));
            }
        });

        editRegistrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                View view = getLayoutInflater().inflate(R.layout.admin_panel_edit_registration_link, null);

                EditText formLink = view.findViewById(R.id.popupEditRegistrationLink);
                Button cancelButton = view.findViewById(R.id.popupEditRegistrationCancelButtonID);
                Button gotoButton = view.findViewById(R.id.popupEditRegistrationGoToID);

                FirebaseDatabase.getInstance().getReference("Registration").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        formLink.setText(snapshot.child("formLink").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(view);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                gotoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String form_link = formLink.getText().toString().trim();

                        if (form_link.isEmpty()) {
                            formLink.setError("Enter the link");
                            formLink.requestFocus();
                            return;
                        }

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registration");

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("formLink", form_link);

                        databaseReference.updateChildren(hashMap);

                        alertDialog.dismiss();
                        Toast.makeText(AdminActivity.this, "Registration link updated successfully.", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }
}