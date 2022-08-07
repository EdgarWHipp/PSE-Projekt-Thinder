package com.hfad.thinder.viewmodels;

public class CourseOfStudyItem {
    private String courseOfStudy;
    private boolean picked = false;

    public CourseOfStudyItem(String courseOfStudy, boolean picked) {
        this.courseOfStudy = courseOfStudy;
        this.picked = picked;
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
