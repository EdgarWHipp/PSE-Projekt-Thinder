package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.source.repository.UserRepository;

public class VerifyTokenViewModel extends ViewModel {

    private UserRepository userRepository = UserRepository.getInstance();


    private MutableLiveData<VerifyTokenStates> state;
    // Todo: Livedata String für Token hinzufügen

    public void VerifyToken() {
        state.setValue(VerifyTokenStates.LOADING);
        /** Todo: Implementieren
         * Wird durch Button im Viewmodel aufgerufen
         * Beispielcode nicht final!!
         *
         * Result result = userRepository.confirmPassword();
         * String errorMessage = result.getErrorMessage();
         * String success = result.getSuccess();
         * result.setValue(new ConfirmPasswordResult(errorMessage, success);
         */
    }

    public MutableLiveData<VerifyTokenStates> getState() {
        if (state == null) {
            state = new MutableLiveData<>();
            state.setValue(VerifyTokenStates.IDLE);
        }
        return state;
    }

}
