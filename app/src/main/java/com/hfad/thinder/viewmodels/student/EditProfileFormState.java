package com.hfad.thinder.viewmodels.student;

import androidx.annotation.Nullable;

/**
 * Class that represents state of validity of the data entered in the {@link com.hfad.thinder.ui.student.StudentProfileFragment}
 */
public class EditProfileFormState {
  @Nullable
  private final Integer firstNameErrorMessage;

  @Nullable
  private final Integer lastNameErrorMessage;

  @Nullable
  private final Integer coursesOfStudyErrorMessage;

  private final boolean isValid;

  /**
   * Constructor for class EditProfileFormState
   *
   * @param firstNameErrorMessage      android string resource id for an error message, should be null if there is no error
   * @param lastNameErrorMessage       android string resource id for an error message, should be null if there is no error
   * @param coursesOfStudyErrorMessage android string resource id for an error message, should be null if there is no error
   */
  public EditProfileFormState(@Nullable Integer firstNameErrorMessage,
                              @Nullable Integer lastNameErrorMessage,
                              @Nullable Integer coursesOfStudyErrorMessage) {
    this.firstNameErrorMessage = firstNameErrorMessage;
    this.lastNameErrorMessage = lastNameErrorMessage;
    this.coursesOfStudyErrorMessage = coursesOfStudyErrorMessage;
    this.isValid = (firstNameErrorMessage == null && lastNameErrorMessage == null &&
        coursesOfStudyErrorMessage == null);
  }

  /**
   * @return in case of an error in the first name the android string resource id for an corresponding error message, null otherwise.
   */
  @Nullable
  public Integer getFirstNameErrorMessage() {
    return this.firstNameErrorMessage;
  }

  /**
   * @return in case of an error in the last name the android string resource id for an corresponding error message, null otherwise.
   */
  @Nullable
  public Integer getLastNameErrorMessage() {
    return this.lastNameErrorMessage;
  }

  /**
   * @return in case there are no courses of study selected the android string resource id for an corresponding error message, null otherwise.
   */
  @Nullable
  public Integer getCoursesOfStudyErrorMessage() {
    return coursesOfStudyErrorMessage;
  }

  /**
   * @return true if there are no errors, false otherwise
   */
  public boolean isDataValid() {
    return this.isValid;
  }
}
