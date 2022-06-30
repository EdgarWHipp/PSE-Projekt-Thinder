package com.hfad.thinder.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfirmPasswordViewModel extends ViewModel {

    private MutableLiveData<Boolean> pending;
    private MutableLiveData<Boolean> successful;

    public ConfirmPasswordViewModel() throws InterruptedException {
        pending = new MutableLiveData<>();
        successful = new MutableLiveData<>();
        pending.setValue(true);
        successful.setValue(false);
    }
    
    public boolean getPending() {
        return pending.getValue();
    }

    public boolean getSuccessful() {
        return successful.getValue();
    }
}
