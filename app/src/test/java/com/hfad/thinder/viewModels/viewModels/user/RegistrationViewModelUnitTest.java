package com.hfad.thinder.viewModels.viewModels.user;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.user.RegistrationViewModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class RegistrationViewModelUnitTest {

  private static final RegistrationViewModel registrationViewModel = new RegistrationViewModel();


  @BeforeClass
  public static void setRegistrationValues() {
    registrationViewModel.setEmail(new MutableLiveData<>("g@kit.edu"));
    registrationViewModel.setFirstName(new MutableLiveData<>("Hans"));
    registrationViewModel.setLastName(new MutableLiveData<>("Wurst"));
    registrationViewModel.setPassword(new MutableLiveData<>("Test1234"));
    registrationViewModel.setPasswordConfirmation(new MutableLiveData<>("Test1234"));
  }

  @Test
  public void validMailTest() {
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(registrationViewModel.getRegistrationFormState().getValue()
        .getEmailErrorMessage());
  }
}
