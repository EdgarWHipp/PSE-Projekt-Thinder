package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.source.repository.UserRepository;

public class ConfirmPasswordViewModel extends ViewModel {

  private UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<Boolean> isLoading;
  private MutableLiveData<ConfirmPasswordResult> success;

  public void ConfirmPassword() {
    /** Todo: Implementieren
     * Beispielcode nicht final!!
     *
     * Result result = userRepository.confirmPassword();
     * String errorMessage = result.getErrorMessage();
     * String success = result.getSuccess();
     * result.setValue(new ConfirmPasswordResult(errorMessage, success);
     * isLoading.setValue(false);
     */
  }


  public MutableLiveData<Boolean> getIsLoading() {
    if (isLoading == null) {
      isLoading = new MutableLiveData<>();
      isLoading.setValue(true);
    }
    return isLoading;
  }

  public MutableLiveData<ConfirmPasswordResult> getSuccess() {
    if (success == null) {
      success = new MutableLiveData<>();
    }
    return success;
  }
}
