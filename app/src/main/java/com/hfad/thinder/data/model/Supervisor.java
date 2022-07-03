package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Supervisor extends User{

    @SerializedName("degree")
    private String academicDegree;
    private String location;
    private String institute;
    private String phoneNumber;


    public Supervisor(String password, String eMail, String userId, String firstName,
                      String lastName, String university, String academicDegree, String location, String institute, String phoneNumber) {
        super(password, eMail, userId, firstName, lastName, university);
        this.academicDegree = academicDegree;
        this.location = location;
        this.institute = institute;
        this.phoneNumber = phoneNumber;
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
