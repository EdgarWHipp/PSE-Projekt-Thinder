package com.hfad.thinder.viewModels.user;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.user.RegistrationFormState;
import com.hfad.thinder.viewmodels.user.RegistrationViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationViewModelTest {
  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  @Mock
  UserRepository userRepository;
  @Mock
  androidx.lifecycle.Observer<ViewModelResult> resultObserver;
  @Mock
  androidx.lifecycle.Observer<RegistrationFormState> formStateObserver;
  private RegistrationViewModel registrationViewModel;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    registrationViewModel = new RegistrationViewModel();
    /*registrationViewModel.getRegistrationResult().observeForever(
        resultObserver);
    registrationViewModel.getRegistrationFormState().observeForever(formStateObserver);
     */
    registrationViewModel.setFirstName(new MutableLiveData<>("firstName"));
    registrationViewModel.setLastName(new MutableLiveData<>("lastName"));
    registrationViewModel.setPassword(new MutableLiveData<>("Test1234"));
    registrationViewModel.setPasswordConfirmation(new MutableLiveData<>("Test1234"));
    registrationViewModel.setEmail(new MutableLiveData<>("g@kit.edu"));
  }

  /*


  @Test
  public void registerFail() {
    registrationViewModel.setFirstName(new MutableLiveData<>("firstName"));
    registrationViewModel.setLastName(new MutableLiveData<>("lastName"));
    registrationViewModel.setPassword(new MutableLiveData<>("password"));
    registrationViewModel.setEmail(new MutableLiveData<>("mail"));
    Result result = new Result("errorMessage", false);
    Mockito.when(userRepository.register("firstName", "secondName", "password", "mail"))
        .thenReturn(result);
    registrationViewModel.register();
    Assert.assertEquals(registrationViewModel.getRegistrationResult().getValue().getSuccess(),
        ViewModelResultTypes.ERROR);
  }

   */

  @Test
  public void registrationDataValid() {
    registrationViewModel.registrationDataChanged();
    Assert.assertTrue(registrationViewModel.getRegistrationFormState().getValue().isDataValid());
  }

  @Test
  public void validMailTest() {
    String validMail = "gut@kit.edu";
    registrationViewModel.setEmail(new MutableLiveData<>(validMail));
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());

    String validMail1 = "GeMel@gmx.de";
    registrationViewModel.setEmail(new MutableLiveData<>(validMail1));
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());

    String validMail2 = "gut@student.kit.edu";
    registrationViewModel.setEmail(new MutableLiveData<>(validMail2));
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());
  }

  @Test
  public void invalidMailTest() {
    String invalidMail = "guki@...";
    registrationViewModel.setEmail(new MutableLiveData<>(invalidMail));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());

    String invalidMail2 = "gut@@kit.edu";
    registrationViewModel.setEmail(new MutableLiveData<>(invalidMail2));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());

    String invalidMail3 = "";
    registrationViewModel.setEmail(new MutableLiveData<>(invalidMail3));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());

    String invalidMail4 = null;
    registrationViewModel.setEmail(new MutableLiveData<>(invalidMail4));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getEmailErrorMessage());

  }

  @Test
  public void validPasswordTest() {
    String validPassword = "Test1234";
    registrationViewModel.setPassword(new MutableLiveData<>(validPassword));
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());

    String validPassword1 = "ebeT1daGvdd";
    registrationViewModel.setPassword(new MutableLiveData<>(validPassword1));
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());

    String validPassword2 = "@t&5ugiGGHHHH";
    registrationViewModel.setPassword(new MutableLiveData<>(validPassword2));
    registrationViewModel.registrationDataChanged();
    Assert.assertNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());
  }

  @Test
  public void invalidPassword() {
    String invalidPassword = "Test1234567891011121314151617";
    registrationViewModel.setPassword(new MutableLiveData<>(invalidPassword));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());

    String invalidPassword1 = "PASSWORD";
    registrationViewModel.setPassword(new MutableLiveData<>(invalidPassword1));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());

    String invalidPassword2 = "Mega_Password";
    registrationViewModel.setPassword(new MutableLiveData<>(invalidPassword2));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());

    String invalidPassword3 = "";
    registrationViewModel.setPassword(new MutableLiveData<>(invalidPassword3));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());

    String invalidPassword4 = null;
    registrationViewModel.setPassword(new MutableLiveData<>(invalidPassword4));
    registrationViewModel.registrationDataChanged();
    Assert.assertNotNull(
        registrationViewModel.getRegistrationFormState().getValue().getPasswordErrorMessage());
  }

  @Test
  public void getRegistrationFormStateNotNullTest() {
    MutableLiveData<RegistrationFormState> registrationFormState =
        registrationViewModel.getRegistrationFormState();
    Assert.assertNotNull(registrationFormState);
  }

  @Test
  public void getRegistrationResultNotNullTest() {
    MutableLiveData<ViewModelResult> viewModelResult =
        registrationViewModel.getRegistrationResult();
    Assert.assertNotNull(viewModelResult);
  }

  @Test
  public void getEmailNotNull() {
    registrationViewModel.setEmail(null);
    MutableLiveData<String> mail = registrationViewModel.getEmail();
    Assert.assertNotNull(mail);
  }

  @Test
  public void setEmail() {
    String mail = "mail";
    registrationViewModel.setEmail(new MutableLiveData<>(mail));
    Assert.assertEquals(registrationViewModel.getEmail().getValue(), mail);
  }

  @Test
  public void getFirstNameNotNull() {
    registrationViewModel.setFirstName(null);
    MutableLiveData<String> firstName = registrationViewModel.getFirstName();
    Assert.assertNotNull(firstName);
  }

  @Test
  public void setFirstName() {
    String firstName = "Gandalf";
    registrationViewModel.setFirstName(new MutableLiveData<>(firstName));
    Assert.assertEquals(firstName, registrationViewModel.getFirstName().getValue());
  }

  @Test
  public void getLastNameNotNull() {
    registrationViewModel.setLastName(null);
    MutableLiveData<String> lastName = registrationViewModel.getLastName();
    Assert.assertNotNull(lastName);
  }

  @Test
  public void setLastName() {
    String lastName = "Graurock";
    registrationViewModel.setLastName(new MutableLiveData<>(lastName));
    Assert.assertEquals(lastName, registrationViewModel.getLastName().getValue());
  }

  @Test
  public void getPasswordNotNull() {
    registrationViewModel.setPassword(null);
    MutableLiveData<String> password = registrationViewModel.getPassword();
    Assert.assertNotNull(password);
  }

  @Test
  public void setPassword() {
    String password = "password";
    registrationViewModel.setPassword(new MutableLiveData<>(password));
    Assert.assertEquals(password, registrationViewModel.getPassword().getValue());
  }

  @Test
  public void getPasswordConfirmationNotNull() {
    registrationViewModel.setPasswordConfirmation(null);
    MutableLiveData<String> password = registrationViewModel.getPasswordConfirmation();
    Assert.assertNotNull(password);
  }

  @Test
  public void setPasswordConfirmation() {
    String password = "password";
    registrationViewModel.setPasswordConfirmation(new MutableLiveData<>(password));
    Assert.assertEquals(password, registrationViewModel.getPasswordConfirmation().getValue());
  }

}