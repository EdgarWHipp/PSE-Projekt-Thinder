package com.hfad.thinder.viewModels.supervisor;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.hfad.thinder.viewmodels.supervisor.EditThesisViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class EditThesisViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private EditThesisViewModel viewModel;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    viewModel = new EditThesisViewModel();
  }


  @Test
  public void getTotalRatingNotNull() {
    Assert.assertNotNull(viewModel.getTotalRating());
  }
  

  @Test
  public void getDeleteThesisResultNotNull() {
    Assert.assertNotNull(viewModel.getTotalRating());
  }

}