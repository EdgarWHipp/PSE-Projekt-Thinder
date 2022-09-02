package com.hfad.thinder.viewmodels;

import java.util.UUID;

/**
 * {@link CourseOfStudyItem} is used in the {@link com.hfad.thinder.ui.CoursesOfStudyAdapter} and
 * stores all necessary data for an item in the recyclerview
 */
public class CourseOfStudyItem {
    private final UUID uuid;
    private final String courseOfStudy;
    private boolean picked;

    /**
     * Constructor
     *
     * @param courseOfStudy name of the course of study
     * @param uuid          unique id from the database
     * @param picked        picked by the user
     */
    public CourseOfStudyItem(String courseOfStudy, UUID uuid, boolean picked) {
        this.courseOfStudy = courseOfStudy;
        this.picked = picked;
        this.uuid = uuid;
    }

    /**
     * CourseOfStudy getter
     *
     * @return name of the course of study
     */
    public String getCourseOfStudy() {
        return courseOfStudy;
    }

    /**
     * Picked getter
     *
     * @return whether course of study has been picked
     */
    public boolean isPicked() {
        return picked;
    }

    /**
     * picks course of study
     *
     * @param picked whether course of study has been picked
     */
    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    /**
     * UUID getter
     *
     * @return unique id associated with the course of study from the backend database
     */
    public UUID getUuid() {
        return uuid;
    }
}
