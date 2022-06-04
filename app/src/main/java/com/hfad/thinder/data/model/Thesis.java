package com.hfad.thinder.data.model;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Thesis {
    @SerializedName("id")
    int thesisId;

    String name;
    @SerializedName("text")
    String body;

    Form form;
    @SerializedName("list")
    List<Image> images;
}
