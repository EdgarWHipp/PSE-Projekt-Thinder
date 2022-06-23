package com.hfad.thinder.viewmodels.supervisor;

import androidx.annotation.Nullable;

public class EditProfileFormState {

  private final boolean isDataValid;
  @Nullable
  private final String academicDegreeErrorMessage;
  @Nullable
  private final String firstNameErrorMessage;
  @Nullable
  private final String lastNameErrorMessage;
  @Nullable
  private final String buildingErrorMessage;
  @Nullable
  private final String roomErrorMessage;
  @Nullable
  private final String phoneNumberErrorMessage;
  @Nullable
  private final String instituteErrorMessage;

  public EditProfileFormState(@Nullable String academicDegreeErrorMessage,
                              @Nullable String firstNameErrorMessage,
                              @Nullable String lastNameErrorMessage,
                              @Nullable String buildingErrorMessage,
                              @Nullable String roomErrorMessage,
                              @Nullable String phoneNumberErrorMessage,
                              @Nullable String instituteErrorMessage,
                              boolean isDataValid) {
    this.academicDegreeErrorMessage = academicDegreeErrorMessage;
    this.firstNameErrorMessage = firstNameErrorMessage;
    this.lastNameErrorMessage = lastNameErrorMessage;
    this.buildingErrorMessage = buildingErrorMessage;
    this.roomErrorMessage = roomErrorMessage;
    this.phoneNumberErrorMessage = phoneNumberErrorMessage;
    this.instituteErrorMessage = instituteErrorMessage;
    this.isDataValid = isDataValid;
  }

  public boolean isDataValid() {
    return isDataValid;
  }

  @Nullable
  public String getAcademicDegreeErrorMessage() {
    return academicDegreeErrorMessage;
  }

  @Nullable
  public String getFirstNameErrorMessage() {
    return firstNameErrorMessage;
  }

  @Nullable
  public String getLastNameErrorMessage() {
    return lastNameErrorMessage;
  }

  @Nullable
  public String getBuildingErrorMessage() {
    return buildingErrorMessage;
  }

  @Nullable
  public String getRoomErrorMessage() {
    return roomErrorMessage;
  }

  @Nullable
  public String getPhoneNumberErrorMessage() {
    return phoneNumberErrorMessage;
  }

  @Nullable
  public String getInstituteErrorMessage() {
    return instituteErrorMessage;
  }
}
