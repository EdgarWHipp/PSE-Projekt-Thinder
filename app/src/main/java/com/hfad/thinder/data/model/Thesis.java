package com.hfad.thinder.data.model;

import android.media.Image;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

public class Thesis {
    @SerializedName("id")
    private int thesisId;

    private String name;
    @SerializedName("description")
    private String body;

    public Supervisor getSupervisor() {
        return supervisor;
    }
    @Nullable
    public Set<ThesisRating> getStudentRatings() {
        return studentRatings;
    }

    public Set<Degree> getPossibleDegrees() {
        return possibleDegrees;
    }

    @SerializedName("questionForm")
    private Form form;
    private Set<Image> images;
    private Supervisor supervisor;
    private Set<ThesisRating> studentRatings;
    private Set<Degree> possibleDegrees;


    public Thesis(int thesisId, String name, String body, Form form, Set<Image> images) {
        this.thesisId = thesisId;
        this.name = name;
        this.body = body;
        this.form = form;
        this.images = images;
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
