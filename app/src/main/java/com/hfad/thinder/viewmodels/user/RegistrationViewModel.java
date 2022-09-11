package com.hfad.thinder.viewmodels.user;


import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.user.ForgotPasswordActivity ForgotPasswordActivity}.
 */
public class RegistrationViewModel extends ViewModel {
  private static final Pattern PASSWORD_PATTERN =
      Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
  private static final UserRepository userRepository = UserRepository.getInstance();

  private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
  private MutableLiveData<ViewModelResult> registrationResult = new MutableLiveData<>();

  private MutableLiveData<String> email;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> password;
  private MutableLiveData<String> passwordConfirmation;

  private MutableLiveData<Boolean> isLoading;


  /**
   * Use this method to register a new user.
   */
  public void register() {
    new RegisterTask().execute(firstName.getValue(), lastName.getValue(), password.getValue(),
            email.getValue());
  }

  /**
   * Use this method to check whether the entered data is valid. This method should be called everytime the user edits the data in the ui.
   */
  public void registrationDataChanged() {
    getRegistrationFormState().setValue(
        new RegistrationFormState(emailFormatIsValid(), firstNameFormatIsValid(),
            lastNameFormatIsValid(), passwordFormIsValid(), passwordConfirmationFormIsValid()));
  }

  //--------------------getter and setter --------------------------------------

  /**
   * @return the current {@link RegistrationFormState}
   */
  public MutableLiveData<RegistrationFormState> getRegistrationFormState() {
    if (registrationFormState == null) {
      registrationFormState = new MutableLiveData<>();
    }
    return this.registrationFormState;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #register()} operation.
   */
  public MutableLiveData<ViewModelResult> getRegistrationResult() {
    if (registrationResult == null) {
      registrationResult = new MutableLiveData<>();
    }
    return this.registrationResult;
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

  /**
   * @return the users first name.
   */
  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<String>();
    }
    return firstName;
  }

  /**
   * @param firstName the users first name.
   */
  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the users last name.
   */
  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<String>();
    }
    return lastName;
  }

  /**
   * @param lastName the users last name.
   */
  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the entered password.
   */
  public MutableLiveData<String> getPassword() {
    if (password == null) {
      password = new MutableLiveData<String>();
    }
    return password;
  }

  /**
   * @param password the entered password.
   */
  public void setPassword(MutableLiveData<String> password) {
    this.password = password;
  }

  /**
   * @return the entered password confirmation.
   */
  public MutableLiveData<String> getPasswordConfirmation() {
    if (passwordConfirmation == null) {
      passwordConfirmation = new MutableLiveData<String>();
    }
    return passwordConfirmation;
  }

  /**
   * @param passwordConfirmation the entered password confirmation.
   */
  public void setPasswordConfirmation(MutableLiveData<String> passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  /** Use this method to determine if a model call has finished or not.
   * @return true if the call to the model is not finished, false otherwise.
   */
  public MutableLiveData<Boolean> getIsLoading() {
    if(isLoading == null){
      isLoading = new MutableLiveData<>();
      isLoading.setValue(false);
    }
    return isLoading;
  }

  //---------private methods---------------------------------------------------------------------


  private Integer passwordFormIsValid() {
    if (password.getValue() == null || password.getValue().equals("")) {
      return R.string.no_password_error;
    }
    if (password.getValue().length() < 8) {
      return R.string.password_to_short_error;
    }
    Matcher m = PASSWORD_PATTERN.matcher(password.getValue());
    if (!m.matches()) {
      return R.string.password_not_safe_error;
    }
    return null;
  }

  private Integer passwordConfirmationFormIsValid() {
    if (passwordConfirmation.getValue() == null || passwordConfirmation.getValue().equals("")) {
      return R.string.no_password_confirmation_error;

    }
    if (password.getValue() == null ||
        !password.getValue().equals(passwordConfirmation.getValue())) {
      return R.string.passwords_do_not_match_error;
    }
    return null;
  }


  private Integer emailFormatIsValid() {
    if (email.getValue() == null || email.getValue().equals("")) {
      return R.string.no_email_error;
    }
    if (!EMAIL_PATTERN.matcher(email.getValue()).matches()) {
      return R.string.invalid_email_error;
    }
    return null;
  }

  private Integer firstNameFormatIsValid() {
    if (firstName.getValue() == null || firstName.getValue().equals("")) {
      return R.string.no_first_name_error;
    }
    return null;
  }

  private Integer lastNameFormatIsValid() {
    if (lastName.getValue() == null || lastName.getValue().equals("")) {
      return R.string.no_last_name_error;
    }
    return null;
  }

  private class RegisterTask extends AsyncTask<String, Void, Result>{

    @Override
    protected void onPreExecute() {
      getIsLoading().setValue(true);
    }

    @Override
    protected Result doInBackground(String... strings) {
      return userRepository.register(strings[0], strings[1], strings[2],
              strings[3]);
    }

    @Override
    protected void onPostExecute(Result result) {
      if (!result.getSuccess()) {
        registrationResult.setValue(new ViewModelResult(result.getErrorMessage(),
                ViewModelResultTypes.ERROR));
      } else {
        registrationResult.setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      }
      isLoading.setValue(false);
    }
  }
}
