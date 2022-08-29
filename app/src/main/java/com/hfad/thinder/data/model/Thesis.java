package com.hfad.thinder.data.model;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.hfad.thinder.data.source.result.Pair;

import java.util.Set;
import java.util.UUID;

/**
 * This class defines all attributes that a thesis need on creation, attributes like the id are created and automatically generated inside the backend.
 */
public class Thesis {
    @SerializedName("id")
    private UUID id;
    @SerializedName("name")
    private String name;
    @SerializedName("supervisingProfessor")
    private String supervisingProfessor;
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
    @SerializedName("possibleDegrees")
    private Set<Degree> possibleDegrees;
    @SerializedName("ratings")
    private Pair<Integer, Integer> ratings;

    public Thesis(String supervisingProfessor, String name, String motivation, String task
            , Form form, @Nullable Set<Image> images, Supervisor supervisor
            , Set<Degree> possibleDegrees) {
        this.name = name;
        this.motivation = motivation;
        this.task = task;
        this.form = form;
        this.images = images;
        this.supervisor = supervisor;
        this.possibleDegrees = possibleDegrees;
        this.supervisingProfessor = supervisingProfessor;
    }

    public String getSupervisingProfessor() {
        return supervisingProfessor;
    }

    public void setSupervisingProfessor(String supervisingProfessor) {
        this.supervisingProfessor = supervisingProfessor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public Thesis() {

    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Degree> getPossibleDegrees() {
        return possibleDegrees;
    }

    public void setPossibleDegrees(Set<Degree> possibleDegrees) {
        this.possibleDegrees = possibleDegrees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    @Nullable
    public Set<Image> getImages() {
        return images;
    }

    public void setImages(@Nullable Set<Image> images) {
        this.images = images;
    }

    public Pair<Integer, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Pair<Integer, Integer> ratings) {
        this.ratings = ratings;
    }
}
