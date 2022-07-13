package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

//Enthält informationen darüber, ob der Login erfolgreich war
public class LoginResult {

  @Nullable
  private final String errorMessage;

  @Nullable
  private final RegistrationViewModel.ResultTypes resultTypes;


  public LoginResult(@Nullable String errorMessage, @Nullable
      RegistrationViewModel.ResultTypes resultTypes) {
    this.errorMessage = errorMessage;
    this.resultTypes = resultTypes;
  }


  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Nullable
  public RegistrationViewModel.ResultTypes getSuccess() {
    return this.resultTypes;
  }

  public boolean isSuccess() {
    return this.resultTypes != null;
  }
}
