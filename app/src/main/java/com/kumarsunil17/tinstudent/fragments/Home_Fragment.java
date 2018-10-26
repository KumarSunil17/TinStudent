package com.kumarsunil17.tinstudent.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumarsunil17.tinstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private DatabaseReference studentRef;
    private FirebaseAuth mAuth;
    String studentYear;

    public Home_Fragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_home_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Home");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_home);

        mAuth = FirebaseAuth.getInstance();
        //Toast.makeText(a, mAuth.getCurrentUser().getUid()+" uid", Toast.LENGTH_SHORT).show();
        String uid = mAuth.getCurrentUser().getUid();
        studentRef = FirebaseDatabase.getInstance().getReference().child("users").child("student");
        studentRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentYear = dataSnapshot.child("year").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(a, "Studet year : "+studentYear, Toast.LENGTH_SHORT).show();
        return v;
    }
}
