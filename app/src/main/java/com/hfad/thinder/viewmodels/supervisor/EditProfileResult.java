package com.hfad.thinder.viewmodels.supervisor;

import androidx.annotation.Nullable;

public class EditProfileResult {

  @Nullable
  private final String errorMessage;

  private final boolean isSuccessful;

  public EditProfileResult(@Nullable String errorMessage, boolean isSuccessful) {
    this.errorMessage = errorMessage;
    this.isSuccessful = isSuccessful;
  }

  @Nullable
  public String getErrorMessage() {
    return errorMessage;
  }

  public boolean isSuccessful() {
    return isSuccessful;
  }
}
