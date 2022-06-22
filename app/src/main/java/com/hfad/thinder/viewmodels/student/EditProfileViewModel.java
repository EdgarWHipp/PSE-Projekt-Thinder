package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.source.repository.StudentRepository;
import java.util.ArrayList;

public class EditProfileViewModel {
  private final StudentRepository editProfileRepository = StudentRepository.getInstance();
  private EditProfileFormState formState;
  private EditProfileResult safeResult;
  private EditProfileResult deleteResult;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;

  private MutableLiveData<ArrayList<String>> diplomas;
  private MutableLiveData<Integer> selectedDiplomaPosition1;
  private MutableLiveData<Integer> selectedDiplomaPosition2;

  private MutableLiveData<ArrayList<String>> degrees;
  private MutableLiveData<Integer> selectedDegreePosition1;
  private MutableLiveData<Integer> selectedDegreePosition2;


  public void safe() {
    //Todo: implement
  }

  public void delete() {
    //Todo: implement
  }

  public void profileDataChanged() {
    //Todo: implement
  }


  //----------------getter and setter-----------------------------------------
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

  public MutableLiveData<ArrayList<String>> getDiplomas() {
    if (diplomas == null) {
      diplomas = new MutableLiveData<>();
      loadDiplomas();
    }
    return diplomas;
  }

  public MutableLiveData<Integer> getSelectedDiplomaPosition1() {
    if (selectedDiplomaPosition1 == null) {
      selectedDiplomaPosition1 = new MutableLiveData<>();
    }
    return selectedDiplomaPosition1;
  }

  public void setSelectedDiplomaPosition1(MutableLiveData<Integer> selectedDiplomaPosition1) {
    this.selectedDiplomaPosition1 = selectedDiplomaPosition1;
  }

  public MutableLiveData<Integer> getSelectedDiplomaPosition2() {
    if (selectedDiplomaPosition2 == null) {
      selectedDiplomaPosition2 = new MutableLiveData<>();
    }
    return selectedDiplomaPosition2;
  }

  public void setSelectedDiplomaPosition2(MutableLiveData<Integer> selectedDiplomaPosition2) {
    this.selectedDiplomaPosition2 = selectedDiplomaPosition2;
  }

  public MutableLiveData<ArrayList<String>> getDegrees() {
    if (degrees == null) {
      degrees = new MutableLiveData<>();
      loadDegrees();
    }
    return degrees;
  }

  public MutableLiveData<Integer> getSelectedDegreePosition1() {
    if (selectedDegreePosition1 == null) {
      selectedDegreePosition1 = new MutableLiveData<>();
    }
    return selectedDegreePosition1;
  }

  public void setSelectedDegreePosition1(MutableLiveData<Integer> selectedDegreePosition1) {
    this.selectedDegreePosition1 = selectedDegreePosition1;
  }

  public MutableLiveData<Integer> getSelectedDegreePosition2() {
    if (selectedDegreePosition2 == null) {
      selectedDegreePosition2 = new MutableLiveData<>();
    }
    return selectedDegreePosition2;
  }

  public void setSelectedDegreePosition2(MutableLiveData<Integer> selectedDegreePosition2) {
    this.selectedDegreePosition2 = selectedDegreePosition2;
  }


  //-------------------private methods-------------------------
  private void loadDegrees() {
    //Todo: lade Studiengänge aus dem Repository
  }

  private void loadDiplomas() {
    //Todo: lade Abschlüsse aus dem Repository
  }

  private void loadFirstName() {
    //Todo: lade Ersten Namen aus dem Repository
  }

  private void loadLastName() {
    //Todo: lade Nachnamen aud dem Repository
  }


}
