package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;

public class ForgotPasswordFormState {

  @Nullable
  private final Integer codeErrorMessage;

  @Nullable
  private final Integer newPasswordErrorMessage;

  @Nullable
  private final Integer newPasswordConfirmationErrorMessage;

  private final boolean isDataValid;

  public ForgotPasswordFormState(@Nullable Integer codeErrorMessage,
                                 @Nullable Integer newPasswordErrorMessage,
                                 @Nullable Integer newPasswordConfirmationErrorMessage) {
    this.codeErrorMessage = codeErrorMessage;
    this.newPasswordErrorMessage = newPasswordErrorMessage;
    this.newPasswordConfirmationErrorMessage = newPasswordConfirmationErrorMessage;
    this.isDataValid = codeErrorMessage == null && newPasswordErrorMessage == null &&
        newPasswordConfirmationErrorMessage == null;
  }

  @Nullable
  public Integer getCodeErrorMessage() {
    return codeErrorMessage;
  }

  @Nullable
  public Integer getNewPasswordErrorMessage() {
    return newPasswordErrorMessage;
  }

  @Nullable
  public Integer getNewPasswordConfirmationErrorMessage() {
    return newPasswordConfirmationErrorMessage;
  }

  public boolean isDataValid() {
    return isDataValid;
  }
}
