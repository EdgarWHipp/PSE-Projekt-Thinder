package com.hfad.thinder.viewmodels.user;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.user.ForgotPasswordActivity ForgotPasswordActivity}.
 */
public class ForgotPasswordViewModel extends ViewModel {
  private static final Pattern PASSWORD_PATTERN =
      Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
  private final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<ForgotPasswordFormState> formState;
  private MutableLiveData<ViewModelResult> loginResult;

  private MutableLiveData<String> code;
  private MutableLiveData<String> newPassword;
  private MutableLiveData<String> newPasswordConfirmation;
  private MutableLiveData<Boolean> isLoading;

  /**
   * Use this method to log in to the users account with the token and a new password.
   */
  public void login() {
    new LoginTask().execute(code.getValue(), newPassword.getValue());

  }
  //Todo: es fehlt noch ein Unverified

  /**
   * Use this method to check whether the entered profile data is valid. This method should be called everytime the user edits the data in the ui.
   */
  public void passwordForgotDataChanged() {
    getFormState().setValue(
        new ForgotPasswordFormState(codeConfirmationIsValid(), passwordFormIsValid(),
            passwordConfirmationFormIsValid()));
  }

  //---------------------getter and setter----------------------------------------------------------

  /**
   * @return the current {@link ForgotPasswordFormState}.
   */
  public MutableLiveData<ForgotPasswordFormState> getFormState() {
    if (formState == null) {
      formState = new MutableLiveData<>();
    }
    return formState;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #login()} operation.
   */
  public MutableLiveData<ViewModelResult> getLoginResult() {
    if (loginResult == null) {
      loginResult = new MutableLiveData<>();
    }
    return loginResult;
  }

  /**
   * @return the token used to change the password
   */
  public MutableLiveData<String> getCode() {
    if (code == null) {
      code = new MutableLiveData<>();
    }
    return code;
  }

  /**
   * @param code the token used to change the password
   */
  public void setCode(MutableLiveData<String> code) {
    this.code = code;
  }

  /**
   * @return the new password
   */
  public MutableLiveData<String> getNewPassword() {
    if (newPassword == null) {
      newPassword = new MutableLiveData<>();
    }
    return newPassword;
  }

  /**
   * @param newPassword the new password
   */
  public void setNewPassword(MutableLiveData<String> newPassword) {
    this.newPassword = newPassword;
  }

  /**
   * @return the confirmation of the new password
   */
  public MutableLiveData<String> getNewPasswordConfirmation() {
    if (newPasswordConfirmation == null) {
      newPasswordConfirmation = new MutableLiveData<>();
    }
    return newPasswordConfirmation;
  }

  /**
   * @param newPasswordConfirmation the confirmation of the new password
   */
  public void setNewPasswordConfirmation(
      MutableLiveData<String> newPasswordConfirmation) {
    this.newPasswordConfirmation = newPasswordConfirmation;
  }

  public MutableLiveData<Boolean> getIsLoading() {
    if(isLoading == null){
      isLoading = new MutableLiveData<>();
      isLoading.setValue(false);
    }
    return isLoading;
  }

  //--------private methods-------------------------------------------

  private Integer passwordFormIsValid() {
    if (newPassword.getValue() == null || newPassword.getValue().equals("")) {
      return R.string.no_password_error;
    }
    if (newPassword.getValue().length() < 8) {
      return R.string.password_to_short_error;
    }
    Matcher m = PASSWORD_PATTERN.matcher(newPassword.getValue());
    if (!m.matches()) {
      return R.string.password_not_safe_error;
    }
    return null;
  }

  private Integer passwordConfirmationFormIsValid() {
    if (newPasswordConfirmation.getValue() == null ||
        newPasswordConfirmation.getValue().equals("")) {
      return R.string.no_password_confirmation_error;
      //Todo: seltsamen Bugg beheben
    }
    if (newPassword.getValue() == null ||
        !newPassword.getValue().equals(newPasswordConfirmation.getValue())) {
      return R.string.passwords_do_not_match_error;
    }
    return null;
  }

  private Integer codeConfirmationIsValid() {
    if (code.getValue() == null || code.getValue().equals("")) {
      return R.string.token_error;
    }
    return null;
  }
  
  private class LoginTask extends AsyncTask<String, Void, Result> {
    @Override
    protected void onPreExecute() {
      getIsLoading().setValue(true);
    }

    @Override
    protected Result doInBackground(String... strings) {
      return userRepository.resetPasswordWithToken(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(Result result) {
      if (!result.getSuccess()) {
        loginResult.setValue(
                new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
      } else if (result.getSuccess()) {
        loginResult.setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      }
      loginResult.setValue(null);
      isLoading.setValue(false);
    }
  }
}
