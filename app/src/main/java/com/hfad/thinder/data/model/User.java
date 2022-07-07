package com.hfad.thinder.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model of the User Class, contains all attributes that is required for a user upon registration.
 */

public class User {
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


    public User(String password, String eMail, String firstName, String lastName) {
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
