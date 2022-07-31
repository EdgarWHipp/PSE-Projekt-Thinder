package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.ListIterator;

public class ImageGalleryViewModel extends ViewModel {

    private ArrayList<Bitmap> images;
    private ListIterator<Bitmap> iterator;
    private String thesisId;

    private MutableLiveData<Bitmap> currentImage;

    //todo: fill images arraylist with images from thesisId

    public MutableLiveData<Bitmap> getCurrentImage() {
        return currentImage;
    }

    public void nextImage(){
        if(iterator.hasNext())
            currentImage.setValue(iterator.next());
    }

    public void previousImage(){
        if(iterator.hasPrevious())
            currentImage.setValue(iterator.previous());
    }

    public void setThesisId(String thesisId) {
        this.thesisId = thesisId;
    }
}
