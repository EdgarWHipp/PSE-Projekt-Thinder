package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Student extends User {

    private ArrayList<Degree> degree;


    public Student(USERTYPE role, UUID id, boolean active, UUID universityId, String password, String mail, String firstName, String lastName, ArrayList<Degree> degree) {
        super(role, id, active, universityId, password, mail, firstName, lastName);
        this.degree = degree;
    }

    public ArrayList<Degree> getDegree() {
        return degree;
    }

    public void setDegree(ArrayList<Degree> degree) {
        this.degree = degree;
    }
}
