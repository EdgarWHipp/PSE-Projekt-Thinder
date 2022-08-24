package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.repository.SupervisorRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.student.StudentProfileFragment StudentProfileFragment}.
 */
public class EditProfileViewModel extends ViewModel {
  private static final Pattern PHONE_NUMBER_PATTERN =
      Pattern.compile("^\\+?(?:[0-9]⋅?){6,14}[0-9]$");
  private static final UserRepository userRepository = UserRepository.getInstance();
  private static final SupervisorRepository supervisorRepository =
      SupervisorRepository.getInstance();
  private static final DegreeRepository degreeRepository = DegreeRepository.getInstance();
  private Supervisor supervisor;
  private MutableLiveData<EditProfileFormState> formState;
  private MutableLiveData<ViewModelResult> safeResult;
  private MutableLiveData<ViewModelResult> deleteResult;
  private MutableLiveData<ArrayList<String>> academicTitles;
  private MutableLiveData<Integer> selectedAcademicTitlePosition;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> building;
  private MutableLiveData<String> room;
  private MutableLiveData<String> phoneNumber;
  private MutableLiveData<String> institute;


  /**
   * Use this method to save the changes to the profile data.
   */
  public void save() {
    String degree = academicTitles.getValue().get(selectedAcademicTitlePosition.getValue());
    String buildingString = building.getValue();
    String officeNumber = room.getValue();
    String institute = getInstitute().getValue();
    String phoneNumber = getPhoneNumber().getValue();
    String firstName = getFirstName().getValue();
    String lastName = getLastName().getValue();
    Result result =
        supervisorRepository.editProfilSupervisor(degree, officeNumber, buildingString, institute,
            phoneNumber, firstName,
            lastName);
    if (result.getSuccess()) {
      safeResult.setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.SUCCESSFUL));
      safeResult.setValue(null);
    } else {
      safeResult.setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }

  }

  /**
   * Use this method to delete the current profile.
   */
  public void delete() {
    Result result = userRepository.delete();
    if (result.getSuccess()) {
      deleteResult.setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.SUCCESSFUL));
    } else {
      deleteResult.setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  /**
   * Use this method to check whether the entered profile data is valid. This method should be called everytime the user edits the data in the ui.
   */
  public void profileDataChanged() {
    getFormState().setValue(
        new EditProfileFormState(checkFirstName(), checkLastName(), checkBuilding(), checkRoom(),
            checkPhoneNumber(), checkInstitute()));
  }

  //------------getter and setter -------------------------

  /**
   * @return the current {@link EditProfileFormState}
   */
  public MutableLiveData<EditProfileFormState> getFormState() {
    if (formState == null) {
      formState = new MutableLiveData<>();
    }
    return formState;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #save()} operation.
   */
  public MutableLiveData<ViewModelResult> getSafeResult() {
    if (safeResult == null) {
      safeResult = new MutableLiveData<>();
    }
    return safeResult;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #delete()} operation.
   */
  public MutableLiveData<ViewModelResult> getDeleteResult() {
    if (deleteResult == null) {
      deleteResult = new MutableLiveData<>();
    }
    return deleteResult;
  }

  /**
   * @return all existing academic titles.
   */
  public MutableLiveData<ArrayList<String>> getAcademicTitles() {
    if (academicTitles == null) {
      academicTitles = new MutableLiveData<>();
      loadAcademicTitles();
    }
    return academicTitles;
  }

  /**
   * @return the index of the currently selected academic title in {@link #getAcademicTitles()}.
   */
  public MutableLiveData<Integer> getSelectedAcademicTitlePosition() {
    if (selectedAcademicTitlePosition == null) {
      selectedAcademicTitlePosition = new MutableLiveData<>();
      loadSelectedAcademicTitlePosition();
    }
    return selectedAcademicTitlePosition;
  }

  /**
   * @param selectedAcademicTitlePosition the index of the currently selected academic title in {@link #getAcademicTitles()}.
   */
  public void setSelectedAcademicTitlePosition(
      MutableLiveData<Integer> selectedAcademicTitlePosition) {
    this.selectedAcademicTitlePosition = selectedAcademicTitlePosition;
  }

  /**
   * @return the supervisors first name
   */
  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<>();
      loadFirstName();
    }
    return firstName;
  }

  /**
   * @param firstName the supervisors first name
   */
  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the supervisors last name.
   */
  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<>();
      loadLastName();
    }
    return lastName;
  }

  /**
   * @param lastName the supervisors last name.
   */
  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the address of the supervisors office building.
   */
  public MutableLiveData<String> getBuilding() {
    if (building == null) {
      building = new MutableLiveData<>();
      loadBuilding();
    }
    return building;
  }

  /**
   * @param building the address of the supervisors office building.
   */
  public void setBuilding(MutableLiveData<String> building) {
    this.building = building;
  }

  /**
   * @return the location of the supervisors office within the office building.
   */
  public MutableLiveData<String> getRoom() {
    if (room == null) {
      room = new MutableLiveData<>();
      loadRoom();
    }
    return room;
  }

  /**
   * @param room the location of the supervisors office within the office building.
   */
  public void setRoom(MutableLiveData<String> room) {
    this.room = room;
  }

  /**
   * @return the supervisors phone number.
   */
  public MutableLiveData<String> getPhoneNumber() {
    if (phoneNumber == null) {
      phoneNumber = new MutableLiveData<>();
      loadPhoneNumber();
    }
    return phoneNumber;
  }

  /**
   * @param phoneNumber the supervisors phone number.
   */
  public void setPhoneNumber(MutableLiveData<String> phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the name of the supervisors institute.
   */
  public MutableLiveData<String> getInstitute() {
    if (institute == null) {
      institute = new MutableLiveData<>();
      loadInstitute();
    }
    return institute;
  }

  /**
   * @param institute the name of the supervisors institute.
   */
  public void setInstitute(MutableLiveData<String> institute) {
    this.institute = institute;
  }

  //-------------------private Methods--------------------------------------------------

  private void loadAcademicTitles() {
    academicTitles.setValue(degreeRepository.getAcademicTitles());
  }

  private void loadSelectedAcademicTitlePosition() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      String currentTile = supervisor.getAcademicDegree();
      Integer currentPosition = getAcademicTitles().getValue().indexOf(currentTile);
      selectedAcademicTitlePosition.setValue(currentPosition);
    }

  }

  private void loadFirstName() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      firstName.setValue(supervisor.getFirstName());
    }
  }

  private void loadLastName() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      lastName.setValue(supervisor.getLastName());
    }
  }

  private void loadBuilding() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      building.setValue(supervisor.getBuilding());
    }
  }

  private void loadPhoneNumber() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      phoneNumber.setValue(supervisor.getPhoneNumber());
    }
  }

  private void loadRoom() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      room.setValue(supervisor.getOfficeNumber());
    }

  }

  private void loadInstitute() {
    if (supervisor == null) {
      supervisor = (Supervisor) userRepository.getUser();
    }
    if (supervisor != null) {
      institute.setValue(supervisor.getInstitute());
    }
  }

  private Integer checkFirstName() {
    if (firstName.getValue() == null || firstName.getValue().equals("")) {
      return R.string.no_first_name_error;
    }
    return null;
  }

  private Integer checkLastName() {
    if (lastName.getValue() == null || lastName.getValue().equals("")) {
      return R.string.no_last_name_error;
    }
    return null;
  }

  private Integer checkBuilding() {
    if (building.getValue() == null || building.getValue().equals("")) {
      return R.string.no_building_error;
    }
    return null;
  }

  private Integer checkRoom() {
    if (room.getValue() == null || room.getValue().equals("")) {
      return R.string.no_room_error;
    }
    return null;
  }

  private Integer checkPhoneNumber() {
    if (phoneNumber.getValue() == null || phoneNumber.getValue().equals("")) {
      return R.string.no_phone_number_error;
    }
    if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber.getValue()).matches()) {
      return R.string.invalid_phone_number_error;
    }
    return null;
  }

  private Integer checkInstitute() {
    if (institute.getValue() == null || institute.getValue().equals("")) {
      return R.string.no_institute_error;
    }
    return null;
  }
}
