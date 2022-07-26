package com.hfad.thinder.viewmodels.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

public class PasswordResetRequestViewModel extends ViewModel {
    private static final UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<String> email;

    public void resetRequest() {
        Result result = userRepository.sendRecoveryEmail(email.getValue());

    }
//----------------getter and setter-----------------------------------
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
