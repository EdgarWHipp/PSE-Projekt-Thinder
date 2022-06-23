package com.hfad.thinder.viewmodels.student;

import androidx.annotation.Nullable;

public class EditProfileFormState {
  @Nullable
  private final String firstNameErrorMessage;

  @Nullable
  private final String lastNameErrorMessage;

  private final boolean isValid;

  public EditProfileFormState(String firstNameErrorMessage, @Nullable String lastNameErrorMessage) {
    this.firstNameErrorMessage = firstNameErrorMessage;
    this.lastNameErrorMessage = lastNameErrorMessage;
    this.isValid = (firstNameErrorMessage == null && lastNameErrorMessage == null);
  }

  public String getFirstNameErrorMessage() {
    return this.firstNameErrorMessage;
  }

  public String getLastNameErrorMessage() {
    return this.lastNameErrorMessage;
  }

  public boolean isDataValid() {
    return this.isValid;
  }
}
