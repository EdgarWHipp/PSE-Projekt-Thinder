package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentViewModel extends ViewModel {
    // signals whether the user has already filled in the profile information
    private MutableLiveData<Boolean> profileComplete;
    //todo retrieve data from backend

    public MutableLiveData<Boolean> getProfileComplete() {
        if(profileComplete == null)
            profileComplete = new MutableLiveData<>();
        profileComplete.setValue(true);
        return profileComplete;
    }
}
