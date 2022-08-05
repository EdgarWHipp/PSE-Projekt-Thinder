package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class Degree {
  private String degree;

  public Degree(String degree) {

    this.degree = degree;

  }




  public String getDegree() {
    return degree;
  }

  @Override
  public boolean equals(Object degree) {
    return this.degree.equals(((Degree) degree).getDegree());
  }


}
