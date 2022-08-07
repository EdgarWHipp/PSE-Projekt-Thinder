package com.hfad.thinder.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.Set;

public class Degree {
  private String degree;

  public Degree(String degree) {
    this.degree = degree;

  }


  public String getDegree() {
    return degree;
  }



}
