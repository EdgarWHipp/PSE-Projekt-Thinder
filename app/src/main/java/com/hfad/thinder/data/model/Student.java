package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Student extends User {

    private ArrayList<Degree> degrees;


    public Student(USERTYPE role, UUID id, boolean active, UUID universityId, String password, String mail, String firstName, String lastName, ArrayList<Degree> degrees) {
        super(role, id, active, universityId, password, mail, firstName, lastName);
        this.degrees = degrees;
    }

    public ArrayList<Degree> getDegrees() {
        return degree;
    }

    public void setDegrees(ArrayList<Degree> degree) {
        this.degree = degree;
    }
}
