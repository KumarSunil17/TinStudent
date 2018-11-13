package com.kumarsunil17.tinstudent;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Feedback_activity extends AppCompatActivity {

    private Spinner subject;
    private Button submitBtn;
    private RatingBar ratingBar;
    private EditText details;
    private DatabaseReference feedbackRef;
    private String subjectText, detailsText, ratingText;
    private ProgressDialog pg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_activity);

        subject = findViewById(R.id.feedback_subject);
        submitBtn = findViewById(R.id.btn_submit);
        ratingBar = findViewById(R.id.feedback_rating);
        details = findViewById(R.id.feedback_details);
        feedbackRef = FirebaseDatabase.getInstance().getReference().child("feedback");
        mAuth = FirebaseAuth.getInstance();

        pg = new ProgressDialog(this);
        pg.setMessage("Please wait while we are submitting your response!");
        pg.setTitle("Please wait");
        pg.setCancelable(false);
        pg.setCanceledOnTouchOutside(false);

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        details.setEnabled(false);
                        break;
                    case 1:
                        subjectText = "Attendance";
                        details.setEnabled(true);
                        break;
                    case 2:
                        subjectText = "Notice";
                        details.setEnabled(true);
                        break;
                    case 3:
                        subjectText = "Sign in/Sign up";
                        details.setEnabled(true);
                        break;
                    case 4:
                        subjectText = "Syllabus";
                        details.setEnabled(true);
                        break;
                    case 5:
                        subjectText = "About our app";
                        details.setEnabled(true);
                        break;
                    case 6:
                        subjectText = "Faculties";
                        details.setEnabled(true);
                        break;
                    default:
                        details.setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText = String.valueOf(rating);
            }
        });

        details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                detailsText = details.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(detailsText)){
                    Toast.makeText(Feedback_activity.this, detailsText, Toast.LENGTH_SHORT).show();
                    details.setError("Field cannot be empty!");
                }else if(TextUtils.isEmpty(ratingText)){
                    Toast.makeText(Feedback_activity.this, "Please give us star", Toast.LENGTH_SHORT).show();
                }else{
                    pg.show();
                    Date d = new Date();
                    SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");
                    String time = s.format(d);
                    Map<String,Object> m = new HashMap<>();
                    m.put("details",detailsText);
                    m.put("date",time);
                    m.put("subject",subjectText);
                    m.put("rate",ratingText);

                    feedbackRef.child(mAuth.getCurrentUser().getUid()).updateChildren(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pg.dismiss();
                                Toast.makeText(Feedback_activity.this, "Feedback submitted", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Feedback_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
