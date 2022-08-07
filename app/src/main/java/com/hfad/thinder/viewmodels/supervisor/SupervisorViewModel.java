package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SupervisorViewModel extends ViewModel {
    // signals whether the user has already filled in the profile information
    private MutableLiveData<Boolean> profileComplete;
    //todo retrieve data from backend

    public MutableLiveData<Boolean> getProfileComplete() {
        if(profileComplete == null)
            profileComplete = new MutableLiveData<>();
            loadProfileComplete();
        return profileComplete;
    }

    //Todo: lade Daten aus dem Model
    private void loadProfileComplete() {
        profileComplete.postValue(true);
    }
}
