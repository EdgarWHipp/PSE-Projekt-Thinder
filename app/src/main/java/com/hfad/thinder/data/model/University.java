package com.hfad.thinder.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Set;
import java.util.UUID;

public class University {
  @SerializedName("id")
  private UUID universityId;
  @SerializedName("name")
  private String name;
  @SerializedName("members")
  @Nullable
  private Set<User> members;
  @SerializedName("studentMailRegex")
  private String studentMailRegex;
  @SerializedName("supervisorMailRegex")
  private String supervisorMailRegex;




  protected University(){}
  public UUID getUniversityId() {
    return universityId;
  }

  public University(UUID universityId, String name, @Nullable Set<User> members, String studentMailRegex, String supervisorMailRegex) {
    this.universityId = universityId;
    this.name = name;
    this.members = members;
    this.studentMailRegex = studentMailRegex;
    this.supervisorMailRegex = supervisorMailRegex;
  }

  public void setUniversityId(UUID universityId) {
    this.universityId = universityId;
  }

  @Nullable
  public Set<User> getMembers() {
    return members;
  }

  public void setMembers(@Nullable Set<User> members) {
    this.members = members;
  }

  public University(String name, String studentMailRegex, String supervisorMailRegex) {
    this.name = name;
    this.studentMailRegex = studentMailRegex;
    this.supervisorMailRegex = supervisorMailRegex;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }



  public String getStudentMailRegex() {
    return studentMailRegex;
  }

  public void setStudentMailRegex(String studentMailRegex) {
    this.studentMailRegex = studentMailRegex;
  }

  public String getSupervisorMailRegex() {
    return supervisorMailRegex;
  }

  public void setSupervisorMailRegex(String supervisorMailRegex) {
    this.supervisorMailRegex = supervisorMailRegex;
  }



}
