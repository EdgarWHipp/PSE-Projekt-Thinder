package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Student extends User{

    private Set<Degree> degree;


    public Student(String password, String eMail, String userId, String firstName, String lastName, String university, Set<Degree> degree) {
        super(password, eMail, firstName, lastName);
        this.degree = degree;
    }

    public Set<Degree> getDegree() {
        return degree;
    }

}
