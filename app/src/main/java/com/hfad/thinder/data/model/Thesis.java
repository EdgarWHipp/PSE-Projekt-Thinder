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
    @SerializedName("body")
    private String body;
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
    public Supervisor getSupervisor() {
        return supervisor;
    }


    public Set<ThesisRating> getStudentRatings() {
        return studentRatings;
    }

    public Set<Degree> getPossibleDegrees() {
        return possibleDegrees;
    }





    public Thesis(String name, String body, Form form, Set<Image> images, Supervisor supervisor, Set<ThesisRating> studentRatings, Set<Degree> possibleDegrees) {
        this.name = name;
        this.body = body;
        this.form = form;
        this.images = images;
        this.supervisor = supervisor;
        this.studentRatings = studentRatings;
        this.possibleDegrees = possibleDegrees;
    }

    public int getThesisId() {
        return thesisId;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
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
