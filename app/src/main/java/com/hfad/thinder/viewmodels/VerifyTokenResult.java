package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

public class VerifyTokenResult {

    @Nullable
    private String errorMessage;

    @Nullable
    private VerifyTokenResultTypes success;

    public VerifyTokenResult(String errorMessage, String success) {
        this.errorMessage = errorMessage;
        this.success = VerifyTokenResultTypes.setResult(success);
    }

    @Nullable
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Nullable
    public VerifyTokenResultTypes getSuccess() {
        return this.success;
    }

}
