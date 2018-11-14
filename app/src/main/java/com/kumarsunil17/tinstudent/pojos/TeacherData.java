package com.kumarsunil17.tinstudent.pojos;

import com.google.firebase.database.PropertyName;

public class TeacherData {
    @PropertyName("name")
    private String name;
    @PropertyName("image")
    private String image;

    public TeacherData(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public TeacherData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
