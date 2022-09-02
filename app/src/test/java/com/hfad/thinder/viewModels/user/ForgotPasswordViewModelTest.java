package com.hfad.thinder.viewModels.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.user.ForgotPasswordViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.MockitoAnnotations;

public class ForgotPasswordViewModelTest {

  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private ForgotPasswordViewModel viewModel;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    viewModel = new ForgotPasswordViewModel();
    viewModel.setCode(new MutableLiveData<>("code"));
    viewModel.setNewPassword(new MutableLiveData<>("Test1234"));
    viewModel.setNewPasswordConfirmation(new MutableLiveData<>("Test1234"));
  }

  @Test
  public void passwordForgotDataValid() {
    viewModel.passwordForgotDataChanged();
    Assert.assertTrue(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidCode() {
    String code = "";
    viewModel.setCode(new MutableLiveData<>(code));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getCodeErrorMessage());
    code = null;
    viewModel.setCode(new MutableLiveData<>(code));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getCodeErrorMessage());
  }

  @Test
  public void validCode() {
    String code = "code";
    viewModel.setCode(new MutableLiveData<>(code));
    viewModel.passwordForgotDataChanged();
    Assert.assertNull(viewModel.getFormState().getValue().getCodeErrorMessage());
  }

  @Test
  public void invalidPassword() {
    String password = "";
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());

    password = null;
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());

    password = "Ab1234";//to short
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());

    password = "Test1234567890111213141516";// to long
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());

    password = "test1234";//no upper case letter
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());

    password = "Testabsde";//no numbers
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());

    password = "TEST1234";//no lower case letter
    viewModel.setNewPassword(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(viewModel.getFormState().getValue().getNewPasswordErrorMessage());
  }

  @Test
  public void invalidConfirmPassword() {
    String password = "";
    viewModel.setNewPasswordConfirmation(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(
        viewModel.getFormState().getValue().getNewPasswordConfirmationErrorMessage());

    password = null;
    viewModel.setNewPasswordConfirmation(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(
        viewModel.getFormState().getValue().getNewPasswordConfirmationErrorMessage());

    password = "Test12345";//password doesn't match
    viewModel.setNewPasswordConfirmation(new MutableLiveData<>(password));
    viewModel.passwordForgotDataChanged();
    Assert.assertNotNull(
        viewModel.getFormState().getValue().getNewPasswordConfirmationErrorMessage());
  }

  @Test
  public void getFormStateNotNull() {
    assertNotNull(viewModel.getFormState());
  }

  @Test
  public void getLoginResultNotNull() {
    assertNotNull(viewModel.getLoginResult());
  }

  @Test
  public void getCodeNotNull() {
    viewModel.setCode(null);
    assertNotNull(viewModel.getCode());
  }

  @Test
  public void setCode() {
    String code = "code";
    viewModel.setCode(new MutableLiveData<>(code));
    assertEquals(code, viewModel.getCode().getValue());
  }

  @Test
  public void getNewPasswordNotNull() {
    viewModel.setNewPassword(null);
    assertNotNull(viewModel.getNewPassword());
  }

  @Test
  public void setNewPassword() {
    String password = "password";
    viewModel.setNewPassword(new MutableLiveData<>(password));
    assertEquals(password, viewModel.getNewPassword().getValue());
  }

  @Test
  public void getNewPasswordConfirmationNotNull() {
    viewModel.setNewPasswordConfirmation(null);
    assertNotNull(viewModel.getNewPasswordConfirmation());
  }

  @Test
  public void setNewPasswordConfirmation() {
    String password = "password";
    viewModel.setNewPasswordConfirmation(new MutableLiveData<>(password));
    assertEquals(password, viewModel.getNewPasswordConfirmation().getValue());
  }

  @Test
  public void getIsLoadingNotNull() {
    assertNotNull(viewModel.getIsLoading());
  }
}