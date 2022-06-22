package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

// Klasse gibt an, ob die Eingabe bei der Registrierung einem bestimmten format entspricht.
public class RegistrationFormState {

  @Nullable
  private final String passwordErrorMessage;

  @Nullable
  private final String passwordConfirmationErrorMessage;

  @Nullable
  private final String emailErrorMessage;

  @Nullable
  private final String firstNameErrorMessage;

  @Nullable
  private final String lastNameErrorMessage;


  private final boolean isValid;


  public RegistrationFormState(@Nullable String emailErrorMessage,
                               @Nullable String firstNameErrorMessage,
                               @Nullable String lastNameErrorMessage,
                               @Nullable String passwordErrorMessage,
                               @Nullable String passwordConfirmationErrorMessage) {
    this.emailErrorMessage = emailErrorMessage;
    this.passwordErrorMessage = passwordErrorMessage;
    this.firstNameErrorMessage = firstNameErrorMessage;
    this.lastNameErrorMessage = lastNameErrorMessage;
    this.passwordConfirmationErrorMessage = passwordConfirmationErrorMessage;
    this.isValid = (emailErrorMessage == null && firstNameErrorMessage == null &&
        lastNameErrorMessage == null && passwordErrorMessage == null &&
        passwordConfirmationErrorMessage == null);
  }


  public boolean isDataValid() {
    return isValid;
  }

  @Nullable
  public String getEmailErrorMessage() {
    return this.emailErrorMessage;
  }

  @Nullable
  public String getFirstNameErrorMessage() {
    return this.firstNameErrorMessage;
  }

  @Nullable
  public String getLastNameErrorMessage() {
    return this.lastNameErrorMessage;
  }

  @Nullable
  public String getPasswordErrorMessage() {
    return passwordErrorMessage;
  }

  @Nullable
  public String getPasswordConfirmationErrorMessage() {
    return this.passwordConfirmationErrorMessage;
  }


}
