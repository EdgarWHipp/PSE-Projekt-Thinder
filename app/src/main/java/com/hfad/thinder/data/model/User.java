package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model of the User Class, contains all attributes that is required for a user upon registration.
 */

public class User {
    private final String password;
    private final String eMail;
    @SerializedName("id")
    private String userId;
    private String firstName;
    private String lastName;
    private String university;

    public User(String password, String eMail, String userId, String firstName, String lastName) {
        this.password = password;
        this.eMail = eMail;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;

    }
    public User(String password, String eMail, String firstName, String lastName) {
        this.password = password;
        this.eMail = eMail;

        this.firstName = firstName;
        this.lastName = lastName;

    }


    public String getPassword() {
        return password;
    }

    public String geteMail() {
        return eMail;
    }

    public String getId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
