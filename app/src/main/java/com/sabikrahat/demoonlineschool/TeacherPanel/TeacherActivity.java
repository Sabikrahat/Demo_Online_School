package com.sabikrahat.demoonlineschool.TeacherPanel;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sabikrahat.demoonlineschool.AdminPanel.AdminActivity;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.R;
import com.sabikrahat.demoonlineschool.StudentListActivity;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {

    private Button assignMark;

    private String selectedBatch="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        assignMark = findViewById(R.id.teacherPanelAssignMarkID);

        assignMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignMarks();
            }
        });
    }

    private void AssignMarks() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherActivity.this);
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
                Toast.makeText(TeacherActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Style and populate the spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(TeacherActivity.this, android.R.layout.simple_spinner_item, batchSuggestions);

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
                Toast.makeText(TeacherActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
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
                if (selectedBatch.equals("")) {
                    Toast.makeText(TeacherActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedBatch.equals("Choose a Batch")) {
                    Toast.makeText(TeacherActivity.this, "Please select a batch.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(TeacherActivity.this, BatchStudentsShowActivity.class);
                intent.putExtra("batch", selectedBatch);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}