package com.hfad.thinder.viewmodels;

/**
 * The different types of results a call to the model can produce.
 */
public enum ViewModelResultTypes {
  /**
   * the call to the model did not produce an error message, signals to the ui to switch from the
   * user mode to the student mode.
   */
  STUDENT,

  /**
   * the call to the model did not produce an error message, signals to the ui to switch from the
   * user mode to the supervisor mode.
   */
  SUPERVISOR,

  /**
   * the call to the model did not produce an error message, signals to the ui to remain in
   * user mode instead of switching to supervisor or student.
   */
  UNVERIFIED,

  /**
   * the call to the model produced no error message, the ui is already in supervisor or student
   * mode.
   */
  SUCCESSFUL,

  /**
   * the call to the model produced an error message
   */
  ERROR
}
