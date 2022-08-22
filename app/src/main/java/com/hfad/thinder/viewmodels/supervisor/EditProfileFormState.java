package com.hfad.thinder.viewmodels.supervisor;

import androidx.annotation.Nullable;

/**
 * Class that represents state of validity of the data entered in the {@link EditProfileViewModel}.
 */
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

  /**
   * @param firstNameErrorMessage   android string resource id for an error message, should be null if there is no error
   * @param lastNameErrorMessage    android string resource id for an error message, should be null if there is no error
   * @param buildingErrorMessage    android string resource id for an error message, should be null if there is no error
   * @param roomErrorMessage        android string resource id for an error message, should be null if there is no error
   * @param phoneNumberErrorMessage android string resource id for an error message, should be null if there is no error
   * @param instituteErrorMessage   android string resource id for an error message, should be null if there is no error
   */
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
    this.isDataValid = firstNameErrorMessage == null &&
        lastNameErrorMessage == null && buildingErrorMessage == null && roomErrorMessage == null &&
        phoneNumberErrorMessage == null && instituteErrorMessage == null;
  }

  /**
   * @return true if all error messages are null
   */
  public boolean isDataValid() {
    return isDataValid;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getFirstNameErrorMessage() {
    return firstNameErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getLastNameErrorMessage() {
    return lastNameErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getBuildingErrorMessage() {
    return buildingErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getRoomErrorMessage() {
    return roomErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getPhoneNumberErrorMessage() {
    return phoneNumberErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getInstituteErrorMessage() {
    return instituteErrorMessage;
  }
}
