package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

//Enthält informationen darüber, ob der Login erfolgreich war
public class LoginResult {

  @Nullable
  private final String errorMessage;

  private final boolean success;

  public LoginResult(@Nullable String errorMessage, boolean success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }

  public LoginResult(boolean success) {
    this.errorMessage = null;
    this.success = success;
  }

  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  public boolean getSuccess() {
    return this.success;
  }
}
