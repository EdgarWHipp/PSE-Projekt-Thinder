package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public interface CoursesOfStudyPicker {
    /**
     * Changes entry in CourseOfStudyList according to the selection made by the user
     * @param position points to entry in the list
     * @param selection user's selection
     */
    public void makeCourseOfStudySelection(int position, boolean selection);
    public ArrayList<CourseOfStudyItem> getElements();
    public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList();
}
