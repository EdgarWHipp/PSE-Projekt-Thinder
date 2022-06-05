package com.hfad.thinder.viewmodels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.regex.Pattern;

//Todo: Javadoc schreiben.
public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private Repository loginRepository = new Repository(); //Todo: Richtiges Repository fehlt



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



}
