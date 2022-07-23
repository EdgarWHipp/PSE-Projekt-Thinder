package com.hfad.thinder.ui;

import java.util.ArrayList;

public class SwipeScreenCard {

    private ArrayList<Integer> images;
    private String title;
    private String task;
    private String motivation;
    private String professor;
    private ArrayList<String> coursesOfStudy;
    private String supervisorFirstName;
    private String supervisorLastName;
    private String building;
    private String roomNumber;
    private String phoneNumber;
    private String institute;
    private Rating rating;

    public SwipeScreenCard(ArrayList<Integer> images, String title, String task, String motivation, String professor, ArrayList<String> coursesOfStudy, String supervisorFirstName, String supervisorLastName, String building, String roomNumber, String phoneNumber, String institute) {
        this.images = images;
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
        this.rating = Rating.UNRATED;
    }

    // Todo: remove
    public SwipeScreenCard(String title) {
        this.title = title;
    }

    public SwipeScreenCard(ArrayList<Integer> images, String title) {
        this.images = images;
        this.title = title;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    public String getTask() {
        return task;
    }

    public String getMotivation() {
        return motivation;
    }

    public String getProfessor() {
        return professor;
    }

    public ArrayList<String> getCoursesOfStudy() {
        return coursesOfStudy;
    }

    public String getSupervisorFirstName() {
        return supervisorFirstName;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInstitute() {
        return institute;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public enum Rating {
        UNRATED,
        LIKED,
        DISLIKED
    }
}


