package com.hfad.thinder.viewmodels;

import android.media.Image;

public class ThesisCardItem {

    private int id;
    private String title;
    private String description;
    private Image image;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public ThesisCardItem(int id, String title, String description, Image image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }


}
