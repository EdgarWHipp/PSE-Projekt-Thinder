package com.hfad.thinder.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.regex.Pattern;

public class RegistrationViewModel extends ViewModel {

  //private Repository registrationRepository = new Repository(); //Todo: Richtiges Repository fehlt
  private static final Pattern PASSWORD_PATTERN = Pattern.compile("");//Todo: geeignetes Pattern überlegen
  private MutableLiveData<List<String>> universities;
  private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
  private MutableLiveData<RegistrationResult> registrationResult = new MutableLiveData<>();

  public void register(String email, String firstName, String lastName, String password, String university) {
    // Result result = registrationRepository.register(email, firstName, lastName, password, university);
    //Todo: result aus Repo in registrationResult verwalten
  }

  public MutableLiveData<List<String>> getUniversities() {
    if (universities == null) {
      this.universities = new MutableLiveData<List<String>>();
      loadUniversities();
    }
    return universities;
  }

  //Ändert den Zustand der Validität der Email und des Passworts
  public void registrationDataChanged(String email, String password) {
    if (passwordFormIsValid(password) && emailFormatIsValid(email)) {
      registrationFormState.setValue(new RegistrationFormState(true));
    }
  }

  private void loadUniversities() {
    //Todo: hole Universitäten aus dem Repository
  }


  private boolean passwordFormIsValid(String password) {
    if (password == null) {
      registrationFormState.setValue(new RegistrationFormState(null, "Passwort ist Null", false));
      return false;
    }
    if (password.length() < 8) {
      registrationFormState.setValue(new RegistrationFormState(null, "Passwort is zu kurz", false));
      return false;
    }
    if (!PASSWORD_PATTERN.matcher(password).matches()) {
      registrationFormState.setValue(new RegistrationFormState(null, "Passwort muss mindestens eine Zahl und einen Großbuchstaben enthalten", false));
      return false;
    }
    return true;
  }

  private boolean emailFormatIsValid(String email) {
    if (!email.contains("@")) {
      registrationFormState.setValue(new RegistrationFormState("Invalide E-Mail Adresse", null, false));
      return false;
    }
    return true;
  }
}
