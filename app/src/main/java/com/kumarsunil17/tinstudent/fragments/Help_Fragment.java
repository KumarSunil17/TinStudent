package com.kumarsunil17.tinstudent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumarsunil17.tinstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Help_Fragment extends Fragment {


    public Help_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_, container, false);
    }

}
