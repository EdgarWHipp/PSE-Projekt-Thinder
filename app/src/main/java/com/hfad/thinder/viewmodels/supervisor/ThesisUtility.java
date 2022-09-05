package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains the methods to convert the data structures that hold the images for the thesis.
 */
public class ThesisUtility {


  /**
   * Use this method to convert all {@link CourseOfStudyItem}s used in the ui to {@link Degree}s used in the model.
   *
   * @param courseOfStudyItems a {@link ArrayList} of {@link CourseOfStudyItem}s
   * @return a {@link Set} of {@link Degree}s.
   */
  public static Set<Degree> getSelectedDegreeSet(ArrayList<CourseOfStudyItem> courseOfStudyItems) {
    HashSet<Degree> degrees = new HashSet<>();
    if (courseOfStudyItems != null) {
      for (CourseOfStudyItem courseOfStudyItem : courseOfStudyItems) {
        if (courseOfStudyItem.isPicked()) {
          degrees.add(
              new Degree(courseOfStudyItem.getCourseOfStudy(), courseOfStudyItem.getUuid()));
        }
      }
    }
    return degrees;
  }

  /**
   * Use this method to convert all images from their ui representation to their model representation.
   *
   * @param bitmaps a {@link ArrayList} of {@link Bitmap}s
   * @return a {@link Set} of {@link Image}s.
   */
  public static Set<Image> getImageSet(ArrayList<Bitmap> bitmaps) {
    HashSet<Image> images = new HashSet<>();
    if (bitmaps != null) {
      for (Bitmap bitmap : bitmaps) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        images.add(new Image(byteArray));
      }
    }

    return images;
  }
}
