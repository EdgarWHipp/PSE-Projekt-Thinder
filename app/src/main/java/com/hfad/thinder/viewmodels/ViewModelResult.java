package com.hfad.thinder.viewmodels;

import androidx.annotation.Nullable;

import com.hfad.thinder.viewmodels.ViewModelResultTypes;

//Enthält informationen darüber, ob der Login erfolgreich war
public class ViewModelResult {

    @Nullable
    private final String errorMessage;

    @Nullable
    private final ViewModelResultTypes viewModelResultTypes;


    public ViewModelResult(@Nullable String errorMessage, @Nullable
            ViewModelResultTypes viewModelResultTypes) {
        this.errorMessage = errorMessage;
        this.viewModelResultTypes = viewModelResultTypes;
    }


    @Nullable
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Nullable
    public ViewModelResultTypes getSuccess() {
        return this.viewModelResultTypes;
    }

    public boolean isSuccess() {
        return this.viewModelResultTypes == ViewModelResultTypes.SUCCESSFUL;
    }
}
