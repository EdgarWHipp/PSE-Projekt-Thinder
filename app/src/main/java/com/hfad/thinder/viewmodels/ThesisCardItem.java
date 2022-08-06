package com.hfad.thinder.viewmodels;


import android.graphics.Bitmap;

import com.hfad.thinder.data.model.Image;

import java.util.UUID;

public class ThesisCardItem {

    private UUID thesisUUID;
    private String title;
    private String task;
    private Bitmap image;

    public UUID getThesisUUID() {
        return thesisUUID;
    }

    public String getTitle() {
        return title;
    }

    public String getTask() {
        return task;
    }

    public Bitmap getImage() {
        return image;
    }

    public ThesisCardItem(UUID id, String title, String task, Bitmap image) {
        this.thesisUUID = id;
        this.title = title;
        this.task = task;
        this.image = image;
    }


}
