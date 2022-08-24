package com.hfad.thinder.viewmodels.student;


import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Student;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.student.StudentProfileFragment StudentProgileFragment}.
 */
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


  /**
   * This method initiates the saving of the edited profile data. In order to do this, the selected
   * courses of study, the first name and last name values must be set.
   */
  public void save() {
    Result result =
        studentRepository.editProfileStudent(getSelectedSet(), firstName.getValue(),
            lastName.getValue());
    if (result.getSuccess()) {
      getSafeResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      getSafeResult().setValue(null);
    } else {
      getSafeResult().setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  /**
   * This method initiates the deletion of all data belonging to the corresponding user.
   */
  public void delete() {
    Result result = userRepository.delete();
    if (result.getSuccess()) {
      getDeleteResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getDeleteResult().setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  /**
   * This method checks if the data given by the user is valid and updates the {@link EditProfileFormState} accordingly.
   */
  public void profileDataChanged() {
    getFormState().setValue(
        new EditProfileFormState(checkFirstName(), checkLastName(), checkCoursesOfStudy()));
  }

  @Override
  public void makeCourseOfStudySelection(String changedCourseOfStudy, boolean selection) {

    ArrayList<CourseOfStudyItem> copy = getCoursesOfStudyList().getValue();
    for (CourseOfStudyItem courseOfStudyItem : copy) {
      if (courseOfStudyItem.getCourseOfStudy().equals(changedCourseOfStudy)) {
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


  /**
   * @return a {@link MutableLiveData} object containing the current {@link EditProfileFormState}.
   */
  public MutableLiveData<EditProfileFormState> getFormState() {
    if (formState == null) {
      formState = new MediatorLiveData<>();
    }
    return formState;
  }

  /**
   * @return a {@link MutableLiveData} object containing the {@link ViewModelResult} of the {@link #save()} method.
   */
  public MutableLiveData<ViewModelResult> getSafeResult() {
    if (safeResult == null) {
      safeResult = new MutableLiveData<>();
    }
    return safeResult;
  }

  /**
   * @return a {@link MutableLiveData} object containing the {@link ViewModelResult} of the {@link #delete()} method.
   */
  public MutableLiveData<ViewModelResult> getDeleteResult() {
    if (deleteResult == null) {
      deleteResult = new MutableLiveData<>();
    }
    return deleteResult;
  }

  /**
   * @return the {@link MutableLiveData} object containing the first name of the user as a {@link String}.
   */
  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<>();
      loadFirstName();
    }
    return firstName;
  }

  /**
   * @param firstName a {@link MutableLiveData} object containing the value of the users first name as a {@link String}.
   */
  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the {@link MutableLiveData} object containing the last name of the user as a {@link String}.
   */
  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<>();
      loadLastName();
    }
    return lastName;
  }

  /**
   * @param lastName a {@link MutableLiveData} object containing the users last name as a {@link String}.
   */
  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  /**
   * @return a {@link MutableLiveData} object containing all currently selected courses of study as a {@link String}.
   */
  public MutableLiveData<String> getSelectedCoursesOfStudy() {

    if (selectedCoursesOfStudy == null) {
      loadCoursesOfStudy();
    } else {
      updateSelectedCoursesOfStudy();
    }
    return selectedCoursesOfStudy;
  }

  public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList() {
    if (coursesOfStudyList == null) {
      coursesOfStudyList = new MutableLiveData<>();
      loadCoursesOfStudy();
    }
    return coursesOfStudyList;
  }


  //-------------------private methods-------------------------


  private void loadCoursesOfStudy() {
    List<Degree> allDegrees =
        degreeRepository.fetchAllCoursesOfStudy().stream().collect(Collectors.toList());
    List<String> selectedDegrees =
        ((Student) userRepository.getUser()).getDegrees().stream().map(e -> e.getDegree()).collect(
            Collectors.toList());

    ArrayList<CourseOfStudyItem> courseOfStudyItems = new ArrayList<>();
    for (Degree degree : allDegrees) {
      if (selectedDegrees.contains(degree.getDegree())) {
        courseOfStudyItems.add(new CourseOfStudyItem(degree.getDegree(), degree.getId(), true));
        selectedDegrees.add(degree.getDegree());
      } else {
        courseOfStudyItems.add(new CourseOfStudyItem(degree.getDegree(), degree.getId(), false));
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
        degrees.add(new Degree(courseOfStudyItem.getCourseOfStudy(), courseOfStudyItem.getUuid()));
      }
    }
    return degrees;
  }

  private void updateSelectedCoursesOfStudy() {
    List<String> selectedCourses = new ArrayList<>();
    for (CourseOfStudyItem courseOfStudyItem : getCoursesOfStudyList().getValue()) {
      if (courseOfStudyItem.isPicked()) {
        selectedCourses.add(courseOfStudyItem.getCourseOfStudy());
      }
    }
    selectedCoursesOfStudy.setValue(String.join(", ", selectedCourses));
  }

}
