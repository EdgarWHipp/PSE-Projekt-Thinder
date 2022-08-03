package com.hfad.thinder.data.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

public class Thesis {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("supervisingProfessor")
    private String supervisingProfessor;
    @SerializedName("motivation")
    private String motivation;
    @SerializedName("task")
    private String task;
    @SerializedName("form")
    private String form;
    @SerializedName("images")
    @Nullable
    private Set<Image> images;
    @SerializedName("supervisor")
    private Supervisor supervisor;
    @SerializedName("possibleDegrees")
    private Set<Degree> possibleDegrees;

    public String getSupervisingProfessor() {
        return supervisingProfessor;
    }

    public void setSupervisingProfessor(String supervisingProfessor) {
        this.supervisingProfessor = supervisingProfessor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setForm(String form) {
        this.form = form;
    }

    public void setImages(@Nullable Set<Image> images) {
        this.images = images;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }


    public void setPossibleDegrees(Set<Degree> possibleDegrees) {
        this.possibleDegrees = possibleDegrees;
    }

    public Thesis(String supervisingProfessor, String name, String motivation, String task, String form, @Nullable Set<Image> images, Supervisor supervisor, Set<Degree> possibleDegrees) {
        this.name = name;
        this.motivation = motivation;
        this.task = task;
        this.form = form;
        this.images = images;
        this.supervisor = supervisor;
        this.possibleDegrees = possibleDegrees;
        this.supervisingProfessor=supervisingProfessor;
    }


    public Supervisor getSupervisor() {
        return supervisor;
    }




    public Set<Degree> getPossibleDegrees() {
        return possibleDegrees;
    }









    public String getName() {
        return name;
    }


    @Nullable
    public String getForm() {
        return form;
    }
    @Nullable
    public Set<Image> getImages() {
        return images;
    }
}
