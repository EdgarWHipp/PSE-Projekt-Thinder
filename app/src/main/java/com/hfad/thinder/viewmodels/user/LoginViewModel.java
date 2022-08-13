package com.hfad.thinder.viewmodels.user;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;


//Todo: Javadoc schreiben.
public class LoginViewModel extends ViewModel {

    private MutableLiveData<ViewModelResult> loginResult = new MutableLiveData<>();
    private UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<Boolean> isDataValid = new MutableLiveData<>();
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;

    //Ruft die Login Funktion im Repository auf und aktualisiert den Zustand der Anmeldung
    public void login() {

        Result result;
        result = userRepository.login(password.getValue(), email.getValue());

        if (!result.getSuccess()) {
            loginResult.setValue(
                    new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
        } else if (result.getSuccess()) {
            userRepository.setPassword(password.getValue());
            USERTYPE usertype = userRepository.getType();
            if (usertype == USERTYPE.STUDENT) {
                loginResult.setValue(
                        new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.STUDENT));
            } else if (usertype == USERTYPE.SUPERVISOR) {
                loginResult.setValue(
                        new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.SUPERVISOR));
            } //Todo: unverified fehlt noch

        }


    }

    public void loginDataChanged() {
        isDataValid.setValue(
                email.getValue() != null && password.getValue() != null &&
                        !email.getValue().equals("") &&
                        !password.getValue().equals(""));
    }


    //----------- getter and setter --------------------------------------

    public MutableLiveData<Boolean> getIsDataValid() {
        if (isDataValid == null) {
            isDataValid = new MutableLiveData<>();
        }
        return isDataValid;
    }

    public MutableLiveData<ViewModelResult> getLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();
        }
        return this.loginResult;
    }

    public MutableLiveData<String> getEmail() {
        if (email == null) {
            email = new MutableLiveData<String>();
        }
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
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


}
