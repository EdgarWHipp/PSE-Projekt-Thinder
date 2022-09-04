package com.hfad.thinder.viewModels.supervisor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.supervisor.EditProfileViewModel;
import org.junit.After;
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
    viewModel.setBuilding(new MutableLiveData<>("Building"));
    viewModel.setRoom(new MutableLiveData<>("Room"));
    viewModel.setPhoneNumber(new MutableLiveData<>("01626221223"));
    viewModel.setInstitute(new MutableLiveData<>("Institut"));
  }

  @After
  public void tearDown() throws Exception {
    viewModel = null;
  }

  @Test
  public void profileDataValid() {
    viewModel.profileDataChanged();
    assertTrue(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidFirstName() {
    viewModel.setFirstName(new MutableLiveData<>(""));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getFirstNameErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setFirstName(new MutableLiveData<>(null));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getFirstNameErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidLastName() {
    viewModel.setLastName(new MutableLiveData<>(""));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getLastNameErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setLastName(new MutableLiveData<>(null));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getLastNameErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidBuilding() {
    viewModel.setBuilding(new MutableLiveData<>(""));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getBuildingErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setBuilding(new MutableLiveData<>(null));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getBuildingErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidRoom() {
    viewModel.setRoom(new MutableLiveData<>(""));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getRoomErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setRoom(new MutableLiveData<>(null));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getRoomErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidPhoneNumber() {
    viewModel.setPhoneNumber(new MutableLiveData<>(""));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getPhoneNumberErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setPhoneNumber(new MutableLiveData<>(null));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getPhoneNumberErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setPhoneNumber(new MutableLiveData<>("12345678912434354"));//to long
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getPhoneNumberErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setPhoneNumber(new MutableLiveData<>("1234"));//to short
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getPhoneNumberErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setPhoneNumber(new MutableLiveData<>("af456afg"));//letters in number
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getPhoneNumberErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void invalidInstitut() {
    viewModel.setInstitute(new MutableLiveData<>(""));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getInstituteErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());

    viewModel.setInstitute(new MutableLiveData<>(null));
    viewModel.profileDataChanged();
    assertNotNull(viewModel.getFormState().getValue().getInstituteErrorMessage());
    assertFalse(viewModel.getFormState().getValue().isDataValid());
  }

  @Test
  public void getFormStateNotNull() {
    assertNotNull(viewModel.getFormState());
  }

  @Test
  public void getSafeResultNotNull() {
    assertNotNull(viewModel.getFormState());
  }

  @Test
  public void getDeleteResultNotNull() {
    assertNotNull(viewModel.getDeleteResult());
  }

  /*
  @Test
  public void getAcademicTitlesNotNull() {
    assertNotNull(viewModel.getAcademicTitles());
  }


  @Test
  public void getSelectedAcademicTitlePosition() {
  }
  geht nicht weil Repository nicht gemockt werden kann
   */


  @Test
  public void setSelectedAcademicTitlePosition() {
    viewModel.setSelectedAcademicTitlePosition(new MutableLiveData<>(1));
    assertEquals(Integer.valueOf(1), viewModel.getSelectedAcademicTitlePosition().getValue());
  }

  @Test
  public void getFirstName() {
    assertEquals("firstName", viewModel.getFirstName().getValue());
  }

  @Test
  public void setFirstName() {
    String firstName = "Hans";
    viewModel.setFirstName(new MutableLiveData<>(firstName));
    assertEquals(firstName, viewModel.getFirstName().getValue());
  }

  @Test
  public void getLastName() {
    assertEquals("lastName", viewModel.getLastName().getValue());
  }

  @Test
  public void setLastName() {
    String lastName = "Wurst";
    viewModel.setLastName(new MutableLiveData<>(lastName));
    assertEquals(lastName, viewModel.getLastName().getValue());
  }

  @Test
  public void getBuilding() {
    assertEquals("Building", viewModel.getBuilding().getValue());
  }

  @Test
  public void setBuilding() {
    String building = "Tower";
    viewModel.setBuilding(new MutableLiveData<>(building));
    assertEquals(building, viewModel.getBuilding().getValue());
  }

  @Test
  public void getRoom() {
    assertEquals("Room", viewModel.getRoom().getValue());
  }

  @Test
  public void setRoom() {
    String room = "chamber";
    viewModel.setRoom(new MutableLiveData<>(room));
    assertEquals(room, viewModel.getRoom().getValue());
  }

  @Test
  public void getPhoneNumber() {
    assertEquals("01626221223", viewModel.getPhoneNumber().getValue());
  }

  @Test
  public void setPhoneNumber() {
    String phoneNumber = "234233434";
    viewModel.setPhoneNumber(new MutableLiveData<>(phoneNumber));
    assertEquals(phoneNumber, viewModel.getPhoneNumber().getValue());
  }

  @Test
  public void getInstitute() {
    assertEquals("Institut", viewModel.getInstitute().getValue());
  }

  @Test
  public void setInstitute() {
    String institut = "megastitut";
    viewModel.setInstitute(new MutableLiveData<>(institut));
    assertEquals(institut, viewModel.getInstitute().getValue());
  }
}