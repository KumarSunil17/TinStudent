package com.kumarsunil17.tinstudent.pojos;

import com.google.firebase.database.PropertyName;

public class StudentData {
    @PropertyName("name")
    private String name;
    @PropertyName("image")
    private String image;
    @PropertyName("roll")
    private String roll;

    public StudentData(String name, String image, String roll) {
        this.name = name;
        this.image = image;
        this.roll = roll;
    }

    public StudentData() {
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

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
