package com.hfad.thinder.viewModels.supervisor;

import android.graphics.Bitmap;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.supervisor.ThesisUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.MockitoAnnotations;

public class ThesisUtilityTest {

  @Rule
  public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private ThesisUtility thesisUtility;
  private ArrayList<CourseOfStudyItem> courseOfStudyItems;
  private ArrayList<Bitmap> bitmaps;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    thesisUtility = new ThesisUtility();
    courseOfStudyItems = new ArrayList<>();
    bitmaps = new ArrayList<>();
  }

  @Test
  public void getSelectedDegreeSetListNullTest() {
    Assert.assertTrue(ThesisUtility.getSelectedDegreeSet(null).isEmpty());
  }

  @Test
  public void getSelectedDegreeSetEmptyListTest() {
    Assert.assertTrue(ThesisUtility.getSelectedDegreeSet(courseOfStudyItems).isEmpty());
  }

  @Test
  public void getSelectedDegreeSetTest() {
    CourseOfStudyItem testItem1 = new CourseOfStudyItem("1", UUID.randomUUID(), false);
    CourseOfStudyItem testItem2 = new CourseOfStudyItem("2", UUID.randomUUID(), false);
    CourseOfStudyItem testItem3 = new CourseOfStudyItem("3", UUID.randomUUID(), true);
    CourseOfStudyItem testItem4 = new CourseOfStudyItem("4", UUID.randomUUID(), true);
    courseOfStudyItems.add(testItem1);
    courseOfStudyItems.add(testItem2);
    courseOfStudyItems.add(testItem3);
    courseOfStudyItems.add(testItem4);
    Set<Degree> degreeSet = ThesisUtility.getSelectedDegreeSet(courseOfStudyItems);
    List<UUID> names =
        degreeSet.stream().map(degree -> degree.getId()).collect(Collectors.toList());
    for (CourseOfStudyItem courseOfStudyItem : courseOfStudyItems) {
      if (courseOfStudyItem.isPicked()) {
        Assert.assertTrue(names.contains(courseOfStudyItem.getUuid()));//contais picked degrees
      }
      if (!courseOfStudyItem.isPicked()) {
        Assert.assertFalse(
            names.contains(courseOfStudyItem.getUuid()));//doesn't contain not picked degrees
      }

    }
  }

  @Test
  public void getImageSetListNull() {
    Assert.assertTrue(ThesisUtility.getImageSet(null).isEmpty());
  }

  @Test
  public void getImageSetEmptyList() {
    Assert.assertTrue(ThesisUtility.getImageSet(bitmaps).isEmpty());
  }


}