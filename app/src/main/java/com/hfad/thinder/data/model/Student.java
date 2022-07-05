package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Student extends User{

    private String degree;


    public Student(String password, String eMail, String userId, String firstName, String lastName, String university, String degree) {
        super(password, eMail, userId, firstName, lastName);
        this.degree = degree;
    }

    public String getDegree() {
        return degree;
    }

}
