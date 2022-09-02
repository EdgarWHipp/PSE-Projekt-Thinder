package com.hfad.thinder.viewModels.user;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.user.LoginViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.MockitoAnnotations;

public class LoginViewModelTest {

  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private LoginViewModel viewModel;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    viewModel = new LoginViewModel();
  }


  @Test
  public void loginDataValid() {
    viewModel.setEmail("a@kit.edu");
    viewModel.setPassword("Test1234");
    viewModel.loginDataChanged();
    Assert.assertTrue(viewModel.getIsDataValid().getValue());
  }

  @Test
  public void invalidEmail() {
    viewModel.setEmail("");
    viewModel.setPassword("Test1234");
    viewModel.loginDataChanged();
    Assert.assertFalse(viewModel.getIsDataValid().getValue());
    viewModel.setEmail(null);
    viewModel.loginDataChanged();
    Assert.assertFalse(viewModel.getIsDataValid().getValue());
  }

  @Test
  public void invalidPassword() {
    viewModel.setEmail("a@kit.edu");
    viewModel.setPassword("");
    viewModel.loginDataChanged();
    Assert.assertFalse(viewModel.getIsDataValid().getValue());
    viewModel.setPassword(null);
    viewModel.loginDataChanged();
    Assert.assertFalse(viewModel.getIsDataValid().getValue());
  }

  @Test
  public void getIsDataValidNotNull() {
    Assert.assertNotNull(viewModel.getIsDataValid());
  }

  @Test
  public void getLoginResultNotNull() {
    Assert.assertNotNull(viewModel.getLoginResult());
  }

  @Test
  public void getEmailNotNull() {
    Assert.assertNotNull(viewModel.getEmail());
  }

  @Test
  public void setEmail() {
    String mail = "mail";
    viewModel.setEmail(mail);
    Assert.assertEquals(mail, viewModel.getEmail().getValue());
  }

  @Test
  public void getPasswordNotNull() {
    Assert.assertNotNull(viewModel.getPassword());
  }

  @Test
  public void setPassword() {
    String password = "password";
    viewModel.setPassword(password);
    Assert.assertEquals(password, viewModel.getPassword().getValue());
  }

  @Test
  public void getIsLoadingNotNull() {
    Assert.assertNotNull(viewModel.getIsLoading());
  }

  @Test
  public void setIsLoading() {
    boolean isLoading = true;
    viewModel.setIsLoading(new MutableLiveData<>(isLoading));
    Assert.assertTrue(viewModel.getIsLoading().getValue());
    viewModel.setIsLoading(new MutableLiveData<>(!isLoading));
    Assert.assertFalse(viewModel.getIsLoading().getValue());

  }
}