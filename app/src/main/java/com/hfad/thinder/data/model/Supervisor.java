package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * This class defines all attributes that a supervisor needs, apart from the already defined attributes in the User class.
 */
public class Supervisor extends User {

    @SerializedName("academicDegree")
    private String academicDegree;
    @SerializedName("building")
    private String building;
    @SerializedName("officeNumber")
    private String OfficeNumber;
    @SerializedName("institute")
    private String institute;
    @SerializedName("phoneNumber")
    private String phoneNumber;

    public Supervisor(USERTYPE type, UUID id, boolean active, UUID universityId, String mail,
                      String firstName, String lastName, String academicDegree, String building,
                      String officeNumber, String institute, String phoneNumber,
                      boolean isComplete) {
        super(type, id, active, universityId, mail, firstName, lastName, isComplete);
        this.academicDegree = academicDegree;
        this.building = building;
        this.OfficeNumber = officeNumber;
        this.institute = institute;
        this.phoneNumber = phoneNumber;
    }

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
