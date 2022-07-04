package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class ThesisRating {
  @SerializedName("id")
  private String thesisRatingId;
  private Student student;
  private Thesis thesis;

  public String getThesisRatingId() {
    return thesisRatingId;
  }

  public Student getStudent() {
    return student;
  }

  public Thesis getThesis() {
    return thesis;
  }

  public boolean isPositiveRated() {
    return positiveRated;
  }

  public ThesisRating(String thesisRatingId, Student student, Thesis thesis, boolean positiveRated) {
    this.thesisRatingId = thesisRatingId;
    this.student = student;
    this.thesis = thesis;
    this.positiveRated = positiveRated;
  }

  private boolean positiveRated;
}
