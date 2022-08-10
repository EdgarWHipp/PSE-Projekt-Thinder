package com.hfad.thinder.viewmodels;

import java.util.UUID;

public class CourseOfStudyItem {
  private final UUID uuid;
  private String courseOfStudy;
  private boolean picked = false;

  public CourseOfStudyItem(String courseOfStudy, UUID uuid, boolean picked) {
    this.courseOfStudy = courseOfStudy;
    this.picked = picked;
    this.uuid = uuid;
  }

  public String getCourseOfStudy() {
    return courseOfStudy;
  }

  public void setCourseOfStudy(String courseOfStudy) {
    this.courseOfStudy = courseOfStudy;
  }

  public boolean isPicked() {
    return picked;
  }

  public void setPicked(boolean picked) {
    this.picked = picked;
  }


}
