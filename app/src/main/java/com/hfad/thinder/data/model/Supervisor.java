package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Supervisor extends User {

    @SerializedName("degree")
    private String academicDegree;
    @SerializedName("location")
    private String location;
    @SerializedName("institute")
    private String institute;
    @SerializedName("phoneNumber")
    private String phoneNumber;


    public Supervisor(String password, String eMail, String userId, String firstName,
                      String lastName, String university, String academicDegree, String location, String institute, String phoneNumber) {
        super(password, eMail, firstName, lastName);
        this.academicDegree = academicDegree;
        this.location = location;
        this.institute = institute;
        this.phoneNumber = phoneNumber;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
