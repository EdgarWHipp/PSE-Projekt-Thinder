package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Degree {
  @SerializedName("id")
  private String degreeId;
  private String name;
  private String degree; //Maybe enum?
  private Set<Thesis> possibleTheses;

  public Degree(String degreeId, String name, String degree, Set<Thesis> possibleTheses) {
    this.degreeId = degreeId;
    this.name = name;
    this.degree = degree;
    this.possibleTheses = possibleTheses;
  }

  public String getDegreeId() {
    return degreeId;
  }

  public String getName() {
    return name;
  }

  public String getDegree() {
    return degree;
  }

  public Set<Thesis> getPossibleTheses() {
    return possibleTheses;
  }
}
