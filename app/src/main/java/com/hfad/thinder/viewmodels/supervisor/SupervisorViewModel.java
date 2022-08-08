package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.source.repository.UserRepository;

public class SupervisorViewModel extends ViewModel {
    // signals whether the user has already filled in the profile information
    private static final UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> profileComplete;

    public MutableLiveData<Boolean> getProfileComplete() {
        if(profileComplete == null)
            profileComplete = new MutableLiveData<>();
            loadProfileComplete();
        return profileComplete;
    }


    private void loadProfileComplete() {
        profileComplete.postValue(userRepository.isProfileComplete());
    }
}
