package com.kumarsunil17.tinstudent.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kumarsunil17.tinstudent.Feedback_activity;
import com.kumarsunil17.tinstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Help_Fragment extends Fragment {

    private View v;
    private AppCompatActivity a;
    private LinearLayout feedBackBtn;

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

        feedBackBtn = v.findViewById(R.id.btn_feedback);
        feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(a,Feedback_activity.class));
            }
        });


        return v;
    }

}
