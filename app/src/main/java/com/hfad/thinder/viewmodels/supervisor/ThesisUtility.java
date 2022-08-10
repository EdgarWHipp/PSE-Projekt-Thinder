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

//Todo Comment
public class ThesisUtility {
  public static final ThesisRepository THESIS_REPOSITORY = ThesisRepository.getInstance();
  public static final DegreeRepository DEGREE_REPOSITORY = DegreeRepository.getInstance();
  public static final UserRepository USER_REPOSITORY = UserRepository.getInstance();

  public static Set<Degree> getSelectedDegreeSet(ArrayList<CourseOfStudyItem> courseOfStudyItems) {
    HashSet<Degree> degrees = new HashSet<>();
    for (CourseOfStudyItem courseOfStudyItem : courseOfStudyItems) {
      if (courseOfStudyItem.isPicked()) {
        degrees.add(new Degree(courseOfStudyItem.getCourseOfStudy()));
      }
    }
    return degrees;
  }

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
