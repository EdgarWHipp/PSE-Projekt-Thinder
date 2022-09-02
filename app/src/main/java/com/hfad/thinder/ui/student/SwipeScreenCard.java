package com.hfad.thinder.ui.student;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Stores all necessary data that is displayed in the {@link SwipeScreenCard}
 */
public class SwipeScreenCard {

    private final ArrayList<Bitmap> images;
    private final java.util.UUID UUID;
    private final String title;
    private final String task;
    private final String motivation;
    private final String professor;
    private final ArrayList<String> coursesOfStudy;
    private final String supervisorFirstName;
    private final String supervisorLastName;
    private final String building;
    private final String roomNumber;
    private final String phoneNumber;
    private final String institute;
    private final String mail;
    private final String academicDegree;

    /**
     * Constructor
     *
     * @param images                images chosen by the supervisor
     * @param UUID                  unique id from the backend database
     * @param title                 title of the thesis
     * @param task                  task of the thesis
     * @param motivation            motivation of the thesis
     * @param professor             professor supervising the thesis
     * @param coursesOfStudy        courses of study chosen by the supervisor
     * @param supervisorFirstName   first name of the supervisor
     * @param supervisorLastName    last name of the supervisor
     * @param building              building in which the supervisor has his/her office
     * @param roomNumber            supervisor office
     * @param phoneNumber           supervisor phone number
     * @param institute             institute supervising the thesis
     * @param mail                  supervisor mail
     * @param academicDegree        supervisor degree
     */
    public SwipeScreenCard(ArrayList<Bitmap> images, java.util.UUID UUID, String title, String task, String motivation, String professor, ArrayList<String> coursesOfStudy, String supervisorFirstName, String supervisorLastName, String building, String roomNumber, String phoneNumber, String institute, String mail, String academicDegree) {
        this.images = images;
        this.UUID = UUID;
        this.title = title;
        this.task = task;
        this.motivation = motivation;
        this.professor = professor;
        this.coursesOfStudy = coursesOfStudy;
        this.supervisorFirstName = supervisorFirstName;
        this.supervisorLastName = supervisorLastName;
        this.building = building;
        this.roomNumber = roomNumber;
        this.phoneNumber = phoneNumber;
        this.institute = institute;
        this.mail = mail;
        this.academicDegree = academicDegree;
    }

    /**
     * image list getter
     *
     * @return list of images selected by the supervisor
     */
    public ArrayList<Bitmap> getImages() {
        return images;
    }

    /**
     *
     * @return title of the thesis
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return task of the thesis
     */
    public String getTask() {
        return task;
    }

    /**
     *
     * @return motivation of the thesis
     */
    public String getMotivation() {
        return motivation;
    }

    /**
     *
     * @return professor supervising the thesis
     */
    public String getProfessor() {
        return professor;
    }

    /**
     *
     * @return courses of study chosen by the supervisor
     */
    public ArrayList<String> getCoursesOfStudy() {
        return coursesOfStudy;
    }

    /**
     *
     * @return first name of the supervisor
     */
    public String getSupervisorFirstName() {
        return supervisorFirstName;
    }

    /**
     *
     * @return last name of the supervisor
     */
    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    /**
     *
     * @return building in which the supervisor has his/her office
     */
    public String getBuilding() {
        return building;
    }

    /**
     *
     * @return supervisor office
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     *
     * @return supervisor phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return institute supervising the thesis
     */
    public String getInstitute() {
        return institute;
    }

    /**
     *
     * @return unique id from the backend database
     */
    public java.util.UUID getUUID() {
        return UUID;
    }

    /**
     *
     * @return supervisor mail
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @return supervisor degree
     */
    public String getAcademicDegree() {
        return academicDegree;
    }
}


