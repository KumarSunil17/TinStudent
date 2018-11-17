package com.kumarsunil17.tinstudent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumarsunil17.tinstudent.pojos.StudentData;
import com.kumarsunil17.tinstudent.view_holder.StudentViewholder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class AllStudentActivity extends AppCompatActivity {

    private RecyclerView allStudentView;

    private DatabaseReference studentRef;
    private FirebaseRecyclerAdapter<StudentData,StudentViewholder> f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student);

        allStudentView = findViewById(R.id.container_all_students);
        allStudentView.hasFixedSize();
        allStudentView.setLayoutManager(new LinearLayoutManager(AllStudentActivity.this,LinearLayoutManager.VERTICAL,false));

        final String year = getIntent().getExtras().getString("year");

        final ProgressDialog pg = new ProgressDialog(this);
        pg.setMessage("Please wait while we are fetching data");
        pg.setTitle("Please wait");
        pg.setCancelable(false);
        pg.setCanceledOnTouchOutside(false);
        //pg.show();

        studentRef = FirebaseDatabase.getInstance().getReference().child("users").child("student");
        studentRef.keepSynced(true);
        FirebaseRecyclerOptions<StudentData> options = new FirebaseRecyclerOptions.Builder<StudentData>().setQuery(studentRef.orderByChild("year").equalTo(year),StudentData.class).build();
        f = new FirebaseRecyclerAdapter<StudentData, StudentViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final StudentViewholder holder, final int position, @NonNull final StudentData model) {
                studentRef.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.setName(dataSnapshot.child("name").getValue().toString());
                        holder.setRoll(dataSnapshot.child("roll").getValue().toString());
                        holder.getCard().setVisibility(View.VISIBLE);

                        holder.getCard().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(AllStudentActivity.this,StudentProfileActivity.class);
                                i.putExtra("uid",getRef(position).getKey());
                                startActivity(i);
                            }
                        });
                        final String dpURL = dataSnapshot.child("image").getValue().toString();
                        Picasso.with(AllStudentActivity.this).load(dpURL).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.no_profile)
                                .into(holder.getDp(), new Callback() {
                                    @Override
                                    public void onSuccess() {
                                    }
                                    @Override
                                    public void onError() {
                                        Picasso.with(AllStudentActivity.this).load(dpURL).placeholder(R.drawable.no_profile).into(holder.getDp());
                                    }
                                });

                        pg.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AllStudentActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        pg.dismiss();
                    }
                });
            }

            @NonNull
            @Override
            public StudentViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(AllStudentActivity.this).inflate(R.layout.student_row,viewGroup,false);
                return new StudentViewholder(view);
            }
        };
        allStudentView.setAdapter(f);
    }

    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }
}
