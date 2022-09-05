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
  public void like() {

  }

  @Test
  public void dislike() {
  }

  @Test
  public void redraw() {
  }

  @Test
  public void pushRatings() {
  }

  @Test
  public void incrementCurrentDetailViewPosition() {
  }

  @Test
  public void decrementCurrentDetailViewPosition() {
  }

  @Test
  public void getCurrentCardNotNull() {

  }

  @Test
  public void getNextCard() {
  }

  @Test
  public void getCurrentDetailViewState() {
  }

  @Test
  public void getCurrentDetailViewImage() {
  }

  @Test
  public void getCurrentDeckPositionNotNull() {
    Assert.assertNotNull(viewModel.getCurrentDeckPosition());
  }

  @Test
  public void getCurrentImageIsNull() {
    Assert.assertNull(viewModel.getCurrentImage());
  }

  @Test
  public void getNextImageIsNull() {
    Assert.assertNull(viewModel.getNextImage());
  }

  @Test
  public void getCoursesOfStudy() {
    Assert.assertNull(viewModel.getCoursesOfStudy());
  }

  @Test
  public void getSuperVisorName() {
    //gibt exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getPhoneNumber() {
    //wift exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getBuilding() {
    //wirft exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getMail() {
    //wirft exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getProfessorName() {
    //wirft exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getInstitute() {
    //wirft exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getCurrentTask() {
    //wirft exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getCurrentMotivation() {
    //wirft exception, wenn getCurrentCard() null zurückgibt
  }

  @Test
  public void getIsLoadingNotNull() {
    Assert.assertNotNull(viewModel.getIsLoading());
  }
}