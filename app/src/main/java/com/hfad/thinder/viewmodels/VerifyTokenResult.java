package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

public class VerifyTokenResult {

  @Nullable
  private String errorMessage;

  @Nullable
  private ResultTypes success;

  public VerifyTokenResult(@Nullable String errorMessage, @Nullable ResultTypes success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }

  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Nullable
  public ResultTypes getSuccess() {
    return this.success;
  }

  public boolean isSuccess() {
    return this.success != null;
  }

}
