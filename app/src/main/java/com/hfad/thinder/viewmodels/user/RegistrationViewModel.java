package com.hfad.thinder.viewmodels.user;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


  // This function is called when the user presses the register button
  public void register() {
    Result result=userRepository.register(firstName.getValue(), lastName.getValue(), password.getValue(),
            email.getValue());

    if (!result.getSuccess()) {
      registrationResult.setValue(new ViewModelResult(result.getErrorMessage(),
          ViewModelResultTypes.ERROR));
    } else {
      registrationResult.setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    }

  }

  //Ändert den Zustand der Validität der Email und des Passworts
  // Wird in der afterTextChange Methode aufgerufen
  public void registrationDataChanged() {
    getRegistrationFormState().setValue(
        new RegistrationFormState(emailFormatIsValid(), firstNameFormatIsValid(),
            lastNameFormatIsValid(), passwordFormIsValid(), passwordConfirmationFormIsValid()));
  }

  //--------------------getter and setter --------------------------------------

  public MutableLiveData<RegistrationFormState> getRegistrationFormState() {
    if (registrationFormState == null) {
      registrationFormState = new MutableLiveData<>();
    }
    return this.registrationFormState;
  }

  public MutableLiveData<ViewModelResult> getRegistrationResult() {
    if (registrationResult == null) {
      registrationResult = new MutableLiveData<>();
    }
    return this.registrationResult;
  }


  public MutableLiveData<String> getEmail() {
    if (email == null) {
      email = new MutableLiveData<String>();
    }
    return email;
  }

  public void setEmail(MutableLiveData<String> email) {
    this.email = email;
  }

  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<String>();
    }
    return firstName;
  }

  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<String>();
    }
    return lastName;
  }

  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  public MutableLiveData<String> getPassword() {
    if (password == null) {
      password = new MutableLiveData<String>();
    }
    return password;
  }

  public void setPassword(MutableLiveData<String> password) {
    this.password = password;
  }

  public MutableLiveData<String> getPasswordConfirmation() {
    if (passwordConfirmation == null) {
      passwordConfirmation = new MutableLiveData<String>();
    }
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(MutableLiveData<String> passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
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
      //Todo: seltsamen Bugg beheben
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

}
