package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import java.util.ArrayList;

public interface CoursesOfStudyPicker {
  /**
   * Changes entry in CourseOfStudyList according to the selection made by the user
   *
   * @param changedCourseOfStudy points to entry in the list
   * @param selection            user's selection
   */
  void makeCourseOfStudySelection(String changedCourseOfStudy, boolean selection);

  ArrayList<CourseOfStudyItem> getElements();

  /**
   * @return a {@link MutableLiveData} object containing an {@link ArrayList} of
   * {@link CourseOfStudyItem CourseOfStudyItems}, that includes all courses of study received from
   * {@link DegreeRepository#fetchAllCoursesOfStudy()} method.
   */
  MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList();
}
