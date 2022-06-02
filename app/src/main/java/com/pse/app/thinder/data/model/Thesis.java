package com.pse.app.thinder.data.model;

import com.google.gson.annotations.SerializedName;

import android.media.Image;

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
