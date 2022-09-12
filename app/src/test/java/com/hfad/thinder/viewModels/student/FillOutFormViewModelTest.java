package com.hfad.thinder.viewModels.student;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.student.FillOutFormViewModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class FillOutFormViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private FillOutFormViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    viewModel = new FillOutFormViewModel();
  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }

  @Test
  public void sendForm() {
    //Aufruf an Async Task
  }

  @Test
  public void formDataValid() {
    //get questions darf nicht null sein. kann nicht einfach gesetzt werden
  }

  @Test
  public void getQuestionsNotNull() {
    Assert.assertNotNull(viewModel.getQuestions());
  }

  @Test
  public void getAnswersNotNull() {
    Assert.assertNotNull(viewModel.getAnswers());
  }

  @Test
  public void setAnswers() {
    String answers = "answers";
    viewModel.setAnswers(new MutableLiveData<>(answers));
    Assert.assertEquals(answers, viewModel.getAnswers().getValue());
  }

  @Test
  public void setThesisId() {
    //aufruf von AsyncTask
  }

  @Test
  public void getIsDataValidNotNull() {
    Assert.assertNotNull(viewModel.getIsDataValid());
  }

  @Test
  public void getSendResultNotNull() {
    Assert.assertNotNull(viewModel.getSendResult());
  }
}