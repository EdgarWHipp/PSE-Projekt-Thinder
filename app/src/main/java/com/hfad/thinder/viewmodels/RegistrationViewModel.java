package com.hfad.thinder.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.source.repository.UserRepository;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationViewModel extends ViewModel {
  private static final Pattern PASSWORD_PATTERN =
      Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
  private final UserRepository registrationRepository = UserRepository.getInstance();
  private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
  private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
  private ArrayList<String> testUniversities;//Todo:löschen

  // Position of the item selected by the spinner, i.e. the entry of the university
  private MutableLiveData<Integer> selectedItemPosition;
  private MutableLiveData<ArrayList<String>> universities;

  private MutableLiveData<String> email;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> password;
  private MutableLiveData<String> passwordConfirmation;

  private MutableLiveData<Boolean> registrationSuccessful;


  // This function is called when the user presses the register button
  public void register() {
    registrationResult.setValue(new RegistrationResult("Registration Error", false));
    // Code here should only be executed if registration data is valid

    // This should only be changed to true if registration was successful 
    registrationSuccessful.setValue(true);
  }

  //Ändert den Zustand der Validität der Email und des Passworts
  // Wird in der afterTextChange Methode aufgerufen
  public void registrationDataChanged() {
    registrationFormState.setValue(
        new RegistrationFormState(emailFormatIsValid(), firstNameFormatIsValid(),
            lastNameFormatIsValid(), passwordFormIsValid(), passwordConfirmationFormIsValid()));
  }

//--------------------getter and setter --------------------------------------

  public MutableLiveData<RegistrationFormState> getRegistrationFormState() {
    if (registrationFormState == null) {
      registrationFormState = new MutableLiveData<>();
    }
    return this.registrationFormState;
  }

  public MutableLiveData<RegistrationResult> getRegistrationResult() {
    if (registrationResult == null) {
      registrationResult = new MutableLiveData<>();
    }
    return this.registrationResult;
  }

  public MutableLiveData<Integer> getSelectedItemPosition() {
    if (selectedItemPosition == null) {
      selectedItemPosition = new MutableLiveData<Integer>();
    }
    return selectedItemPosition;
  }

  public MutableLiveData<Boolean> getRegistrationSuccessful(){
    if(registrationSuccessful == null) {
      registrationSuccessful = new MutableLiveData<Boolean>();
      registrationSuccessful.setValue(false);
    }
    return registrationSuccessful;
  }

  public void setSelectedItemPosition(MutableLiveData<Integer> selectedItemPosition) {
    this.selectedItemPosition = selectedItemPosition;
  }

  public MutableLiveData<String> getEmail() {
    if (email == null) {
      email = new MutableLiveData<String>();
    }
    return email;
  }

  public void setEmail(MutableLiveData<String> email) {
    this.email = email;
  }

  public MutableLiveData<String> getFirstName() {
    if (firstName == null) {
      firstName = new MutableLiveData<String>();
    }
    return firstName;
  }

  public void setFirstName(MutableLiveData<String> firstName) {
    this.firstName = firstName;
  }

  public MutableLiveData<String> getLastName() {
    if (lastName == null) {
      lastName = new MutableLiveData<String>();
    }
    return lastName;
  }

  public void setLastName(MutableLiveData<String> lastName) {
    this.lastName = lastName;
  }

  public MutableLiveData<String> getPassword() {
    if (password == null) {
      password = new MutableLiveData<String>();
    }
    return password;
  }

  public void setPassword(MutableLiveData<String> password) {
    this.password = password;
  }

  public MutableLiveData<String> getPasswordConfirmation() {
    if (passwordConfirmation == null) {
      passwordConfirmation = new MutableLiveData<String>();
    }
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(MutableLiveData<String> passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  public void setRegistrationSuccessful(MutableLiveData<Boolean> registrationSuccessful){
    this.registrationSuccessful = registrationSuccessful;
  }

  public MutableLiveData<ArrayList<String>> getUniversities() {
    if (testUniversities == null) {
      testUniversities = new ArrayList<String>();
      testUniversities.add("KIT");
      testUniversities.add("TUM");
    }
    if (universities == null) {
      universities = new MutableLiveData<ArrayList<String>>();
      universities.setValue(testUniversities);
      loadUniversities();
    }
    return universities;
  }

  public void setUniversities(MutableLiveData<ArrayList<String>> universities) {
    this.universities = universities;
  }

  //---------private methods---------------------------------------------------------------------

  private void loadUniversities() {
    //Todo: hole Universitäten aus dem Repository
  }

  private String passwordFormIsValid() {
    if (password.getValue() == null || password.getValue().equals("")) {
      return "Passwortfeld darf nicht leer sein";
    }
    if (password.getValue().length() < 8) {
      return "Password ist zu kurz";
    }
    Matcher m = PASSWORD_PATTERN.matcher(password.getValue());
    if (!m.matches()) {
      return "Passwort muss mindestens eine Zahl und einen Großbuchstaben enthalten";
    }
    return null;
  }

  private String passwordConfirmationFormIsValid() {
    if (passwordConfirmation.getValue() == null || passwordConfirmation.getValue().equals("")) {
      return "Passwort muss bestätigt werden";
      //Todo: seltsamen Bugg beheben
    }
    if (password.getValue() == null ||
        !password.getValue().equals(passwordConfirmation.getValue())) {
      return "Passwörter stimmen nicht überein";
    }
    return null;
  }


  private String emailFormatIsValid() {
    if (email.getValue() == null || email.getValue().equals("")) {
      return "E-Mail muss angegeben werden";
    }
    if (!email.getValue().contains("@")) {
      return "Invalide E-Mail Adresse";
    }
    return null;
  }

  private String firstNameFormatIsValid() {
    if (firstName.getValue() == null || firstName.getValue().equals("")) {
      return "Vorname Muss angegeben werden";
    }
    return null;
  }

  private String lastNameFormatIsValid() {
    if (lastName.getValue() == null || lastName.getValue().equals("")) {
      return "Nachname muss angegeben werden";
    }
    return null;
  }
}
