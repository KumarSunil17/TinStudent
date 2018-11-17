package com.kumarsunil17.tinstudent.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kumarsunil17.tinstudent.AllStudentActivity;
import com.kumarsunil17.tinstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Fragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private MaterialCardView firstYr, secondYr, thirdYr, fourthyr, fifthYr;
    private DatabaseReference studentRef;

    public Student_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_student_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Students");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_student);

        studentRef = FirebaseDatabase.getInstance().getReference().child("users").child("student");
        firstYr = v.findViewById(R.id.first_yr_row);
        secondYr = v.findViewById(R.id.second_yr_row);
        thirdYr = v.findViewById(R.id.third_yr_row);
        fourthyr = v.findViewById(R.id.fourth_yr_row);
        fifthYr = v.findViewById(R.id.fifth_yr_row);

        firstYr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a,AllStudentActivity.class);
                i.putExtra("year","1");
                startActivity(i);

            }
        });

        secondYr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a,AllStudentActivity.class);
                i.putExtra("year","2");
                startActivity(i);
            }
        });

        thirdYr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a,AllStudentActivity.class);
                i.putExtra("year","3");
                startActivity(i);
            }
        });

        fourthyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a,AllStudentActivity.class);
                i.putExtra("year","4");
                startActivity(i);
            }
        });

        fifthYr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a,AllStudentActivity.class);
                i.putExtra("year","5");
                startActivity(i);
            }
        });
        return v;
    }
}
