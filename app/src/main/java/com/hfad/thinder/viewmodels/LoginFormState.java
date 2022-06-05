package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

// Klasse gibt an, ob die Eingabe beim Login einem bestimmten format entspricht.
public class LoginFormState {

    @Nullable
    private String passwordError;
    private boolean isValid;

    public LoginFormState(boolean isValid) {
        this.isValid = isValid;
    }
    public LoginFormState(String passwordError, boolean isValid) {
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
}
