package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

// Klasse gibt an, ob die Eingabe bei der Registrierung einem bestimmten format entspricht.
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


  public boolean isDataValid() {
    return isValid;
  }

  @Nullable
  public Integer getEmailErrorMessageResourceId() {
    return this.emailErrorMessageResourceId;
  }

  @Nullable
  public Integer getFirstNameErrorMessageResourceId() {
    return this.firstNameErrorMessageResourceId;
  }

  @Nullable
  public Integer getLastNameErrorMessageResourceId() {
    return this.lastNameErrorMessageResourceId;
  }

  @Nullable
  public Integer getPasswordErrorMessageResourceId() {
    return passwordErrorMessageResourceId;
  }

  @Nullable
  public Integer getPasswordConfirmationErrorMessageResourceId() {
    return this.passwordConfirmationErrorMessageResourceId;
  }


}
