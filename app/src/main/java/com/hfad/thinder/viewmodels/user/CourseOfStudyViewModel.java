package com.hfad.thinder.viewmodels.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.ui.CourseOfStudyItem;

public class CourseOfStudyViewModel extends ViewModel {
  private MutableLiveData<CourseOfStudyItem> courseOfStudyItems;

  public MutableLiveData<CourseOfStudyItem> getCourseOfStudyItems() {
    return courseOfStudyItems;
  }

  public void safe() {
    //Todo: implement
  }
}
