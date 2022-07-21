package com.hfad.thinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Model of the User Class, contains all attributes that is required for a user upon registration.
 */

public class User {
    @SerializedName("role")
    private USERTYPE role;
    @SerializedName("id")
    private UUID id;
    @SerializedName("active")
    private boolean active;

    public USERTYPE getRole() {
        return role;
    }

    public void setRole(USERTYPE role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getUniversityId() {
        return universityId;
    }

    public void setUniversityId(UUID universityId) {
        this.universityId = universityId;
    }

    @SerializedName("uni_id")
    private UUID universityId;
    @SerializedName("password")
    @Expose
    private final String password;
    @SerializedName("eMail")
    @Expose
    private final String eMail;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;

    public User(USERTYPE role, UUID id, boolean active, UUID universityId, String password, String eMail, String firstName, String lastName) {
        this.role = role;
        this.id = id;
        this.active = active;
        this.universityId = universityId;
        this.password = password;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public String geteMail() {
        return eMail;
    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
