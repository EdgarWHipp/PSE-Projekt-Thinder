package com.hfad.thinder.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


//Todo: Javadoc schreiben.
public class LoginViewModel extends ViewModel {
  private LiveData<LoginResult> loginResult = new MutableLiveData<>();
  // private Repository loginRepository = new Repository(); //Todo: Richtiges Repository fehlt

  private MutableLiveData<String> email;
  private MutableLiveData<String> password;
  private MutableLiveData<String> errorMessage;

  /**
   * public MutableLiveData<LoginResult> getLoginResult() {
   * return this.loginResult;
   * }
   **/

  public MutableLiveData<String> getEmail() {
    if (email == null) {
      email = new MutableLiveData<String>();
    }
    return email;
  }

  public void setEmail(String email) {
    this.email.setValue(email);
  }

  public LiveData<String> getErrorMessage() {
    if (errorMessage == null) {
      errorMessage = new MutableLiveData<String>();
    }
    return errorMessage;
  }

  public MutableLiveData<String> getPassword() {
    if (password == null) {
      password = new MutableLiveData<String>();
    }
    return password;
  }

  public void setPassword(String password) {
    this.password.setValue(password);
  }

  //Ruft die Login Funktion im Repository auf und aktualisiert den Zustand der Anmeldung
  public void login() {
    // use email.getValue() and password.getValue() to access the Strings of the LiveData object
    // Result restult = loginRepository.login(email, password);
    /**
     * Todo: Implementiere Festlegung des loginResults, wenn Implementierung des Repositories bekannt ist
     * Todo: muss im Falle eines Fehlers das errorMessage Attribut entsprechend anpassen
     */
    // For testing purposes display saved password and email as error
    errorMessage.setValue(email.getValue() + " " + password.getValue());
  }

}
