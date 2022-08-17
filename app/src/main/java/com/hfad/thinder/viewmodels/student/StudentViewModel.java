package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.source.repository.UserRepository;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.student.StudentActivity StudentActivity}.
 */
public class StudentViewModel extends ViewModel {
  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<Boolean> profileComplete;

  /**
   * @return a {@link MutableLiveData} object contains a {@link Boolean} that is true if the user has saved a completed profile and false otherwise.
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
   * @param b a boolean that determines whether the user has saved a completed profile.
   */
  public void setProfileComplete(boolean b) {
    getProfileComplete().setValue(b);
  }

  private void loadProfileComplete() {
    profileComplete.postValue(userRepository.getUser().isComplete());
  }
}
