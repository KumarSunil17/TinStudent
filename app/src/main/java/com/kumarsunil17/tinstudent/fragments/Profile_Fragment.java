package com.kumarsunil17.tinstudent.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kumarsunil17.tinstudent.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private TextView profileName, profileRoll, profilePhone, profileAddress, profileCurrentSemester, profileEmail;
    private ImageView profileDP;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private StorageReference dpRef;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_profile_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Profile");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_profile);

        profileDP = v.findViewById(R.id.profile_dp);
        profileName = v.findViewById(R.id.profile_name);
        profileRoll = v.findViewById(R.id.profile_roll);
        profilePhone = v.findViewById(R.id.profile_phone);
        profileAddress = v.findViewById(R.id.profile_address);
        profileCurrentSemester = v.findViewById(R.id.profile_semester_year);
        profileEmail = v.findViewById(R.id.profile_email);

        mAuth = FirebaseAuth.getInstance();
        dpRef = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child("student");
        userRef.keepSynced(true);

        userRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profileName.setText(dataSnapshot.child("name").getValue().toString());
                profileEmail.setText(dataSnapshot.child("email").getValue().toString());
                profileAddress.setText(dataSnapshot.child("address").getValue().toString());
                profileRoll.setText(dataSnapshot.child("roll").getValue().toString());
                profilePhone.setText(dataSnapshot.child("number").getValue().toString());
                profileCurrentSemester.setText(dataSnapshot.child("year").getValue().toString());

                String dpUrl = dataSnapshot.child("image").getValue().toString();
                Picasso.with(getContext()).load(dpUrl).into(profileDP);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return v;
    }
}
