package com.hfad.thinder.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationViewModel extends ViewModel {
  private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
  private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
  private UserRepository registrationRepository = UserRepository.getInstance();
  private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])");

  private ArrayList<String> testUniversities;//Todo:löschen

  // Position of the item selected by the spinner, i.e. the entry of the university
  private MutableLiveData<Integer> selectedItemPosition;
  private MutableLiveData<ArrayList<String>> universities;

  private MutableLiveData<String> email;
  private MutableLiveData<String> firstName;
  private MutableLiveData<String> lastName;
  private MutableLiveData<String> password;
  private MutableLiveData<String> passwordConfirmation;


  // This function is called when the user presses the register button
  public void register() {
    registrationResult.setValue(new RegistrationResult("Registration Error", false));
  }

  //Ändert den Zustand der Validität der Email und des Passworts
  // Wird in der afterTextChange Methode aufgerufen
  public void registrationDataChanged() {
    if (passwordFormIsValid() && emailFormatIsValid() && formIsFull()) {
      registrationFormState.setValue(new RegistrationFormState(true));
    }
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

  public void setSelectedItemPosition(MutableLiveData<Integer> selectedItemPosition) {
    this.selectedItemPosition = selectedItemPosition;
  }

  public void setUniversities(MutableLiveData<ArrayList<String>> universities) {
    this.universities = universities;
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

  //---------private methods---------------------------------------------------------------------

  private void loadUniversities() {
    //Todo: hole Universitäten aus dem Repository
  }

  private boolean passwordFormIsValid() {
      if (password == null) {
        registrationFormState.setValue(new RegistrationFormState(null, "Passwort ist Null", null, false));
        return false;
      }
      if (password.getValue().length() < 8) {
        registrationFormState.setValue(new RegistrationFormState(null, "Passwort is zu kurz", null, false));
        return false;
      }
      if (!PASSWORD_PATTERN.matcher(password.getValue()).matches()) {
        registrationFormState.setValue(new RegistrationFormState(null, "Passwort muss mindestens eine Zahl und einen Großbuchstaben enthalten", null, false));
        return false;
      }
      if (!password.getValue().equals(passwordConfirmation.getValue())) {
        registrationFormState.setValue(new RegistrationFormState(null, "Passwörter stimmen nicht überein", null, false));
      }
      return true;
    }


    private boolean emailFormatIsValid() {
      if (!email.getValue().contains("@")) {
        registrationFormState.setValue(new RegistrationFormState("Invalide E-Mail Adresse", null, null, false));
        return false;
      }
      return true;
    }

    private boolean formIsFull() {
      if (firstName == null || lastName == null || selectedItemPosition == null) {
        registrationFormState.setValue(new RegistrationFormState(null, null, "Alle Felder müssen ausgefüllt sein", false));
        return false;
      }
    return true;
    }
  }
