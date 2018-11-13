package com.kumarsunil17.tinstudent.view_holder;

import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kumarsunil17.tinstudent.R;

public class FAQ_ViewHolder extends RecyclerView.ViewHolder {
    private TextView question, answer;
    private MaterialCardView card;

    public FAQ_ViewHolder(@NonNull View itemView) {
        super(itemView);
        question = itemView.findViewById(R.id.faq_row_question);
        answer = itemView.findViewById(R.id.faq_row_answer);
        card = itemView.findViewById(R.id.main_faq_row);

    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }

    public void setAnswer(String answer) {
        this.answer.setText(answer);
    }

    public MaterialCardView getCard() {
        return card;
    }
}
