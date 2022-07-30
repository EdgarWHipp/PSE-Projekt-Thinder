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
    @SerializedName("uni_id")
    private UUID universityId;
    @SerializedName("password")
    @Expose
    private final String password;
    @SerializedName("mail")
    @Expose
    private final String mail;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;

    public User(USERTYPE role, UUID id, boolean active, UUID universityId, String password,
                String mail, String firstName, String lastName) {
        this.role = role;
        this.id = id;
        this.active = active;
        this.universityId = universityId;
        this.password = password;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
