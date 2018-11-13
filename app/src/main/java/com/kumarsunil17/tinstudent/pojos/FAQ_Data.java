package com.kumarsunil17.tinstudent.pojos;

import com.google.firebase.database.PropertyName;

public class FAQ_Data {
    @PropertyName("id")
    private String id;

    public FAQ_Data() {
    }

    public FAQ_Data(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
