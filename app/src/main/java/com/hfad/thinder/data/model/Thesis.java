package com.hfad.thinder.data.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Thesis {
    @SerializedName("id")
    private int thesisId;

    private String name;
    @SerializedName("text")
    private String body;
    private Form form;
    @SerializedName("list")
    private List<Image> images;

    public int getThesisId() {
        return thesisId;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public Form getForm() {
        return form;
    }

    public List<Image> getImages() {
        return images;
    }
}
