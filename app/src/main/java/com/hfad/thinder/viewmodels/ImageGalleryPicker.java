package com.hfad.thinder.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

public interface ImageGalleryPicker {
    public MutableLiveData<Bitmap> getCurrentImage();

    public void nextImage();

    public void previousImage();
}
