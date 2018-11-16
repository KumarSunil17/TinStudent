package com.kumarsunil17.tinstudent.view_holder;

import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kumarsunil17.tinstudent.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Viewholder extends RecyclerView.ViewHolder {
    private CircleImageView dp;
    private TextView name, roll;
    private MaterialCardView card;

    public Student_Viewholder(@NonNull View itemView) {
        super(itemView);

        dp = itemView.findViewById(R.id.student_row_dp);
        name = itemView.findViewById(R.id.student_row_name);
        card = itemView.findViewById(R.id.main_student_row);
        roll = itemView.findViewById(R.id.student_row_roll);
    }

    public CircleImageView getDp() {
        return dp;
    }

    public MaterialCardView getCard() {
        return card;
    }

    public void setName(String Name) {
        name.setText(Name);
    }

    public void setRoll(String Roll) {
        roll.setText(Roll);
    }
}
