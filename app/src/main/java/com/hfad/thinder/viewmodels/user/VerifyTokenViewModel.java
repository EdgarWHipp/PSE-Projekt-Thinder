package com.hfad.thinder.viewmodels.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.user.VerifyTokenActivity VerifyTokenActivity}.
 */
public class VerifyTokenViewModel extends ViewModel {

  private final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> token;
  private MutableLiveData<ViewModelResult> verifyTokenResult;
  private MutableLiveData<VerifyTokenStates> state;

  /**
   * Use this method to verify the users mail address with the given token.
   */
  public void VerifyToken() {
    Result result = userRepository.verifyUser(token.getValue());
    if (!result.getSuccess()) {
      verifyTokenResult.setValue(new ViewModelResult(result.getErrorMessage(),
          ViewModelResultTypes.ERROR));
      state.setValue(VerifyTokenStates.FAILURE);
    } else {
      Result roleResult =
          userRepository.login(userRepository.getPassword(), userRepository.getUser().getMail());
      if (!roleResult.getSuccess()) {
        verifyTokenResult.setValue(new ViewModelResult(result.getErrorMessage(),
            ViewModelResultTypes.ERROR));
        state.setValue(VerifyTokenStates.FAILURE);
      } else {

        if (userRepository.getType().toString() == USERTYPE.STUDENT.toString()) {
          verifyTokenResult.setValue(
              new ViewModelResult(null, ViewModelResultTypes.STUDENT));
          state.setValue(VerifyTokenStates.SUCCESSFUL);
        } else if (userRepository.getType().toString() == USERTYPE.SUPERVISOR.toString()) {
          verifyTokenResult.setValue(
              new ViewModelResult(null, ViewModelResultTypes.SUPERVISOR));
          state.setValue(VerifyTokenStates.SUCCESSFUL);
        } else {
          state.setValue(VerifyTokenStates.FAILURE);
        }
      }

    }

  }


  //---------------------getter and setter


  /**
   * @return the token used to verify the mail addess.
   */
  public MutableLiveData<String> getToken() {
    if (token == null) {
      token = new MutableLiveData<>();
    }
    return token;
  }

  /**
   * @param token the token used to verify the mail address.
   */
  public void setToken(MutableLiveData<String> token) {
    this.token = token;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #VerifyToken()} operation.
   */
  public MutableLiveData<ViewModelResult> getVerifyTokenResult() {
    if (verifyTokenResult == null) {
      verifyTokenResult = new MutableLiveData<>();
    }
    return verifyTokenResult;
  }

  /**
   * @return the current {@link VerifyTokenStates state} of the verification process.
   */
  public MutableLiveData<VerifyTokenStates> getState() {
    if (state == null) {
      state = new MutableLiveData<>();
      state.setValue(VerifyTokenStates.IDLE);
    }
    return state;
  }

}

