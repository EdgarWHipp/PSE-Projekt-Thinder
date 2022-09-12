package com.hfad.thinder.viewModels.user;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.student.EditProfileViewModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class EditProfileViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private EditProfileViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    viewModel = new EditProfileViewModel();
    viewModel.setFirstName(new MutableLiveData<>("firstName"));
    viewModel.setLastName(new MutableLiveData<>("lastName"));

  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }



  @Test
  public void getFormStateNotNull() {
    Assert.assertNotNull(viewModel.getFormState());
  }

  @Test
  public void getSafeResultNotNull() {
    Assert.assertNotNull(viewModel.getSafeResult());
  }

  @Test
  public void getDeleteResultNotNull() {
    Assert.assertNotNull(viewModel.getDeleteResult());
  }


  @Test
  public void setFirstName() {
    String firstName = "Gandalf";
    viewModel.setFirstName(new MutableLiveData<>(firstName));
    Assert.assertEquals(firstName, viewModel.getFirstName().getValue());
  }

  @Test
  public void setLastName() {
    String lastName = "Graurock";
    viewModel.setLastName(new MutableLiveData<>(lastName));
    Assert.assertEquals(lastName, viewModel.getLastName().getValue());
  }

}