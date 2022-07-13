package com.hfad.thinder.viewmodels;

import android.media.Image;
import androidx.annotation.Nullable;
import java.util.Set;

public class Thesis {
  private final String task;
  private final String motivation;
  private final String questions;
  private final String professor;
  private final Set<String> fieldsOfStudy;
  private final Set<Image> images;
  @Nullable
  private final String supervisor;
  @Nullable
  private final String rateing;

  public Thesis(String task, String motivation, String questions, String professor,
                Set<String> fieldsOfStudy, Set<Image> images, @Nullable String rateing, @Nullable
                    String supervisor) {//Todo: supervisor und Professor möglicherweise Verdoppelung
    this.task = task;
    this.motivation = motivation;
    this.questions = questions;
    this.professor = professor;
    this.fieldsOfStudy = fieldsOfStudy;
    this.images = images;
    this.rateing = rateing;
    this.supervisor = supervisor;
  }

  public String getTask() {
    return task;
  }

  public String getMotivation() {
    return motivation;
  }

  public String getQuestions() {
    return questions;
  }

  public String getProfessor() {
    return professor;
  }

  public Set<String> getFieldsOfStudy() {
    return fieldsOfStudy;
  }

  public Set<Image> getImages() {
    return images;
  }

  @Nullable
  public String getSupervisor() {
    return supervisor;
  }

  @Nullable
  public String getRateing() {
    return rateing;
  }
}
