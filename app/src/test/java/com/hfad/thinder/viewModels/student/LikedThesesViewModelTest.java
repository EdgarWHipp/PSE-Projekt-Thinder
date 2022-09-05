package com.hfad.thinder.viewModels.student;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.hfad.thinder.viewmodels.student.LikedThesesViewModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class LikedThesesViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private LikedThesesViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    viewModel = new LikedThesesViewModel();
  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }

  @Test
  public void getLikedTheses() {
    //Aufruf an AsyncTask
  }

  @Test
  public void getIsLoadingNotNull() {
    Assert.assertNotNull(viewModel.getIsLoading());
  }

  @Test
  public void loadLikedTheses() {
    //Aufruf an AsyncTask
  }
}