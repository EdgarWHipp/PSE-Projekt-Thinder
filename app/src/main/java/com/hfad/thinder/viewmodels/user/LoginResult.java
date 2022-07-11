package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;
import com.hfad.thinder.viewmodels.ResultTypes;

//Enthält informationen darüber, ob der Login erfolgreich war
public class LoginResult {

  @Nullable
  private final String errorMessage;

  @Nullable
  private final ResultTypes resultTypes;


  public LoginResult(@Nullable String errorMessage, @Nullable ResultTypes resultTypes) {
    this.errorMessage = errorMessage;
    this.resultTypes = resultTypes;
  }


  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  @Nullable
  public ResultTypes getSuccess() {
    return this.resultTypes;
  }

  public boolean isSuccess() {
    return this.resultTypes != null;
  }
}
