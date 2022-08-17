package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains references to the repositories and methods to convert the data structures that hold the images for the thesis.
 */
public class ThesisUtility {
  public static final ThesisRepository THESIS_REPOSITORY = ThesisRepository.getInstance();
  public static final DegreeRepository DEGREE_REPOSITORY = DegreeRepository.getInstance();
  public static final UserRepository USER_REPOSITORY = UserRepository.getInstance();

  /**
   * Use this method to convert all {@link CourseOfStudyItem}s used in the ui to {@link Degree}s used in the model.
   *
   * @param courseOfStudyItems a {@link ArrayList} of {@link CourseOfStudyItem}s
   * @return a {@link Set} of {@link Degree}s.
   */
  public static Set<Degree> getSelectedDegreeSet(ArrayList<CourseOfStudyItem> courseOfStudyItems) {
    HashSet<Degree> degrees = new HashSet<>();
    for (CourseOfStudyItem courseOfStudyItem : courseOfStudyItems) {
      if (courseOfStudyItem.isPicked()) {
        degrees.add(new Degree(courseOfStudyItem.getCourseOfStudy(), courseOfStudyItem.getUuid()));
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
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray;
        byteArray = byteBuffer.array();

        images.add(new Image(byteArray));

      }
    }

    return images;
  }
}
