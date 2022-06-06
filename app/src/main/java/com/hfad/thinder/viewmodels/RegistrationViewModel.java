package com.hfad.thinder.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationViewModel extends ViewModel {

  private MutableLiveData<List<String>> universities;
  private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
  private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
  //private Repository registrationRepository = new Repository(); //Todo: Richtiges Repository fehlt
  private static final Pattern PASSWORD_PATTERN = Pattern.compile("");//Todo: geeignetes Pattern überlegen

  private ArrayList<String> testUniversities;

  // Position of the item selected by the spinner, i.e. the entry of the university
  private MutableLiveData<Integer> selectedItemPosition;
  private MutableLiveData<ArrayList<String>> universities;

  private MutableLiveData<String> email;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> password;
  private MutableLiveData<String> passwordConfirmation;
  private MutableLiveData<String> errorMessage;

  public MutableLiveData<Integer> getSelectedItemPosition() {
    if (selectedItemPosition == null) {
      selectedItemPosition = new MutableLiveData<Integer>();
    }
    return selectedItemPosition;
  }

  public void setSelectedItemPosition(MutableLiveData<Integer> selectedItemPosition) {
    this.selectedItemPosition = selectedItemPosition;
  }

  public void setUniversities(MutableLiveData<ArrayList<String>> universities) {
    this.universities = universities;
  }

  public MutableLiveData<String> getEmail() {
    if (email == null) {
      email = new MutableLiveData<String>();
    }
    return email;
  }

  public void setEmail(MutableLiveData<String> email) {
    this.email = email;
  }

  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<String>();
    }
    return firstName;
  }

  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<String>();
    }
    return lastName;
  }

  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  public MutableLiveData<String> getPassword() {
    if (password == null) {
      password = new MutableLiveData<String>();
    }
    return password;
  }

  public void setPassword(MutableLiveData<String> password) {
    this.password = password;
  }

  public MutableLiveData<String> getPasswordConfirmation() {
    if (passwordConfirmation == null) {
      passwordConfirmation = new MutableLiveData<String>();
    }
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(MutableLiveData<String> passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  public MutableLiveData<String> getErrorMessage() {
    if (errorMessage == null) {
      errorMessage = new MutableLiveData<String>();
    }
    return errorMessage;
  }

  public void setErrorMessage(MutableLiveData<String> errorMessage) {
    this.errorMessage = errorMessage;
  }


  public void register(String email, String firstName, String lastName, String password, String university) {
    // Result result = registrationRepository.register(email, firstName, lastName, password, university);
    //Todo: result aus Repo in registrationResult verwalten
  }

  public MutableLiveData<List<String>> getUniversities() {
    public MutableLiveData<ArrayList<String>> getUniversities() {
      if (testUniversities == null) {
        testUniversities = new ArrayList<String>();
        testUniversities.add("KIT");
        testUniversities.add("TUM");
      }
      if (universities == null) {
        this.universities = new MutableLiveData<List<String>>();
        universities = new MutableLiveData<ArrayList<String>>();
        universities.setValue(testUniversities);
        loadUniversities();
      }
      return universities;
    }
    return true;
  }

  // This function is called when the user presses the register button
  public void register() {
    errorMessage.setValue(email.getValue() + " " + firstName.getValue() + " " + lastName.getValue() + " " + password.getValue() + " " + passwordConfirmation.getValue() + " " + universities.getValue().get(selectedItemPosition.getValue()));
  }
}
