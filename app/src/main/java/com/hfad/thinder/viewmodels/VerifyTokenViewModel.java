package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;

public class VerifyTokenViewModel extends ViewModel {

  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> token;
  private MutableLiveData<VerifyTokenResult> verifyTokenResult;
  private MutableLiveData<VerifyTokenStates> state;
  // Todo: Auch nach dem Verlassen der App muss, befor es weiter geht der Token verifiziert werden in(siehe login seite)

  public void VerifyToken() {
    state.setValue(VerifyTokenStates.LOADING);

    boolean result = userRepository.verifyToken(
        token.getValue());//Todo: hier auch ein Result zurückgeben für Fehlermeldungen
    if (!result) {
      if (userRepository.getType() == USERTYPE.STUDENT) {
        verifyTokenResult.setValue(new VerifyTokenResult(null, ResultTypes.STUDENT));
      }
      if (userRepository.getType() == USERTYPE.SUPERVISOR) {
        verifyTokenResult.setValue(new VerifyTokenResult(null, ResultTypes.SUPERVISOR));
      }
      state.setValue(VerifyTokenStates.SUCCESSFUL);
    } else {
      verifyTokenResult.setValue(new VerifyTokenResult("Verifizierung fehlgeschlagen",
          null));//Todo: hier kommt error aus model
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

  public MutableLiveData<VerifyTokenResult> getVerifyTokenResult() {
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
