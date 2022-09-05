package com.hfad.thinder.viewModels.student;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.hfad.thinder.viewmodels.student.StudentViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class StudentViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private StudentViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    viewModel = new StudentViewModel();
  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }

  @Test
  public void getProfileComplete() {
    //ruft loadPfoileComplete() auf, UserRepository muss gemockt werde.
  }

  @Test
  public void setProfileComplete() {
    //überprüfung unmöglich (diese Methode setzt den Wert, nachdem exakt dieser Wert aus dem Backend geholt wurde.
    
  }
}