package com.hfad.thinder.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.regex.Pattern;

//Todo: Javadoc schreiben.
public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private Repository loginRepository = new Repository(); //Todo: Richtiges Repository fehlt

    private static final Pattern PASSWORD_PATTERN = Pattern.compile();//Todo: geeignetes Pattern überlegen


    public MutableLiveData<LoginFormState> getLoginFormState() {
        return this.loginFormState;
    }

    public MutableLiveData<LoginResult> getLoginResult() {
        return this.loginResult;
    }

    //Ruft die Login Funktion im Repository auf und aktualisiert den Zustand der Anmeldung
    public void login(String email, String password) {
        Result restult = loginRepository.login(email, password);
        //Todo: Implementiere Festlegung des loginResults, wenn Implementierung des Repositories bekannt ist
    }

    //Ändert den Zustand der Validität der Email und des Passworts
    public void loginDataChanged(String email, String password) {
        if (passwordFormIsValid(password)) {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean passwordFormIsValid(String password) {
        if (password == null){
            loginFormState.setValue(new LoginFormState("Passwort ist Null",false));
            return false;
        }
        if(password.length() < 8) {
            loginFormState.setValue(new LoginFormState("Passwort is zu kurz", false));
            return false;
        }
        if(!PASSWORD_PATTERN.matcher(password).matches()) {
            loginFormState.setValue(new LoginFormState("Passwort muss mindestens eine Zahl und einen Großbuchstaben enthalten", false));
        return false;
        }
        return true;
    }

    //Todo: Eventuell Prüfung des E-Mail Formats













}
