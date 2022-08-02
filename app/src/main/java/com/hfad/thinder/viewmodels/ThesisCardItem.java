package com.hfad.thinder.viewmodels;


import com.hfad.thinder.data.model.Image;

public class ThesisCardItem {

    private String id;
    private String title;
    private String description;
    private Image image;

    public String getId() {
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

    public ThesisCardItem(String id, String title, String description, Image image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }


}
