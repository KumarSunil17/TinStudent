package com.kumarsunil17.tinstudent.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumarsunil17.tinstudent.R;
import com.kumarsunil17.tinstudent.StudentProfileActivity;
import com.kumarsunil17.tinstudent.TeacherProfileActivity;
import com.kumarsunil17.tinstudent.pojos.TeacherData;
import com.kumarsunil17.tinstudent.view_holder.TeacherViewholder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Teacher_Fragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private DatabaseReference teacherRef;
    private FirebaseRecyclerAdapter<TeacherData,TeacherViewholder> f;
    private RecyclerView teacherView;

    public Teacher_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_teacher_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Teachers");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_teacher);

        teacherView = v.findViewById(R.id.container_teacher);
        teacherView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        teacherView.hasFixedSize();

        teacherRef = FirebaseDatabase.getInstance().getReference().child("users").child("teacher");
        teacherRef.keepSynced(true);

        FirebaseRecyclerOptions<TeacherData> options = new FirebaseRecyclerOptions.Builder<TeacherData>().setQuery(teacherRef,TeacherData.class).build();
        f = new FirebaseRecyclerAdapter<TeacherData, TeacherViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TeacherViewholder holder, final int position, @NonNull TeacherData model) {
                teacherRef.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.setName(dataSnapshot.child("name").getValue().toString());
                        final String dpUrl = dataSnapshot.child("image").getValue().toString();
                        Picasso.with(a).load(dpUrl).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.no_profile)
                                .into(holder.getDp(), new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }
                                    @Override
                                    public void onError() {
                                        Picasso.with(a).load(dpUrl).placeholder(R.drawable.no_profile).into(holder.getDp());
                                    }
                                });
                        holder.getCard().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getContext(),TeacherProfileActivity.class);
                                i.putExtra("uid",getRef(position).getKey());
                                startActivity(i);
                            }
                        });


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
            }

            @NonNull
            @Override
            public TeacherViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.teacher_row,viewGroup,false);
                return new TeacherViewholder(view);
            }
        };
        teacherView.setAdapter(f);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        f.startListening();
    }
}
