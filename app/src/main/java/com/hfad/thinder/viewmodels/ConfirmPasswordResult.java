package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

public class ConfirmPasswordResult {

  @Nullable
  private String errorMessage;

  @Nullable
  private ConfirmPasswordResultTypes success;

  public ConfirmPasswordResult(String errorMessage, String success) {
    this.errorMessage = errorMessage;
    this.success = ConfirmPasswordResultTypes.setResult(success);
  }

  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Nullable
  public ConfirmPasswordResultTypes getSuccess() {
    return this.success;
  }

}
