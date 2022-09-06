package com.hfad.thinder.viewmodels;

import android.graphics.Bitmap;
import androidx.lifecycle.MutableLiveData;


public interface ImageGalleryPicker {

  /**
   * @return the image which is supposed to be displayed in the view.
   */
  MutableLiveData<Bitmap> getCurrentImage();

  /**
   * change the {@link #getCurrentImage() current} image to the next image in the list of all images
   */
  void nextImage();

  /**
   * change the {@link #getCurrentImage() current} image to the previous image in the list of all images.
   */
  void previousImage();
}
