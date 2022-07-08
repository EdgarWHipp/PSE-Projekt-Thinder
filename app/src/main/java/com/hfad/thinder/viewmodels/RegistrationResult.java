package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;


public class RegistrationResult {

  @Nullable
  private final String errorMessage;


  private final boolean isSuccess;

  public RegistrationResult(@Nullable String errorMessage, boolean isSuccess) {
    this.errorMessage = errorMessage;
    this.isSuccess = isSuccess;
  }

  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  public boolean isSuccess() {
    return this.isSuccess;
  }
}
