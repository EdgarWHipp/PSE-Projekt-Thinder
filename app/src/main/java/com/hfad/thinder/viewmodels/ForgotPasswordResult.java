package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

public class ForgotPasswordResult {

  @Nullable
  private final String errorMessage;

  private final boolean success;

  public ForgotPasswordResult(@Nullable String errorMessage, boolean success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }

  @Nullable
  public String getErrorMessage() {
    return errorMessage;
  }


  public boolean isSuccess() {
    return success;
  }
}
