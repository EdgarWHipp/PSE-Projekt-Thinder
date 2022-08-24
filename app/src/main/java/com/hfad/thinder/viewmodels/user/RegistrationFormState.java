package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

/**
 * Class that represents state of validity of the data entered in the {@link RegistrationViewModel}.
 */
public class RegistrationFormState {

  @Nullable
  private final Integer passwordErrorMessage;

  @Nullable
  private final Integer passwordConfirmationErrorMessage;

  @Nullable
  private final Integer emailErrorMessage;

  @Nullable
  private final Integer firstNameErrorMessage;

  @Nullable
  private final Integer lastNameErrorMessage;


  private final boolean isValid;


  /**
   * @param emailErrorMessage                android string resource id for an error message, should be null if there is no error
   * @param firstNameErrorMessage            android string resource id for an error message, should be null if there is no error
   * @param lastNameErrorMessage             android string resource id for an error message, should be null if there is no error
   * @param passwordErrorMessage             android string resource id for an error message, should be null if there is no error
   * @param passwordConfirmationErrorMessage android string resource id for an error message, should be null if there is no error
   */
  public RegistrationFormState(@Nullable Integer emailErrorMessage,
                               @Nullable Integer firstNameErrorMessage,
                               @Nullable Integer lastNameErrorMessage,
                               @Nullable Integer passwordErrorMessage,
                               @Nullable Integer passwordConfirmationErrorMessage) {
    this.emailErrorMessage = emailErrorMessage;
    this.passwordErrorMessage = passwordErrorMessage;
    this.firstNameErrorMessage = firstNameErrorMessage;
    this.lastNameErrorMessage = lastNameErrorMessage;
    this.passwordConfirmationErrorMessage = passwordConfirmationErrorMessage;
    this.isValid =
        (emailErrorMessage == null && firstNameErrorMessage == null &&
            lastNameErrorMessage == null && passwordErrorMessage == null &&
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
  public Integer getEmailErrorMessage() {
    return this.emailErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getFirstNameErrorMessage() {
    return this.firstNameErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getLastNameErrorMessage() {
    return this.lastNameErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getPasswordErrorMessage() {
    return passwordErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getPasswordConfirmationErrorMessage() {
    return this.passwordConfirmationErrorMessage;
  }


}
