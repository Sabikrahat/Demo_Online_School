package com.sabikrahat.demoonlineschool.StudentPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.R;
import com.sabikrahat.demoonlineschool.Webview.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private TextView grettings;
    private Button liveClass, routine, recorded, others;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private BatchLink batchLink;
    private String Batch, routineLink, otherLink, selectedBatch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        grettings = findViewById(R.id.greetings);
        liveClass = findViewById(R.id.liveClass);
        routine = findViewById(R.id.classRoutine);
        recorded = findViewById(R.id.recordedVideos);
        others = findViewById(R.id.others);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        grettings.setText("Welcome! " + mUser.getDisplayName());

        Intent intent_Extra = getIntent();
        Batch = intent_Extra.getStringExtra("batch");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BatchUrl");
        ref.child(Batch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BatchLink batchLink_private = snapshot.getValue(BatchLink.class);
                routineLink = batchLink_private.getClassRoutineDriveLink();
                otherLink = batchLink_private.getOtherWebsiteLink();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        liveClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveClassFunction();
            }
        });

        routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, WebviewActivity.class);
                intent.putExtra("WebCode", routineLink);
                startActivity(intent);
            }
        });

        recorded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, RecordedVideoList.class).putExtra("BatchName", Batch));
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, WebviewActivity.class);
                intent.putExtra("WebCode", otherLink);
                startActivity(intent);
            }
        });
    }

    private void LiveClassFunction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        View view = getLayoutInflater().inflate(R.layout.join_class_link_selection_layout, null);

        Spinner batchSelectorSpinner = view.findViewById(R.id.popupSubjectSelectionSpinnerID);
        Button cancelButton = view.findViewById(R.id.popupSubjectSelectionCancelButtonID);
        Button goToButton = view.findViewById(R.id.popupSubjectSelectionGotoButtonID);

        List<String> batchSuggestions = new ArrayList<>();
        batchSuggestions.add(0, "Choose a class");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BatchUrl").child(Batch);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                batchLink = snapshot.getValue(BatchLink.class);

                if (!(batchLink.getClass_1_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_1_Name());
                }
                if (!(batchLink.getClass_2_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_2_Name());
                }
                if (!(batchLink.getClass_3_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_3_Name());
                }
                if (!(batchLink.getClass_4_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_4_Name());
                }
                if (!(batchLink.getClass_5_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_5_Name());
                }
                if (!(batchLink.getClass_6_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_6_Name());
                }
                if (!(batchLink.getClass_7_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_7_Name());
                }
                if (!(batchLink.getClass_8_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_8_Name());
                }
                if (!(batchLink.getClass_9_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_9_Name());
                }
                if (!(batchLink.getClass_10_Name()).equals("")) {
                    batchSuggestions.add(batchLink.getClass_10_Name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Style and populate the spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(StudentActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

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
                Toast.makeText(StudentActivity.this, "Please select a class.", Toast.LENGTH_SHORT).show();
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
                if (selectedBatch.equals("") || selectedBatch.equals("Choose a class")) {
                    Toast.makeText(StudentActivity.this, "Please select a class.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((batchLink.getClass_1_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_1_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_2_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_2_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_3_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_3_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_4_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_4_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_5_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_5_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_6_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_6_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_7_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_7_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_8_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_8_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_9_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_9_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
                if ((batchLink.getClass_10_Name()).equals(selectedBatch)) {
                    Uri classMeetLink = Uri.parse(batchLink.getClass_10_Link());
                    Intent intent = new Intent(Intent.ACTION_VIEW, classMeetLink);
                    startActivity(intent);
                    alertDialog.dismiss();
                    return;
                }
            }
        });
        alertDialog.show();
    }
}