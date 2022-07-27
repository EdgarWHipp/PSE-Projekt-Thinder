package com.hfad.thinder.data.model;

import android.media.Image;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

public class Thesis {
    @SerializedName("id")
    private int thesisId;
    @SerializedName("name")
    private String name;
    @SerializedName("supervisingProfessor")
    private String supervisingProfessor;

    public String getSupervisingProfessor() {
        return supervisingProfessor;
    }

    public void setSupervisingProfessor(String supervisingProfessor) {
        this.supervisingProfessor = supervisingProfessor;
    }

    @SerializedName("motivation")
    private String motivation;
    @SerializedName("task")
    private String task;
    @SerializedName("form")
    private Form form;
    @SerializedName("images")
    @Nullable
    private Set<Image> images;
    @SerializedName("supervisor")
    private Supervisor supervisor;
    @SerializedName("studentRatings")
    @Nullable
    private Set<ThesisRating> studentRatings;
    @SerializedName("possibleDegrees")
    private Set<Degree> possibleDegrees;
    public void setThesisId(int thesisId) {
        this.thesisId = thesisId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void setImages(@Nullable Set<Image> images) {
        this.images = images;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public void setStudentRatings(@Nullable Set<ThesisRating> studentRatings) {
        this.studentRatings = studentRatings;
    }

    public void setPossibleDegrees(Set<Degree> possibleDegrees) {
        this.possibleDegrees = possibleDegrees;
    }

    public Thesis(int thesisId, String name, String motivation, String task, Form form, @Nullable Set<Image> images, Supervisor supervisor, @Nullable Set<ThesisRating> studentRatings, Set<Degree> possibleDegrees) {
        this.thesisId = thesisId;
        this.name = name;
        this.motivation = motivation;
        this.task = task;
        this.form = form;
        this.images = images;
        this.supervisor = supervisor;
        this.studentRatings = studentRatings;
        this.possibleDegrees = possibleDegrees;
    }


    public Supervisor getSupervisor() {
        return supervisor;
    }


    public Set<ThesisRating> getStudentRatings() {
        return studentRatings;
    }

    public Set<Degree> getPossibleDegrees() {
        return possibleDegrees;
    }







    public int getThesisId() {
        return thesisId;
    }

    public String getName() {
        return name;
    }


    @Nullable
    public Form getForm() {
        return form;
    }
    @Nullable
    public Set<Image> getImages() {
        return images;
    }
}
