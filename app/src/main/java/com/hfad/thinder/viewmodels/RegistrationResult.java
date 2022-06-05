package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

//Enthält informationen darüber, ob die Registrierung erfolgreich war
public class RegistrationResult {

    @Nullable
    private String errorMessage;

    private boolean success;

    public RegistrationResult(String errorMessage, boolean success) {
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public RegistrationResult(boolean success) {
        this.success = success;
    }

    @Nullable
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
