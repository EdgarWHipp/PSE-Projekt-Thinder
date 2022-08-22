package com.hfad.thinder.viewmodels.user;

import androidx.annotation.Nullable;


/**
 * Class that represents state of validity of the data entered in the {@link ForgotPasswordViewModel}.
 */
public class ForgotPasswordFormState {

  @Nullable
  private final Integer codeErrorMessage;

  @Nullable
  private final Integer newPasswordErrorMessage;

  @Nullable
  private final Integer newPasswordConfirmationErrorMessage;

  private final boolean isDataValid;

  /**
   * @param codeErrorMessage                    android string resource id for an error message, should be null if there is no error
   * @param newPasswordErrorMessage             android string resource id for an error message, should be null if there is no error
   * @param newPasswordConfirmationErrorMessage android string resource id for an error message, should be null if there is no error
   */
  public ForgotPasswordFormState(@Nullable Integer codeErrorMessage,
                                 @Nullable Integer newPasswordErrorMessage,
                                 @Nullable Integer newPasswordConfirmationErrorMessage) {
    this.codeErrorMessage = codeErrorMessage;
    this.newPasswordErrorMessage = newPasswordErrorMessage;
    this.newPasswordConfirmationErrorMessage = newPasswordConfirmationErrorMessage;
    this.isDataValid = codeErrorMessage == null && newPasswordErrorMessage == null &&
        newPasswordConfirmationErrorMessage == null;
  }

  /**
   * @return true if all error messages are null, false otherwise
   */
  public boolean isDataValid() {
    return isDataValid;
  }
}
