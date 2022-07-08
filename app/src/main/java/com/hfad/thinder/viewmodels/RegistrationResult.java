package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;


public class RegistrationResult {

  @Nullable
  private final Integer errorMessageRessourceID;

  private final boolean isSuccess;

  public RegistrationResult(@Nullable Integer errorMessage, boolean isSuccess) {
    this.errorMessageRessourceID = errorMessage;
    this.isSuccess = isSuccess;
  }

  @Nullable
  public Integer getErrorMessage() {
    return this.errorMessageRessourceID;
  }

  public boolean isSuccess() {
    return this.isSuccess;
  }
}
