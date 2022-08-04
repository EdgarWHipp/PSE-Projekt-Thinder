package com.hfad.thinder.viewmodels;


import android.graphics.Bitmap;

import com.hfad.thinder.data.model.Image;

public class ThesisCardItem {

    private String thesisUUID;
    private String title;
    private String task;
    private Bitmap image;

    public String getThesisUUID() {
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

    public ThesisCardItem(String id, String title, String task, Bitmap image) {
        this.thesisUUID = id;
        this.title = title;
        this.task = task;
        this.image = image;
    }


}
