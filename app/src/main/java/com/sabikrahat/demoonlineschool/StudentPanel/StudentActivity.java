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
import com.sabikrahat.demoonlineschool.Model.AssignMark;
import com.sabikrahat.demoonlineschool.Model.BatchLink;
import com.sabikrahat.demoonlineschool.Model.User;
import com.sabikrahat.demoonlineschool.R;
import com.sabikrahat.demoonlineschool.Webview.WebviewActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private TextView grettings, barMark, title_1, mark_1, title_2, mark_2, title_3, mark_3, title_4, mark_4, title_5, mark_5,
            title_6, mark_6, title_7, mark_7, title_8, mark_8, title_9, mark_9, title_10, mark_10, comment;
    private Button liveClass, routine, recorded, others;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private BatchLink batchLink;
    private String Batch, routineLink, otherLink = "", selectedBatch = "";

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
        barMark = findViewById(R.id.abc_xyz_mno);
        title_1 = findViewById(R.id.title_1);
        mark_1 = findViewById(R.id.mark_1);
        title_2 = findViewById(R.id.title_2);
        mark_2 = findViewById(R.id.mark_2);
        title_3 = findViewById(R.id.title_3);
        mark_3 = findViewById(R.id.mark_3);
        title_4 = findViewById(R.id.title_4);
        mark_4 = findViewById(R.id.mark_4);
        title_5 = findViewById(R.id.title_5);
        mark_5 = findViewById(R.id.mark_5);
        title_6 = findViewById(R.id.title_6);
        mark_6 = findViewById(R.id.mark_6);
        title_7 = findViewById(R.id.title_7);
        mark_7 = findViewById(R.id.mark_7);
        title_8 = findViewById(R.id.title_8);
        mark_8 = findViewById(R.id.mark_8);
        title_9 = findViewById(R.id.title_9);
        mark_9 = findViewById(R.id.mark_9);
        title_10 = findViewById(R.id.title_10);
        mark_10 = findViewById(R.id.mark_10);
        comment = findViewById(R.id.comment);

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

        FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (!(user.getStatus().equalsIgnoreCase("Student"))) {
                    barMark.setVisibility(View.GONE);
                    title_1.setVisibility(View.GONE);
                    mark_1.setVisibility(View.GONE);
                    title_2.setVisibility(View.GONE);
                    mark_2.setVisibility(View.GONE);
                    title_3.setVisibility(View.GONE);
                    mark_3.setVisibility(View.GONE);
                    title_4.setVisibility(View.GONE);
                    mark_4.setVisibility(View.GONE);
                    title_5.setVisibility(View.GONE);
                    mark_5.setVisibility(View.GONE);
                    title_6.setVisibility(View.GONE);
                    mark_6.setVisibility(View.GONE);
                    title_7.setVisibility(View.GONE);
                    mark_7.setVisibility(View.GONE);
                    title_8.setVisibility(View.GONE);
                    mark_8.setVisibility(View.GONE);
                    title_9.setVisibility(View.GONE);
                    mark_9.setVisibility(View.GONE);
                    title_10.setVisibility(View.GONE);
                    mark_10.setVisibility(View.GONE);
                    comment.setVisibility(View.GONE);
                } else {
                    FirebaseDatabase.getInstance().getReference("StudentsMarks").child(user.getBatch()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(user.getRid())) {
                                title_1.setText("Title: " + snapshot.child(user.getRid()).child("title_1").getValue().toString());
                                mark_1.setText("Mark: " + snapshot.child(user.getRid()).child("mark_1").getValue().toString());
                                title_2.setText("Title: " + snapshot.child(user.getRid()).child("title_2").getValue().toString());
                                mark_2.setText("Mark: " + snapshot.child(user.getRid()).child("mark_2").getValue().toString());
                                title_3.setText("Title: " + snapshot.child(user.getRid()).child("title_3").getValue().toString());
                                mark_3.setText("Mark: " + snapshot.child(user.getRid()).child("mark_3").getValue().toString());
                                title_4.setText("Title: " + snapshot.child(user.getRid()).child("title_4").getValue().toString());
                                mark_4.setText("Mark: " + snapshot.child(user.getRid()).child("mark_4").getValue().toString());
                                title_5.setText("Title: " + snapshot.child(user.getRid()).child("title_5").getValue().toString());
                                mark_5.setText("Mark: " + snapshot.child(user.getRid()).child("mark_5").getValue().toString());
                                title_6.setText("Title: " + snapshot.child(user.getRid()).child("title_6").getValue().toString());
                                mark_6.setText("Mark: " + snapshot.child(user.getRid()).child("mark_6").getValue().toString());
                                title_7.setText("Title: " + snapshot.child(user.getRid()).child("title_7").getValue().toString());
                                mark_7.setText("Mark: " + snapshot.child(user.getRid()).child("mark_7").getValue().toString());
                                title_8.setText("Title: " + snapshot.child(user.getRid()).child("title_8").getValue().toString());
                                mark_8.setText("Mark: " + snapshot.child(user.getRid()).child("mark_8").getValue().toString());
                                title_9.setText("Title: " + snapshot.child(user.getRid()).child("title_9").getValue().toString());
                                mark_9.setText("Mark: " + snapshot.child(user.getRid()).child("mark_9").getValue().toString());
                                title_10.setText("Title: " + snapshot.child(user.getRid()).child("title_10").getValue().toString());
                                mark_10.setText("Mark: " + snapshot.child(user.getRid()).child("mark_10").getValue().toString());
                                comment.setText("Comment: " + snapshot.child(user.getRid()).child("comment").getValue().toString());
                            } else {
                                title_1.setText(" N/A ");
                                mark_1.setText(" N/A ");
                                title_2.setText(" N/A ");
                                mark_2.setText(" N/A ");
                                title_3.setText(" N/A ");
                                mark_3.setText(" N/A ");
                                title_4.setText(" N/A ");
                                mark_4.setText(" N/A ");
                                title_5.setText(" N/A ");
                                mark_5.setText(" N/A ");
                                title_6.setText(" N/A ");
                                mark_6.setText(" N/A ");
                                title_7.setText(" N/A ");
                                mark_7.setText(" N/A ");
                                title_8.setText(" N/A ");
                                mark_8.setText(" N/A ");
                                title_9.setText(" N/A ");
                                mark_9.setText(" N/A ");
                                title_10.setText(" N/A ");
                                mark_10.setText(" N/A ");
                                comment.setText(" N/A ");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(StudentActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
                if (otherLink.equals("")) {
                    Toast.makeText(StudentActivity.this, "This option is not available yet for your batch.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(StudentActivity.this, WebviewActivity.class);
                    intent.putExtra("WebCode", otherLink);
                    startActivity(intent);
                }
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