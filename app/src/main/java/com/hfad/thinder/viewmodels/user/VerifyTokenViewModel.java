package com.hfad.thinder.viewmodels.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

public class VerifyTokenViewModel extends ViewModel {

  private UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> token;
  private MutableLiveData<ViewModelResult> verifyTokenResult;
  private MutableLiveData<VerifyTokenStates> state;
  // Todo: Auch nach dem Verlassen der App muss, befor es weiter geht der Token verifiziert werden in(siehe login seite)

  public void VerifyToken() {
    Result result = userRepository.verifyUser(token.getValue());
    if (!result.getSuccess()) {
      verifyTokenResult.setValue(new ViewModelResult(result.getErrorMessage(),
              ViewModelResultTypes.ERROR));

    }else {
      Result roleResult = userRepository.login(userRepository.getUser().getPassword(),userRepository.getUser().getMail());
      if (!roleResult.getSuccess()) {
        verifyTokenResult.setValue(new ViewModelResult(result.getErrorMessage(),
                ViewModelResultTypes.ERROR));
      }else {

        if (userRepository.getType().toString() == USERTYPE.STUDENT.toString()) {
          verifyTokenResult.setValue(
                  new ViewModelResult("STUDENT", ViewModelResultTypes.STUDENT));
        } else if (userRepository.getType().toString() == USERTYPE.SUPERVISOR.toString()) {
          verifyTokenResult.setValue(
                  new ViewModelResult("SUPERVISOR", ViewModelResultTypes.SUPERVISOR));
        } else {
          state.setValue(VerifyTokenStates.FAILURE);
        }
      }

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

