package com.hfad.thinder.viewmodels.supervisor;

import androidx.annotation.Nullable;

/**
 * Class that represents state of validity of the data entered in the {@link ThesisViewModel}.
 */
public class ThesisFormState {

  @Nullable
  private final Integer titleErrorMessage;

  @Nullable
  private final Integer taskErrorMessage;

  @Nullable
  private final Integer motivationErrorMessage;

  @Nullable
  private final Integer questionsErrorMessage;

  @Nullable
  private final Integer professorErrorMessage;

  @Nullable
  private final Integer courseOfStudyErrorMessage;

  private final boolean isValid;

  private final boolean hasImages;

  /**
   * @param titleErrorMessage         android string resource id for an error message, should be null if there is no error
   * @param taskErrorMessage          android string resource id for an error message, should be null if there is no error
   * @param motivationErrorMessage    android string resource id for an error message, should be null if there is no error
   * @param questionsErrorMessage     android string resource id for an error message, should be null if there is no error
   * @param professorErrorMessage     android string resource id for an error message, should be null if there is no error
   * @param courseOfStudyErrorMessage android string resource id for an error message, should be null if there is no error
   * @param hasImages                 android string resource id for an error message, should be null if there is no error
   */
  public ThesisFormState(@Nullable Integer titleErrorMessage, @Nullable Integer taskErrorMessage,
                         @Nullable Integer motivationErrorMessage,
                         @Nullable Integer questionsErrorMessage,
                         @Nullable Integer professorErrorMessage,
                         @Nullable Integer courseOfStudyErrorMessage, boolean hasImages) {
    this.titleErrorMessage = titleErrorMessage;
    this.taskErrorMessage = taskErrorMessage;
    this.motivationErrorMessage = motivationErrorMessage;
    this.questionsErrorMessage = questionsErrorMessage;
    this.professorErrorMessage = professorErrorMessage;
    this.courseOfStudyErrorMessage = courseOfStudyErrorMessage;
    this.isValid =
        (titleErrorMessage == null && taskErrorMessage == null && motivationErrorMessage == null
            && questionsErrorMessage == null && professorErrorMessage == null &&
            courseOfStudyErrorMessage == null);
    this.hasImages = hasImages;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getTitleErrorMessage() {
    return titleErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getTaskErrorMessage() {
    return taskErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getMotivationErrorMessage() {
    return motivationErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getQuestionsErrorMessage() {
    return questionsErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getProfessorErrorMessage() {
    return professorErrorMessage;
  }

  /**
   * @return a resource id for a android string resource.
   */
  @Nullable
  public Integer getCourseOfStudyErrorMessage() {
    return courseOfStudyErrorMessage;
  }

  /**
   * @return true if all error messages are null
   */
  public boolean isValid() {
    return isValid;
  }

  /**
   * @return true if the supervisor has added images to the thesis.
   */
  public boolean hasImages() {
    return hasImages;
  }
}
