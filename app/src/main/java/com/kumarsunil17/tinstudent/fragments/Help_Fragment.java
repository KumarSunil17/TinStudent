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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kumarsunil17.tinstudent.Feedback_activity;
import com.kumarsunil17.tinstudent.R;
import com.kumarsunil17.tinstudent.pojos.FAQ_Data;
import com.kumarsunil17.tinstudent.pojos.NotificationData;
import com.kumarsunil17.tinstudent.view_holder.FAQ_ViewHolder;
import com.kumarsunil17.tinstudent.view_holder.NotificationViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Help_Fragment extends Fragment {

    private View v;
    private AppCompatActivity a;
    private LinearLayout feedBackBtn;
    private FirebaseRecyclerAdapter<FAQ_Data,FAQ_ViewHolder> f;
    private DatabaseReference faqRef;
    private RecyclerView faqView;

    public Help_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_help_, container, false);
        a = (AppCompatActivity) getActivity();
        a.getSupportActionBar().setTitle("Help");
        NavigationView nav = a.findViewById(R.id.nav_view);
        nav.setCheckedItem(R.id.nav_help);

        faqView = v.findViewById(R.id.container_faq);
        faqView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        faqView.hasFixedSize();

        feedBackBtn = v.findViewById(R.id.btn_feedback);
        feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(a,Feedback_activity.class));
            }
        });

        faqRef = FirebaseDatabase.getInstance().getReference().child("faq");
        faqRef.keepSynced(true);
        FirebaseRecyclerOptions<FAQ_Data> options = new FirebaseRecyclerOptions.Builder<FAQ_Data>().setQuery(faqRef,FAQ_Data.class).build();
        f = new FirebaseRecyclerAdapter<FAQ_Data, FAQ_ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FAQ_ViewHolder holder, int position, @NonNull FAQ_Data model) {
                faqRef.child(getRef(position).getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.setAnswer(dataSnapshot.child("answer").getValue().toString());
                        holder.setQuestion(dataSnapshot.child("question").getValue().toString());
                        holder.getCard().setVisibility(View.VISIBLE);
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
            public FAQ_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.faq_row,viewGroup,false);
                return new FAQ_ViewHolder(view);
            }
        };
        faqView.setAdapter(f);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        f.startListening();
    }
}
