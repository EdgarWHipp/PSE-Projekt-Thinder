package com.hfad.thinder.viewmodels.supervisor;

import androidx.annotation.Nullable;

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
    private final Integer professor;

    @Nullable
    private final Integer courseOfStudy;

    private final boolean isValid;

    private final boolean hasImages;

    public ThesisFormState(@Nullable Integer titleErrorMessage, @Nullable Integer taskErrorMessage, @Nullable Integer motivationErrorMessage, @Nullable Integer questionsErrorMessage, @Nullable Integer professor, @Nullable Integer courseOfStudy, boolean hasImages) {
        this.titleErrorMessage = titleErrorMessage;
        this.taskErrorMessage = taskErrorMessage;
        this.motivationErrorMessage = motivationErrorMessage;
        this.questionsErrorMessage = questionsErrorMessage;
        this.professor = professor;
        this.courseOfStudy = courseOfStudy;
        this.isValid = (titleErrorMessage == null && taskErrorMessage == null && motivationErrorMessage == null
                && questionsErrorMessage == null && professor == null && courseOfStudy == null);
        this.hasImages = hasImages;
    }

    @Nullable
    public Integer getTitleErrorMessage() {
        return titleErrorMessage;
    }

    @Nullable
    public Integer getTaskErrorMessage() {
        return taskErrorMessage;
    }

    @Nullable
    public Integer getMotivationErrorMessage() {
        return motivationErrorMessage;
    }

    @Nullable
    public Integer getQuestionsErrorMessage() {
        return questionsErrorMessage;
    }

    @Nullable
    public Integer getProfessor() {
        return professor;
    }

    @Nullable
    public Integer getCourseOfStudy() {
        return courseOfStudy;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean hasImages() {
        return hasImages;
    }
}
