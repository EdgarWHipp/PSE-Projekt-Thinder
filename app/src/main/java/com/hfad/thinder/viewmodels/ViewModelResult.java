package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;
import com.hfad.thinder.data.source.result.Result;

/**
 * A class representing the result of call to the model. Contains a {@link ViewModelResultTypes ViewModelResultType}
 * and in case of the call returns an error a corresponding error message.
 */
public class ViewModelResult {

  @Nullable
  private final String errorMessage;

  @Nullable
  private final ViewModelResultTypes viewModelResultType;

  /**
   * @param errorMessage        an error message
   * @param viewModelResultType a {@link ViewModelResultTypes ViewModelResultType}
   */
  public ViewModelResult(@Nullable String errorMessage, @Nullable
      ViewModelResultTypes viewModelResultType) {
    this.errorMessage = errorMessage;
    this.viewModelResultType = viewModelResultType;
  }

  /**
   * @return the error message produced by the model call. Returns null if no error occurred.
   */
  @Nullable
  public String getErrorMessage() {
    return this.errorMessage;
  }

  /**
   * @return the {@link ViewModelResultTypes ViewModelResultType} of the the model call.
   */
  @Nullable
  public ViewModelResultTypes getSuccess() {
    return this.viewModelResultType;
  }

  /**
   * @return true if {@link Result#getSuccess()} is true, false otherwise.
   */
  public boolean isSuccess() {
    return this.viewModelResultType == ViewModelResultTypes.SUCCESSFUL ||
        this.viewModelResultType == ViewModelResultTypes.STUDENT ||
        this.viewModelResultType == ViewModelResultTypes.SUPERVISOR;
  }
}
