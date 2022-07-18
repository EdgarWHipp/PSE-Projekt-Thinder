package com.hfad.thinder.viewmodels.supervisor;

import androidx.annotation.Nullable;

public class EditProfileFormState {

  private final boolean isDataValid;

  @Nullable
  private final Integer firstNameErrorMessage;
  @Nullable
  private final Integer lastNameErrorMessage;
  @Nullable
  private final Integer buildingErrorMessage;
  @Nullable
  private final Integer roomErrorMessage;
  @Nullable
  private final Integer phoneNumberErrorMessage;
  @Nullable
  private final Integer instituteErrorMessage;

  public EditProfileFormState(@Nullable Integer firstNameErrorMessage,
                              @Nullable Integer lastNameErrorMessage,
                              @Nullable Integer buildingErrorMessage,
                              @Nullable Integer roomErrorMessage,
                              @Nullable Integer phoneNumberErrorMessage,
                              @Nullable Integer instituteErrorMessage
  ) {

    this.firstNameErrorMessage = firstNameErrorMessage;
    this.lastNameErrorMessage = lastNameErrorMessage;
    this.buildingErrorMessage = buildingErrorMessage;
    this.roomErrorMessage = roomErrorMessage;
    this.phoneNumberErrorMessage = phoneNumberErrorMessage;
    this.instituteErrorMessage = instituteErrorMessage;
    this.isDataValid = firstNameErrorMessage != null &&
        lastNameErrorMessage != null && buildingErrorMessage != null && roomErrorMessage != null &&
        phoneNumberErrorMessage != null && instituteErrorMessage != null;
  }

  public boolean isDataValid() {
    return isDataValid;
  }
  

  @Nullable
  public Integer getFirstNameErrorMessage() {
    return firstNameErrorMessage;
  }

  @Nullable
  public Integer getLastNameErrorMessage() {
    return lastNameErrorMessage;
  }

  @Nullable
  public Integer getBuildingErrorMessage() {
    return buildingErrorMessage;
  }

  @Nullable
  public Integer getRoomErrorMessage() {
    return roomErrorMessage;
  }

  @Nullable
  public Integer getPhoneNumberErrorMessage() {
    return phoneNumberErrorMessage;
  }

  @Nullable
  public Integer getInstituteErrorMessage() {
    return instituteErrorMessage;
  }
}
