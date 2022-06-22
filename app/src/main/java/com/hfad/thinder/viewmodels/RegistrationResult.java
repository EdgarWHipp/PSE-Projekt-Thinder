package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;


public class RegistrationResult {

  @Nullable
  private final String errorMessage;

  private final boolean success;

  public RegistrationResult(@Nullable String errorMessage, boolean success) {
    this.errorMessage = errorMessage;
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
