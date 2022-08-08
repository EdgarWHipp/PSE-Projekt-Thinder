package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EditProfileViewModel extends ViewModel implements CoursesOfStudyPicker {
  private final static UserRepository userRepository = UserRepository.getInstance();
  private final static DegreeRepository degreeRepository = DegreeRepository.getInstance();
  private final static StudentRepository studentRepository = StudentRepository.getInstance();
  private User user;
  // list of all courses of study from the backend combined with the user selection
  private MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList;
  private MutableLiveData<EditProfileFormState> formState;
  private MutableLiveData<ViewModelResult> safeResult;
  private MutableLiveData<ViewModelResult> deleteResult;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  // from user selectedCoursesOfStudy
  private MutableLiveData<String> selectedCoursesOfStudy;


  public void save() {
    Result result =
            studentRepository.editProfileStudent(getSelectedSet(), firstName.getValue(), lastName.getValue());
    if (result.getSuccess()) {
      getSafeResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getSafeResult().setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  public void delete() {
    Result result = userRepository.delete();
    if (result.getSuccess()) {
      getDeleteResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getDeleteResult().setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  public void profileDataChanged() {
    getFormState().setValue(
        new EditProfileFormState(checkFirstName(), checkLastName(), checkCoursesOfStudy()));
  }

  @Override
  public void makeCourseOfStudySelection(String changedCourseOfStudy, boolean selection) {

    ArrayList<CourseOfStudyItem> copy = getCoursesOfStudyList().getValue();
    for(CourseOfStudyItem courseOfStudyItem : copy){
      if(courseOfStudyItem.getCourseOfStudy().equals(changedCourseOfStudy)){
        courseOfStudyItem.setPicked(selection);
      }
    }
    getCoursesOfStudyList().postValue(copy);
  }


  @Override
  public ArrayList<CourseOfStudyItem> getElements() {
    return getCoursesOfStudyList().getValue();
  }

  //----------------getter and setter-----------------------------------------


  public MutableLiveData<EditProfileFormState> getFormState() {
    if (formState == null) {
      formState = new MediatorLiveData<>();
    }
    return formState;
  }

  public MutableLiveData<ViewModelResult> getSafeResult() {
    if (safeResult == null) {
      safeResult = new MutableLiveData<>();
    }
    return safeResult;
  }

  public MutableLiveData<ViewModelResult> getDeleteResult() {
    if (deleteResult == null) {
      deleteResult = new MutableLiveData<>();
    }
    return deleteResult;
  }

  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<>();
      loadFirstName();
    }
    return firstName;
  }

  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<>();
      loadLastName();
    }
    return lastName;
  }

  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  public MutableLiveData<String> getSelectedCoursesOfStudy() {

    if (selectedCoursesOfStudy == null) {
      loadCoursesOfStudy();
    } else {
      updateSelectedCoursesOfStudy();
    }
    return selectedCoursesOfStudy;
  }

  public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList() {
    if(coursesOfStudyList == null){
      coursesOfStudyList = new MutableLiveData<>();
      loadCoursesOfStudy();
    }
    return coursesOfStudyList;
  }

  public void setCoursesOfStudyList(MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList) {
    this.coursesOfStudyList = coursesOfStudyList;
  }


  //-------------------private methods-------------------------


  private void loadCoursesOfStudy() {
    ArrayList<String> allDegrees = degreeRepository.getAllDegrees();
    ArrayList<String> selectedDegrees = studentRepository.getSelectedDegrees();
    ArrayList<CourseOfStudyItem> courseOfStudyItems = new ArrayList<>();
    for (String degree : allDegrees) {
      if (selectedDegrees.contains(degree)) {
        courseOfStudyItems.add(new CourseOfStudyItem(degree, true));
        selectedDegrees.add(degree);
      }else{
        courseOfStudyItems.add(new CourseOfStudyItem(degree, false));
      }
    }
    coursesOfStudyList = new MutableLiveData<>();
    coursesOfStudyList.setValue(courseOfStudyItems);

    String degreesAsString = String.join(", ", selectedDegrees);

    selectedCoursesOfStudy = new MutableLiveData<>();
    selectedCoursesOfStudy.setValue(degreesAsString);
  }

  private void loadFirstName() {
    if (user == null) {
      user = userRepository.getUser();
    }
    firstName = new MutableLiveData<>(user.getFirstName());
  }

  private void loadLastName() {
    if (user == null) {
      user = userRepository.getUser();
    }
    lastName = new MutableLiveData<>(user.getLastName());
  }

  private Integer checkLastName() {
    if (lastName.getValue() == null || lastName.getValue().equals("")) {
      return R.string.no_first_name_error;
    }
    return null;
  }

  private Integer checkFirstName() {
    if (lastName.getValue() == null || lastName.getValue().equals("")) {
      return R.string.no_last_name_error;
    }
    return null;
  }

  private Integer checkCoursesOfStudy() {
    if (selectedCoursesOfStudy.getValue() == null || selectedCoursesOfStudy.getValue().equals("")) {
      return R.string.no_courses_of_study_error_message;
    }
    return null;
  }

  private ArrayList<Degree> getSelectedSet() {
    ArrayList<Degree> degrees = new ArrayList<>();
    for (CourseOfStudyItem courseOfStudyItem : getCoursesOfStudyList().getValue()) {
      if (courseOfStudyItem.isPicked()) {
        degrees.add(new Degree(courseOfStudyItem.getCourseOfStudy()));
      }
    }
    return degrees;
  }

  private void updateSelectedCoursesOfStudy() {
    List<String> selectedCourses = new ArrayList<>();
    for (CourseOfStudyItem courseOfStudyItem: getCoursesOfStudyList().getValue()) {
      if (courseOfStudyItem.isPicked()) {
        selectedCourses.add(courseOfStudyItem.getCourseOfStudy());
      }
    }
    selectedCoursesOfStudy.setValue(String.join(", ", selectedCourses));
  }

}
