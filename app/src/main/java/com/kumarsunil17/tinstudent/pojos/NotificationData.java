package com.kumarsunil17.tinstudent.pojos;

import com.google.firebase.database.PropertyName;

public class NotificationData {
    @PropertyName("id")
    private String id;

    public NotificationData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationData(String id) {
        this.id = id;
    }
}
