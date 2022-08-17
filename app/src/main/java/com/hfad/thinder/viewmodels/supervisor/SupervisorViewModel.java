package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.source.repository.UserRepository;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.supervisor.SupervisorActivity SupervisorActivity}.
 */
public class SupervisorViewModel extends ViewModel {
  // signals whether the user has already filled in the profile information
  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<Boolean> profileComplete;

  /**
   * @return true if a complete profile has been saved successfully, false otherwise.
   */
  public MutableLiveData<Boolean> getProfileComplete() {
    if (profileComplete == null) {
      profileComplete = new MutableLiveData<>();
    }
    loadProfileComplete();
    return profileComplete;
  }

  /**
   * This method must be called when the user has saved his profile data and the {@link com.hfad.thinder.viewmodels.ViewModelResult ViewModelResult} signals a success.
   *
   * @param isComplete a boolean that determines whether the user has saved a completed profile.
   */
  public void setProfileComplete(Boolean isComplete) {
    getProfileComplete().setValue(isComplete);
  }


  private void loadProfileComplete() {
    profileComplete.postValue(userRepository.getUser().isComplete());
  }
}
