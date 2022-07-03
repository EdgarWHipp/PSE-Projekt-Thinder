package com.hfad.thinder.viewmodels;

/**
 * The different states the ConfirmPasswordActivity can be in.
 * Used by the XML layout to show the appropriate views and change attributes accordingly.
 */
public enum VerifyTokenStates {
    IDLE, // The user has not yet pressed the verify button
    LOADING, // The verification is currently ongoing
    SUCCESSFUL, // the verification was successful
    FAILURE // The verification failed
}
