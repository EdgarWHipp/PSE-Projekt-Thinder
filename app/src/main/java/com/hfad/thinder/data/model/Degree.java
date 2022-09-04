package com.hfad.thinder.data.model;

import java.util.Objects;
import java.util.UUID;

/**
 * This class defines the attributes of a course of study that the student can select or the supervisor create a specified thesis for.
 */
public class Degree {
    private String degree;
    private UUID id;
    private UUID university_id;

    /**
     * Constructor of the Degree Class.
     * @param degree
     * @param id
     * @return Degree
     */
    public Degree(String degree, UUID id) {
        this.degree = degree;
        this.id = id;

    }

    public UUID getUniversityID() {
        return university_id;
    }

    public void setUniversityID(UUID universityID) {
        this.university_id = universityID;
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

    /**
     * Checks if two Degree objects.
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Degree)) {
            return false;
        }
        Degree degree = (Degree) obj;
        return Objects.equals(degree.degree, this.degree)
                && Objects.equals(degree.id.toString(), this.id.toString())
                && Objects.equals(degree.university_id.toString(), this.university_id.toString());
    }
}
