package com.kumarsunil17.tinstudent.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumarsunil17.tinstudent.R;

import java.lang.ref.Reference;

/**
 * A simple {@link Fragment} subclass.
 */
public class Syllabus_Fragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private DatabaseReference syllabusRef,studentRef;
    private FirebaseAuth mAuth;
    private String studentYear;
    private ProgressDialog pg;

    public Syllabus_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_syllabus_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Syllabus");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_syllabus);

        pg = new ProgressDialog(a);
        pg.setMessage("Please wait while we are fetching your syllabus");
        pg.setTitle("Please wait");
        pg.setCancelable(false);
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        mAuth = FirebaseAuth.getInstance();
        syllabusRef  = FirebaseDatabase.getInstance().getReference().child("syllabus");
        studentRef = FirebaseDatabase.getInstance().getReference().child("users").child("student").child(mAuth.getCurrentUser().getUid());

        studentRef.keepSynced(true);
        studentRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentYear = dataSnapshot.child("year").getValue(String.class);

                if (studentYear.equals("1")){
                    getSyllabus("first",this);
                }else if(studentYear.equals("2")){
                    getSyllabus("second",this);
                }else if(studentYear.equals("3")){
                    getSyllabus("third",this);
                }else if(studentYear.equals("4")){
                    getSyllabus("fourth",this);
                }else if(studentYear.equals("5")) {
                    getSyllabus("fifth",this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
    private void getSyllabus(String year, ValueEventListener listener){
        syllabusRef.child(year).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pg.dismiss();
                String url = dataSnapshot.child("url").getValue().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(url),"application/pdf");
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar snackbar = Snackbar
                        .make(getView(), databaseError.getMessage(), Snackbar.LENGTH_SHORT)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                snackbar.setActionTextColor(Color.WHITE);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.BLUE);

                snackbar.show();
            }
        });
        studentRef.removeEventListener(listener);
    }
}
