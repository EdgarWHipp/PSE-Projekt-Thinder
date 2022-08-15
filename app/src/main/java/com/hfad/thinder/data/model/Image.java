package com.hfad.thinder.data.model;

/**
 * Simply saves a byte array for easier handling.
 */
public class Image {
    private Byte[] image;

    public Image(Byte[] image) {
        this.image = image;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }
}
