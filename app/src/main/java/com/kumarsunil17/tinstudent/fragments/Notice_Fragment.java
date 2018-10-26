package com.kumarsunil17.tinstudent.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumarsunil17.tinstudent.R;
import com.kumarsunil17.tinstudent.pojos.NotificationData;
import com.kumarsunil17.tinstudent.view_holder.NotificationViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notice_Fragment extends Fragment {
    private View v;
    private AppCompatActivity a;
    private FirebaseAuth mAuth;
    private DatabaseReference noticeRef,notificationRef,studentRef;
    private FirebaseRecyclerAdapter<NotificationData,NotificationViewHolder> f;
    private String studentYear;
    private RecyclerView noticeView;

    public Notice_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_notice_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Notice");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_profile);

        mAuth = FirebaseAuth.getInstance();

        noticeView = v.findViewById(R.id.notice_container);
        noticeView.hasFixedSize();
        noticeView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        studentRef = FirebaseDatabase.getInstance().getReference().child("users").child("student").child(mAuth.getCurrentUser().getUid());
        noticeRef = FirebaseDatabase.getInstance().getReference().child("notice");
        notificationRef = FirebaseDatabase.getInstance().getReference().child("notification");

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentYear = dataSnapshot.child("year").getValue(String.class);

                if (studentYear.equals("5")){
                    getNotification("fifth");
                }else if(studentYear.equals("4")){
                    getNotification("fourth");
                }else if(studentYear.equals("4")){
                    getNotification("third");
                }else if(studentYear.equals("4")){
                    getNotification("second");
                }else if(studentYear.equals("4")){
                    getNotification("first");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
    private void getNotification(final String studentYear){

        FirebaseRecyclerOptions<NotificationData> options = new FirebaseRecyclerOptions.Builder<NotificationData>().setQuery(noticeRef.child(studentYear),NotificationData.class).build();
        f = new FirebaseRecyclerAdapter<NotificationData, NotificationViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position, @NonNull NotificationData model) {
                String id = model.getId();
                DatabaseReference db = notificationRef.child(id);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        holder.setBodyText(dataSnapshot.child("body").getValue().toString());
                        holder.setTimeText(dataSnapshot.child("time").getValue().toString());
                        holder.setTitleText(dataSnapshot.child("title").getValue().toString());
                        holder.setPostedbyText(dataSnapshot.child("by").getValue().toString());
                        final String uri = dataSnapshot.child("url").getValue().toString();
                        holder.lin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+uri)));
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(a, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.notification_row,viewGroup,false);
                return new NotificationViewHolder(view);
            }
        };
        f.startListening();
        noticeView.setAdapter(f);
    }
}
