package com.hfad.thinder.viewmodels.user;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

public class PasswordResetRequestViewModel extends ViewModel {
    private static final UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<ViewModelResult> resetRequestResult;
    private MutableLiveData<String> email;

    public void resetRequest() {
        Result result = userRepository.sendRecoveryEmail(email.getValue());
        if (result.getSuccess()) {
            resetRequestResult.setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
        } else {
            resetRequestResult.setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
        }
    }
//----------------getter and setter-----------------------------------


    public MutableLiveData<ViewModelResult> getResetRequestResult() {
        if (resetRequestResult == null) {
            resetRequestResult = new MutableLiveData<>();
        }
        return resetRequestResult;
    }

    public MutableLiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<>();
        }
        return email;
    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
    }
}
