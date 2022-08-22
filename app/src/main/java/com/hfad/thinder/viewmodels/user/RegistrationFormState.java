package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

/**
 * Class that represents state of validity of the data entered in the {@link RegistrationViewModel}.
 */
public class RegistrationFormState {

  @Nullable
  private final Integer passwordErrorMessageResourceId;

  @Nullable
  private final Integer passwordConfirmationErrorMessageResourceId;

  @Nullable
  private final Integer emailErrorMessageResourceId;

  @Nullable
  private final Integer firstNameErrorMessageResourceId;

  @Nullable
  private final Integer lastNameErrorMessageResourceId;


  private final boolean isValid;


  /**
   * @param emailErrorMessageResourceId      android string resource id for an error message, should be null if there is no error
   * @param firstNameErrorMessageResourceId  android string resource id for an error message, should be null if there is no error
   * @param lastNameErrorMessageResourceId   android string resource id for an error message, should be null if there is no error
   * @param passwordErrorMessage             android string resource id for an error message, should be null if there is no error
   * @param passwordConfirmationErrorMessage android string resource id for an error message, should be null if there is no error
   */
  public RegistrationFormState(@Nullable Integer emailErrorMessageResourceId,
                               @Nullable Integer firstNameErrorMessageResourceId,
                               @Nullable Integer lastNameErrorMessageResourceId,
                               @Nullable Integer passwordErrorMessage,
                               @Nullable Integer passwordConfirmationErrorMessage) {
    this.emailErrorMessageResourceId = emailErrorMessageResourceId;
    this.passwordErrorMessageResourceId = passwordErrorMessage;
    this.firstNameErrorMessageResourceId = firstNameErrorMessageResourceId;
    this.lastNameErrorMessageResourceId = lastNameErrorMessageResourceId;
    this.passwordConfirmationErrorMessageResourceId = passwordConfirmationErrorMessage;
    this.isValid =
        (emailErrorMessageResourceId == null && firstNameErrorMessageResourceId == null &&
            lastNameErrorMessageResourceId == null && passwordErrorMessage == null &&
            passwordConfirmationErrorMessage == null);
  }

  /**
   * @return true if all error messages are null, false otherwise
   */
  public boolean isDataValid() {
    return isValid;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getEmailErrorMessageResourceId() {
    return this.emailErrorMessageResourceId;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getFirstNameErrorMessageResourceId() {
    return this.firstNameErrorMessageResourceId;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getLastNameErrorMessageResourceId() {
    return this.lastNameErrorMessageResourceId;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getPasswordErrorMessageResourceId() {
    return passwordErrorMessageResourceId;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getPasswordConfirmationErrorMessageResourceId() {
    return this.passwordConfirmationErrorMessageResourceId;
  }


}
