package com.hfad.thinder.viewmodels.user;

import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.user.RequestPasswordResetActivity RequestPasswordResetActivity}.
 */
public class PasswordResetRequestViewModel extends ViewModel {
  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<ViewModelResult> resetRequestResult;
  private MutableLiveData<String> email;
  private MutableLiveData<Boolean> isLoading;

  /**
   * Use this method to send a request to reset the users password.
   */
  public void resetRequest() {
    new ResetRequestTask().execute(getEmail().getValue());

  }
//----------------getter and setter-----------------------------------


  /**
   * @return the {@link ViewModelResult} of the {@link #resetRequest()} operation
   */
  public MutableLiveData<ViewModelResult> getResetRequestResult() {
    if (resetRequestResult == null) {
      resetRequestResult = new MutableLiveData<>();
    }
    return resetRequestResult;
  }

  /**
   * @return the users mail address.
   */
  public MutableLiveData<String> getEmail() {
    if (email == null) {
      email = new MutableLiveData<>();
    }
    return email;
  }

  /**
   * @param email the users mail address.
   */
  public void setEmail(MutableLiveData<String> email) {
    this.email = email;
  }

  public MutableLiveData<Boolean> getIsLoading() {
    if (isLoading == null) {
      isLoading = new MutableLiveData<>();
      isLoading.setValue(false);
    }
    return isLoading;
  }

  private class ResetRequestTask extends AsyncTask<String, Void, Result> {

    @Override
    protected void onPreExecute() {
      getIsLoading().setValue(true);
    }

    @Override
    protected Result doInBackground(String... strings) {
      return userRepository.sendRecoveryEmail(strings[0]);
    }

    @Override
    protected void onPostExecute(Result result) {
      if (result.getSuccess()) {
        resetRequestResult.setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      } else {
        resetRequestResult.setValue(
            new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
      }
      isLoading.setValue(false);
    }
  }
}
