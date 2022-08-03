package com.hfad.thinder.ui;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class SwipeScreenCard {

    private ArrayList<Bitmap> images;
    private String UUID;
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
    private String email;
    private Integer id;

    public SwipeScreenCard(ArrayList<Bitmap> images, String UUID, String title, String task, String motivation, String professor, ArrayList<String> coursesOfStudy, String supervisorFirstName, String supervisorLastName, String building, String roomNumber, String phoneNumber, String institute, String email) {
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
        this.email = email;
    }

    public ArrayList<Bitmap> getImages() {
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

    public String getUUID() {
        return UUID;
    }
    public String getEmail() {
        return email;
    }
}


