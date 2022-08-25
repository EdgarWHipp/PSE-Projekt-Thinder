package com.hfad.thinder.viewmodels.user;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.user.LoginActivity LoginActivity}.
 */
public class LoginViewModel extends ViewModel {

  private final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<ViewModelResult> loginResult = new MutableLiveData<>();
  private MutableLiveData<Boolean> isDataValid = new MutableLiveData<>();
  private MutableLiveData<String> email;
  private MutableLiveData<String> password;
  private MutableLiveData<Boolean> isLoading;

  /**
   * Use this method to login in to an existing user profile, using the corresponding email and password.
   */
  public void login() {
    isLoading.setValue(true);
    Result result;
    result = userRepository.login(password.getValue(), email.getValue());
    isLoading.setValue(false);
    if (!result.getSuccess()) {
      loginResult.setValue(
          new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    } else if (result.getSuccess()) {
      userRepository.setPassword(password.getValue());
      USERTYPE usertype = userRepository.getType();
      if (usertype == USERTYPE.STUDENT) {
        loginResult.setValue(
            new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.STUDENT));
      } else if (usertype == USERTYPE.SUPERVISOR) {
        loginResult.setValue(
            new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.SUPERVISOR));
      } //Todo: unverified fehlt noch

    }


  }

  /**
   * Use this method to check whether the entered data is valid. This method should be called everytime the user edits the data in the ui.
   */
  public void loginDataChanged() {
    isDataValid.setValue(
        email.getValue() != null && password.getValue() != null &&
            !email.getValue().equals("") &&
            !password.getValue().equals(""));
  }


  //----------- getter and setter --------------------------------------

  /**
   * @return true if the entered data is valid, false otherwise.
   */
  public MutableLiveData<Boolean> getIsDataValid() {
    if (isDataValid == null) {
      isDataValid = new MutableLiveData<>();
    }
    return isDataValid;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #login()} operation.
   */
  public MutableLiveData<ViewModelResult> getLoginResult() {
    if (loginResult == null) {
      loginResult = new MutableLiveData<>();
    }
    return this.loginResult;
  }

  /**
   * @return the users mail address.
   */
  public MutableLiveData<String> getEmail() {
    if (email == null) {
      email = new MutableLiveData<String>();
    }
    return email;
  }

  /**
   * @param email the users mail address.
   */
  public void setEmail(String email) {
    this.email.setValue(email);
  }

  /**
   * @return the users password.
   */
  public MutableLiveData<String> getPassword() {
    if (password == null) {
      password = new MutableLiveData<String>();
    }
    return password;
  }

  /**
   * @param password the users password.
   */
  public void setPassword(String password) {
    this.password.setValue(password);
  }


  public MutableLiveData<Boolean> getIsLoading() {
    if(isLoading == null) {
      isLoading = new MutableLiveData<>();
      isLoading.setValue(false);
    }
    return isLoading;
  }

  public void setIsLoading(MutableLiveData<Boolean> isLoading) {
    this.isLoading = isLoading;
  }
}
