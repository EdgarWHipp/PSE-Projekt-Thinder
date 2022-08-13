package com.hfad.thinder.data.model;

import java.util.UUID;

/**
 * This class defines the attributes of a course of study that the student can select or the supervisor create a specified thesis for.
 */
public class Degree {
    private String degree;
    private UUID id;
    private UUID universityID;

    public Degree(String degree, UUID id) {
        this.degree = degree;
        this.id = id;

    }

    public UUID getUniversityID() {
        return universityID;
    }

    public void setUniversityID(UUID universityID) {
        this.universityID = universityID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }


}
