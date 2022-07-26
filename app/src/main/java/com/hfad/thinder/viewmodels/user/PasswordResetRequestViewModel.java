package com.hfad.thinder.viewmodels.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PasswordResetRequestViewModel extends ViewModel {
    private MutableLiveData<String> email;

    public void resetRequest() {

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
