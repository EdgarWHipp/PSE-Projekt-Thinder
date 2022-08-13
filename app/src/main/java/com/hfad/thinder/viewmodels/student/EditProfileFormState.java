package com.hfad.thinder.viewmodels.student;

import androidx.annotation.Nullable;

public class EditProfileFormState {
    @Nullable
    private final Integer firstNameErrorMessage;

    @Nullable
    private final Integer lastNameErrorMessage;

    @Nullable
    private final Integer coursesOfStudyErrorMessage;

    private final boolean isValid;

    public EditProfileFormState(@Nullable Integer firstNameErrorMessage,
                                @Nullable Integer lastNameErrorMessage,
                                @Nullable Integer coursesOfStudyErrorMessage) {
        this.firstNameErrorMessage = firstNameErrorMessage;
        this.lastNameErrorMessage = lastNameErrorMessage;
        this.coursesOfStudyErrorMessage = coursesOfStudyErrorMessage;
        this.isValid = (firstNameErrorMessage == null && lastNameErrorMessage == null &&
                coursesOfStudyErrorMessage == null);
    }

    @Nullable
    public Integer getFirstNameErrorMessage() {
        return this.firstNameErrorMessage;
    }

    @Nullable
    public Integer getLastNameErrorMessage() {
        return this.lastNameErrorMessage;
    }

    @Nullable
    public Integer getCoursesOfStudyErrorMessage() {
        return coursesOfStudyErrorMessage;
    }

    public boolean isDataValid() {
        return this.isValid;
    }
}
