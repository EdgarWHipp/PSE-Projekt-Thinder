package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Form {
  @SerializedName("text")
  private String questions;

  public Form(String questions) {
    this.questions = questions;
  }

  public String getQuestions() {
    return questions;
  }

}
