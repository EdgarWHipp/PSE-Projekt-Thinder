package com.hfad.thinder.data.response;

import com.hfad.thinder.data.model.University;

public class UserResponse {
  private final String id;
  private final String password;
  private final String eMail;
  private String firstName;
  private String lastName;
  private University university;
  public String getId() {
    return id;
  }

  public UserResponse(String id, String password, String eMail, String firstName, String lastName, University university) {
    this.id = id;
    this.password = password;
    this.eMail = eMail;
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

  public University getUniversity() {
    return university;
  }

  public void setUniversity(University university) {
    this.university = university;
  }


}
