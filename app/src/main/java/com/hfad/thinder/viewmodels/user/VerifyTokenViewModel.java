package com.hfad.thinder.viewmodels.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

public class VerifyTokenViewModel extends ViewModel {

  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> token;
  private MutableLiveData<ViewModelResult> verifyTokenResult;
  private MutableLiveData<VerifyTokenStates> state;
  // Todo: Auch nach dem Verlassen der App muss, befor es weiter geht der Token verifiziert werden in(siehe login seite)

  public void VerifyToken() {

    Result result = userRepository.verifyToken(token.getValue());
    if (!result.getSuccess()) {
      verifyTokenResult.setValue(new ViewModelResult("Verifizierung fehlgeschlagen",
              null));//Todo: hier kommt error aus model

    }
    state.setValue(VerifyTokenStates.LOADING);
    Result loginResult = userRepository.login(userRepository.getUser().getPassword(), userRepository.getUser().geteMail());
    if (!result.getSuccess()) {
      verifyTokenResult.setValue(new ViewModelResult("Verifizierung fehlgeschlagen",
              null));//Todo: hier kommt error aus model
    }

    if (userRepository.getType() == USERTYPE.STUDENT) {
      verifyTokenResult.setValue(
              new ViewModelResult(null, ViewModelResultTypes.STUDENT));
    } else if (userRepository.getType() == USERTYPE.SUPERVISOR) {
      verifyTokenResult.setValue(
              new ViewModelResult(null, ViewModelResultTypes.SUPERVISOR));
    } else {
      state.setValue(VerifyTokenStates.FAILURE);
    }
  }






  //---------------------getter and setter


  public MutableLiveData<String> getToken() {
    if (token == null) {
      token = new MutableLiveData<>();
    }
    return token;
  }

  public void setToken(MutableLiveData<String> token) {
    this.token = token;
  }

  public MutableLiveData<ViewModelResult> getVerifyTokenResult() {
    if (verifyTokenResult == null) {
      verifyTokenResult = new MutableLiveData<>();
    }
    return verifyTokenResult;
  }

  public MutableLiveData<VerifyTokenStates> getState() {
    if (state == null) {
      state = new MutableLiveData<>();
      state.setValue(VerifyTokenStates.IDLE);
    }
    return state;
  }

}

