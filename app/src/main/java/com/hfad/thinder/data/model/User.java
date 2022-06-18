package com.hfad.thinder.data.model;

/**
 * Model of the User Class, contains all attributes that is required for a user upon registration.
 */

public class User {
    private final String password;

    private final String eMail;

    private int userId;

    private String firstName;
    private String lastName;
    private String university;

    public User(String password, String eMail, int userId, String firstName, String lastName, String university) {
        this.password = password;
        this.eMail = eMail;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
    }



    public String getPassword() {
        return password;
    }

    public String geteMail() {
        return eMail;
    }

    public int getId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
