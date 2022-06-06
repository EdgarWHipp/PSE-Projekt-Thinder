package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Student {

  private String firstName;

  private String lastName;
  @SerializedName("id")
  private int studentId;
  private String university;
  private String degree;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getStudentId() {
    return studentId;
  }

  public String getUniversity() {
    return university;
  }

  public String getDegree() {
    return degree;
  }

}
