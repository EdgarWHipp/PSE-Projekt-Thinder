package com.hfad.thinder.data.model;

/**
 * Simply saves a byte array for easier handling.
 */
public class Image {
    private byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
