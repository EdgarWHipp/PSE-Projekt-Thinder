package com.hfad.thinder.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Degree {
  private String degree;
  private UUID id;
  private UUID universityID;
  public void setDegree(String degree) {
    this.degree = degree;
  }

  public UUID getUniversityID() {
    return universityID;
  }

  public void setUniversityID(UUID universityID) {
    this.universityID = universityID;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Degree(String degree) {
    this.degree = degree;

  }


  public String getDegree() {
    return degree;
  }



}
