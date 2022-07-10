package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

//Enthält informationen darüber, ob der Login erfolgreich war
public class LoginResult {

  @Nullable
  private final String errorMessage;

  @Nullable
  private final Success success;


  public LoginResult(@Nullable String errorMessage, @Nullable Success success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }


  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Nullable
  public Success getSuccess() {
    return this.success;
  }

  public boolean isSuccess() {
    return this.success != null;
  }
}
