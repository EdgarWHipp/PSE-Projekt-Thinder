package com.hfad.thinder.data.model;

import android.media.Image;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Thesis {
  @SerializedName("id")
  private int thesisId;

  private String name;
  @SerializedName("text")
  private String body;
  private Form form;
  @SerializedName("list")
  private List<Image> images;

  public Thesis(int thesisId, String name, String body, Form form, List<Image> images) {
    this.thesisId = thesisId;
    this.name = name;
    this.body = body;
    this.form = form;
    this.images = images;
  }

  public int getThesisId() {
    return thesisId;
  }

  public String getName() {
    return name;
  }

  public String getBody() {
    return body;
  }
  @Nullable
  public Form getForm() {
    return form;
  }

  public List<Image> getImages() {
    return images;
  }
}
