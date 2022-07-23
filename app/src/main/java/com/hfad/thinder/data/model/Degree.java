package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Degree {
  private String name;
  private String degree; //Maybe enum?

  public Degree( String name, String degree) {

    this.name = name;
    this.degree = degree;

  }



  public String getName() {
    return name;
  }

  public String getDegree() {
    return degree;
  }


}
