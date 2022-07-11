package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

public class ForgotPasswordFormState {

  @Nullable
  private final String codeErrorMessage;

  @Nullable
  private final String newPasswordErrorMessage;

  @Nullable
  private final String newPasswordConfirmationErrorMessage;

  private final boolean isDataValid;

  public ForgotPasswordFormState(@Nullable String codeErrorMessage,
                                 @Nullable String newPasswordErrorMessage,
                                 @Nullable String newPasswordConfirmationErrorMessage) {
    this.codeErrorMessage = codeErrorMessage;
    this.newPasswordErrorMessage = newPasswordErrorMessage;
    this.newPasswordConfirmationErrorMessage = newPasswordConfirmationErrorMessage;
    this.isDataValid = codeErrorMessage == null && newPasswordErrorMessage == null &&
        newPasswordConfirmationErrorMessage == null;
  }

  @Nullable
  public String getCodeErrorMessage() {
    return codeErrorMessage;
  }

  @Nullable
  public String getNewPasswordErrorMessage() {
    return newPasswordErrorMessage;
  }

  @Nullable
  public String getNewPasswordConfirmationErrorMessage() {
    return newPasswordConfirmationErrorMessage;
  }

  public boolean isDataValid() {
    return isDataValid;
  }
}
