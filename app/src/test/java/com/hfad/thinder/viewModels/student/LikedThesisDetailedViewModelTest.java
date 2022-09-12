package com.hfad.thinder.viewModels.student;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.viewmodels.student.LikedThesisDetailedViewModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class LikedThesisDetailedViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private LikedThesisDetailedViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    viewModel = new LikedThesisDetailedViewModel();
  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }

  @Test
  public void getCurrentImageNotNull() {
    Assert.assertNotNull(viewModel.getCurrentImage());
  }


  @Test
  public void getTitle() {
    Assert.assertNotNull(viewModel.getTitle());
  }

  @Test
  public void getCoursesOfStudyNotNull() {
    Assert.assertNotNull(viewModel.getCoursesOfStudy());
  }

  @Test
  public void getTask() {
    Assert.assertNotNull(viewModel.getTask());
  }

  @Test
  public void getMotivation() {
    Assert.assertNotNull(viewModel.getMotivation());
  }

  @Test
  public void getSupervisorName() {
    Assert.assertNotNull(viewModel.getSupervisorName());
  }

  @Test
  public void getPhoneNumber() {
    Assert.assertNotNull(viewModel.getPhoneNumber());
  }

  @Test
  public void getBuilding() {
    Assert.assertNotNull(viewModel.getBuilding());
  }

  @Test
  public void getSupervisingProf() {
    Assert.assertNotNull(viewModel.getSupervisingProf());
  }

  @Test
  public void getInstitute() {
    Assert.assertNotNull(viewModel.getInstitute());
  }

  @Test
  public void getMail() {
    Assert.assertNotNull(viewModel.getMail());
  }

  @Test
  public void getDeleteResult() {
    Assert.assertNotNull(viewModel.getDeleteResult());
  }


}