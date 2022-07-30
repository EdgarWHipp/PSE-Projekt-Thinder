package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;
import java.util.UUID;

public class Student extends User {

    private Set<Degree> degree;


    public Student(USERTYPE role, UUID id, boolean active, UUID universityId, String password, String mail, String firstName, String lastName, Set<Degree> degree) {
        super(role, id, active, universityId, password, mail, firstName, lastName);
        this.degree = degree;
    }

    public Set<Degree> getDegree() {
        return degree;
    }

}
