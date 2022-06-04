package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Supervisor {
    private String firstName;

    private String lastName;
    @SerializedName("id")
    private int supervisorId;

    private String university;
    @SerializedName("degree")
    private String academicDegree;
    private String location;
    private String institute;
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public String getUniversity() {
        return university;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public String getLocation() {
        return location;
    }

    public String getInstitute() {
        return institute;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
