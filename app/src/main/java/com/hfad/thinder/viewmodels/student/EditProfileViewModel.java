package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.HashSet;
import java.util.Set;


public class EditProfileViewModel extends ViewModel {
  private final UserRepository userRepository = UserRepository.getInstance();
  private User user;
  private Set<Degree> degrees;//Todo: updaten nachdem aus dem Courses of Study zurück.
  private MutableLiveData<EditProfileFormState> formState;
  private MutableLiveData<ViewModelResult> safeResult;
  private MutableLiveData<ViewModelResult> deleteResult;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> coursesOfStudy;


  public void save() {
    Result result =
        userRepository.editProfileStudent(degrees, firstName.getValue(), lastName.getValue());
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

  public MutableLiveData<String> getCoursesOfStudy() {
    if (coursesOfStudy == null) {
      loadCoursesOfStudy();
    }

    return coursesOfStudy;
  }


  //-------------------private methods-------------------------


  private void loadCoursesOfStudy() {
    if (user == null) {
      user = userRepository.getUser();
    }

    degrees = new HashSet<>();
    degrees.add(new Degree("Informatik", "Bachelor"));
    degrees.add(new Degree("Mathematik", "Bachelor"));//Todo: hole seine Degrees aus Model

    String degreesAsString = "";
    for (Degree degree : degrees) {
      degreesAsString = degreesAsString + degree.getName() + " ";
    }
    coursesOfStudy = new MutableLiveData<>();
    coursesOfStudy.setValue(degreesAsString);
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
    if (coursesOfStudy.getValue() == null || coursesOfStudy.getValue().equals("")) {
      return R.string.no_courses_of_study_error_message;
    }
    return null;
  }

}
