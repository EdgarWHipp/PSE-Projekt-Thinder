package com.hfad.thinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.UUID;

/**
 * Model of the User Class, contains all attributes that is required for a user upon registration.
 */

/**
 * This class holds all the values of a user. This can later be fetched from the UserRepository.
 */
public class User {
    @SerializedName("password")
    @Expose
    private final String password;
    @SerializedName("mail")
    @Expose
    private final String mail;
    @SerializedName("type")
    private USERTYPE type;
    @SerializedName("id")
    private UUID id;
    @SerializedName("active")
    private boolean active;
    @SerializedName("uni_id")
    private UUID universityId;
    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("isComplete")
    @Expose
    private boolean isComplete;

    public User(USERTYPE type, UUID id, boolean active, UUID universityId, String password,
                String mail, String firstName, String lastName, boolean isComplete) {
        this.type = type;
        this.id = id;
        this.active = active;
        this.universityId = universityId;
        this.password = password;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isComplete = isComplete;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public USERTYPE getType() {
        return type;
    }

    public void setType(USERTYPE type) {
        this.type = type;
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

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(password, user.password)
                && Objects.equals(mail, user.mail)
                && Objects.equals(type, user.type)
                && Objects.equals(id, user.id)
                && Objects.equals(active, user.active)
                && Objects.equals(universityId, user.universityId)
                && Objects.equals(isComplete, user.isComplete);
    }
}