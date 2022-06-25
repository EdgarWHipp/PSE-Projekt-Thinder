package com.hfad.thinder.data.source.result;

import androidx.annotation.Nullable;

public class Result {
  @Nullable
  private final String errorMessage;

  private final boolean success;

  public Result(@Nullable String errorMessage, boolean success) {
    this.errorMessage = errorMessage;
    this.success = success;
  }

  public Result(boolean success) {
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
