package com.hfad.thinder.viewModels.student;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.hfad.thinder.viewmodels.supervisor.ThesisManagerViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.MockitoAnnotations;

public class ThesisManagerViewModelTest {

  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();


  private ThesisManagerViewModel viewModel;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    viewModel = new ThesisManagerViewModel();

  }


  @Test
  public void getIsLoadingNotNull() {
    Assert.assertNotNull(viewModel.getIsLoading());
  }
  //Todo: testen momentan wegen sigleton-Muster in den Repositorys und der AsyncTask nicht möglich


}