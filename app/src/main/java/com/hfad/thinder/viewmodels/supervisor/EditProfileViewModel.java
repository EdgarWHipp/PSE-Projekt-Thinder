package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.source.repository.UserRepository;
import java.util.ArrayList;

public class EditProfileViewModel {
  private final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<EditProfileFormState> formState;
  private MutableLiveData<EditProfileResult> safeResult;
  private MutableLiveData<EditProfileResult> deleteResult;
  private MutableLiveData<ArrayList<String>> academicTitles;
  private MutableLiveData<Integer> selectedAcademicTitlePosition;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> building;
  private MutableLiveData<String> room;
  private MutableLiveData<String> phoneNumber;
  private MutableLiveData<String> institute;


  public void safe() {
    //Todo: implement
  }

  public void delete() {
    //Todo: implement
  }

  public void profileDataChanged() {
    //Todo: implement
  }

  //------------getter and setter -------------------------


  public MutableLiveData<EditProfileFormState> getFormState() {
    if (formState == null) {
      formState = new MutableLiveData<>();
    }
    return formState;
  }

  public MutableLiveData<EditProfileResult> getSafeResult() {
    if (safeResult == null) {
      safeResult = new MutableLiveData<>();
    }
    return safeResult;
  }

  public MutableLiveData<EditProfileResult> getDeleteResult() {
    if (deleteResult == null) {
      deleteResult = new MutableLiveData<>();
    }
    return deleteResult;
  }

  public MutableLiveData<ArrayList<String>> getAcademicTitles() {
    loadAcademicTitles();
    if (academicTitles == null) {
      academicTitles = new MutableLiveData<>();

    }
    return academicTitles;
  }

  public MutableLiveData<Integer> getSelectedAcademicTitlePosition() {
    loadSelectedAcademicTitlePosition();
    if (selectedAcademicTitlePosition == null) {
      selectedAcademicTitlePosition = new MutableLiveData<>();
    }
    return selectedAcademicTitlePosition;
  }

  public void setSelectedAcademicTitlePosition(
      MutableLiveData<Integer> selectedAcademicTitlePosition) {
    this.selectedAcademicTitlePosition = selectedAcademicTitlePosition;
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

  public MutableLiveData<String> getBuilding() {
    if (building == null) {
      building = new MutableLiveData<>();
      loadBuilding();
    }
    return building;
  }

  public void setBuilding(MutableLiveData<String> building) {
    this.building = building;
  }

  public MutableLiveData<String> getRoom() {
    if (room == null) {
      room = new MutableLiveData<>();
      loadRoom();
    }
    return room;
  }

  public void setRoom(MutableLiveData<String> room) {
    this.room = room;
  }

  public MutableLiveData<String> getPhoneNumber() {
    if (phoneNumber == null) {
      phoneNumber = new MutableLiveData<>();
      loadPhoneNumber();
    }
    return phoneNumber;
  }

  public void setPhoneNumber(MutableLiveData<String> phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public MutableLiveData<String> getInstitute() {
    if (institute == null) {
      institute = new MutableLiveData<>();
      loadInstitute();
    }
    return institute;
  }

  public void setInstitute(MutableLiveData<String> institute) {
    this.institute = institute;
  }

  //-------------------private Methods--------------------------------------------------

  private void loadAcademicTitles() {
    //Todo: lade aus Repo
  }

  private void loadSelectedAcademicTitlePosition() {
    //Todo: lade aus Repo
  }

  private void loadFirstName() {
    //Todo: lade aus Repo
  }

  private void loadLastName() {
    //Todo: lade aus Repo
  }

  private void loadBuilding() {
    //Todo: lade aus Repo
  }

  private void loadPhoneNumber() {
    //Todo: lade aus Repo
  }

  private void loadRoom() {
    //Todo: lade aus Repo
  }

  private void loadInstitute() {
    //Todo: lade aus Repo
  }

}
