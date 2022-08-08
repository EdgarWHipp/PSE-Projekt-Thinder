package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Supervisor extends User {

    @SerializedName("degree")
    private String academicDegree;
    @SerializedName("building")
    private String building;
    @SerializedName("officeNumber")
    private String OfficeNumber;
    @SerializedName("institute")
    private String institute;
    @SerializedName("phoneNumber")
    private String phoneNumber;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getOfficeNumber() {
        return OfficeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        OfficeNumber = officeNumber;
    }


    public Supervisor(USERTYPE role, UUID id, boolean active, UUID universityId, String password, String mail, String firstName, String lastName, String academicDegree, String building, String officeNumber, String institute, String phoneNumber) {
        super(role, id, active, universityId, password, mail, firstName, lastName);
        this.academicDegree = academicDegree;
        this.building = building;
        this.OfficeNumber = officeNumber;
        this.institute = institute;
        this.phoneNumber = phoneNumber;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
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
