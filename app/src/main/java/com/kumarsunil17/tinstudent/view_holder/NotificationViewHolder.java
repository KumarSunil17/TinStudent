package com.kumarsunil17.tinstudent.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kumarsunil17.tinstudent.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText, bodyText, postedbyText, timeText;
    public LinearLayout lin;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.notification_title);
        bodyText = itemView.findViewById(R.id.notification_body);
        postedbyText = itemView.findViewById(R.id.notification_by);
        timeText = itemView.findViewById(R.id.notification_time);
        lin = itemView.findViewById(R.id.main_notification_row);
    }

    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }

    public void setBodyText(String bodyText) {
        this.bodyText.setText(bodyText);
    }

    public void setPostedbyText(String postedbyText) {
        this.postedbyText.setText(postedbyText);
    }

    public void setTimeText(String timeText) {
        this.timeText.setText(timeText);
    }
}
