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

  public User(String password, String eMail,String firstName, String lastName) {
    this.password = password;
    this.eMail = eMail;
    this.firstName=firstName;
    this.lastName=lastName;
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

  public int getFirstName() {
    return firstName;
  }

  public int getLastName() {
    return lastName;
  }
}
