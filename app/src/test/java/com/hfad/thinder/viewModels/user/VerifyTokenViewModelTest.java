package com.hfad.thinder.viewModels.user;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.viewmodels.user.VerifyTokenViewModel;
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
public class VerifyTokenViewModelTest {

  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();
  @Mock
  private UserRepository userRepository;
  private VerifyTokenViewModel viewModel;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    viewModel = new VerifyTokenViewModel();
    viewModel.setToken(new MutableLiveData<>("token"));

  }



  @Test
  public void getTokenNotNull() {
    viewModel.setToken(null);
    viewModel.getToken();
    Assert.assertNotNull(viewModel.getToken());
  }

  @Test
  public void setToken() {
    MutableLiveData<String> token = new MutableLiveData<>("token");
    viewModel.setToken(token);
    Assert.assertEquals("token", viewModel.getToken().getValue());
  }

  @Test
  public void getVerifyTokenResult() {
    Assert.assertNotNull(viewModel.getVerifyTokenResult());
  }

  @Test
  public void getState() {
    Assert.assertNotNull(viewModel.getState());
  }
}