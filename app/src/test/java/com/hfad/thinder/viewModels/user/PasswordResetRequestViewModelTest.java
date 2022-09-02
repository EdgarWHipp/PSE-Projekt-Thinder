package com.hfad.thinder.viewModels.user;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.user.PasswordResetRequestViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.MockitoAnnotations;

public class PasswordResetRequestViewModelTest {

  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private PasswordResetRequestViewModel viewModel;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    viewModel = new PasswordResetRequestViewModel();
  }

  @Test
  public void getResetRequestResultNotNull() {
    Assert.assertNotNull(viewModel.getResetRequestResult());
  }

  @Test
  public void getEmailNotNull() {
    viewModel.setEmail(null);
    Assert.assertNotNull(viewModel.getEmail());
  }

  @Test
  public void setEmail() {
    String mail = "mail";
    viewModel.setEmail(new MutableLiveData<>(mail));
    Assert.assertEquals(mail, viewModel.getEmail().getValue());
  }

  @Test
  public void getIsLoadingNotNull() {
    Assert.assertNotNull(viewModel.getIsLoading());
  }
}