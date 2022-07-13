package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

public class VerifyTokenResult {

  @Nullable
  private String errorMessage;

  @Nullable
  private RegistrationViewModel.ResultTypes success;

  public VerifyTokenResult(@Nullable String errorMessage, @Nullable
      RegistrationViewModel.ResultTypes success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }

  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Nullable
  public RegistrationViewModel.ResultTypes getSuccess() {
    return this.success;
  }

  public boolean isSuccess() {
    return this.success != null;
  }

}
