package com.hfad.thinder.data.source.result;

import androidx.annotation.Nullable;

/**
 * A result class with an error message and a success value.
 */
public class Result {
    @Nullable
    private final int errorMessage;

    private final boolean success;

    public Result(@Nullable Integer errorMessage, boolean success) {
        this.errorMessage = errorMessage;
        this.success = success;
    }

    public Result(boolean success) {
        this.errorMessage = Integer.parseInt(null);
        this.success = success;
    }

    @Nullable
    public Integer getErrorMessage() {
        return this.errorMessage;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
