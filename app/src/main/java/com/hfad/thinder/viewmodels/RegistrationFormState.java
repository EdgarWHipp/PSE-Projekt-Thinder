package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

// Klasse gibt an, ob die Eingabe bei der Registrierung einem bestimmten format entspricht.
public class RegistrationFormState {

    @Nullable
    private String passwordError;

    @Nullable
    private String emailError;

    private boolean isValid;

    public RegistrationFormState(boolean isValid) {
        this.isValid = isValid;
    }
    public RegistrationFormState(String emailError, String passwordError, boolean isValid) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isValid = isValid;
    }


    public boolean isDataValid() {
        return isValid;
    }

    @Nullable
    public String getPasswordError() {
        return passwordError;
    }

    @Nullable
    public String getEmailError() {
        return  this.emailError;
    }
}
