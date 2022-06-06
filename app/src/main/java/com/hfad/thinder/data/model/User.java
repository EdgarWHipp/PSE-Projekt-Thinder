package com.hfad.thinder.data.model;

/**
 * Model of the User Class, contains all attributes that is required for a user upon registration.
 */

public class User {
  private final String password;

  private final String eMail;

  private int userId;

  private int firstName;
  private int lastName;

  public User(String password, String eMail) {
    this.password = password;
    this.eMail = eMail;
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
