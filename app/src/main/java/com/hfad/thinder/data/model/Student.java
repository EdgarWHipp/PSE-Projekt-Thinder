package com.hfad.thinder.data.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * This class defines all attributes that a student needs, apart from the already defined attributes in the User class.
 */
public class Student extends User {

    private ArrayList<Degree> degrees;


    public Student(USERTYPE type, UUID id, boolean active, UUID universityId, String mail, String firstName, String lastName, ArrayList<Degree> degrees, boolean isComplete) {
        super(type, id, active, universityId, mail, firstName, lastName, isComplete);
        this.degrees = degrees;
    }

    public ArrayList<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(ArrayList<Degree> degrees) {
        this.degrees = degrees;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        Student student = (Student) obj;
        return Objects.equals(this.degrees, student.degrees)
                && super.equals(student);
    }
}
