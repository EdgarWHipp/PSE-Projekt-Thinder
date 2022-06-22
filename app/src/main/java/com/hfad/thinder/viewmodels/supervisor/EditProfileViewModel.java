package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.source.repository.UserRepository;

public class EditProfileViewModel {
  private final UserRepository editProfileRepository = UserRepository.getInstance();
  private EditProfileFormState formState;
  private EditProfileResult safeResult;
  private EditProfileResult deleteResult;
  private MutableLiveData<String> academicTitle;
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

  public MutableLiveData<String> getAcademicTitle() {
    if (academicTitle == null) {
      academicTitle = new MutableLiveData<>();
      loadAcademicDegree();
    }
    return academicTitle;
  }

  public void setAcademicTitle(MutableLiveData<String> academicTitle) {
    this.academicTitle = academicTitle;
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

  private void loadAcademicDegree() {
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
