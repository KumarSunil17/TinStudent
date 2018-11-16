package com.kumarsunil17.tinstudent.view_holder;

import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kumarsunil17.tinstudent.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherViewholder extends RecyclerView.ViewHolder {
    private CircleImageView dp;
    private TextView name;
    private MaterialCardView card;

    public TeacherViewholder(@NonNull View itemView) {
        super(itemView);

        dp = itemView.findViewById(R.id.teacher_row_dp);
        name = itemView.findViewById(R.id.teacher_row_name);
        card = itemView.findViewById(R.id.main_teacher_row);

    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public CircleImageView getDp() {
        return dp;
    }

    public MaterialCardView getCard() {
        return card;
    }
}
