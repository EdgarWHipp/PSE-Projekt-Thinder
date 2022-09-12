package com.hfad.thinder.viewModels.student;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.hfad.thinder.viewmodels.student.SwipeScreenViewModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class SwipeScreenViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private SwipeScreenViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    viewModel = new SwipeScreenViewModel();
  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }

  @Test
  public void getCurrentDeckPositionNotNull() {
    Assert.assertNotNull(viewModel.getCurrentDeckPosition());
  }






  @Test
  public void getIsLoadingNotNull() {
    Assert.assertNotNull(viewModel.getIsLoading());
  }
}