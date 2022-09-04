package com.hfad.thinder.viewmodels.supervisor;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ImageGalleryPicker;
import com.hfad.thinder.viewmodels.ImageListIterator;
import com.hfad.thinder.viewmodels.ViewModelResult;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.supervisor.NewThesisFragment NewThesisFragment}
 * and the {@link com.hfad.thinder.ui.supervisor.EditThesisFragment EditThesisFragment}.
 */
public abstract class ThesisViewModel extends ViewModel
    implements CoursesOfStudyPicker, ImageGalleryPicker {
  public static final int QUALITY = 80;
  private static final int COMPRESSION_SIZE = 1000;
  private static final DegreeRepository degreeRepository = DegreeRepository.getInstance();
  private MutableLiveData<String> title;
  private MutableLiveData<String> task;
  private MutableLiveData<String> motivation;
  private MutableLiveData<String> professor;
  private MutableLiveData<ArrayList<Bitmap>> images;
  private MutableLiveData<String> questions;
  private MutableLiveData<Bitmap> currentImage;
  private ImageListIterator<Bitmap> iterator;
  private MutableLiveData<String> selectedCoursesOfStudy;
  private MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList;
  private MutableLiveData<ThesisFormState> formState;
  private MutableLiveData<ViewModelResult> saveResult;
  private MutableLiveData<Boolean> isLoading;

  /**
   * Use this method to save a new thesis or the changes to an existing thesis.
   */
  public abstract void save();

  /**
   * Use this method to check whether the entered profile data is valid. This method should be called everytime the user edits the data in the ui.
   */
  public void thesisDataChanged() {
    getFormState().postValue(
        new ThesisFormState(checkTitle(), checkTask(), checkMotivation(), checkQuestions(),
            checkProfessor(), checkCoursesOfStudy(), checkImages()));
  }

  @Override
  public void makeCourseOfStudySelection(String changedCourseOfStudy, boolean selection) {

    ArrayList<CourseOfStudyItem> copy = getCoursesOfStudyList().getValue();
    for (CourseOfStudyItem courseOfStudyItem : copy) {
      if (courseOfStudyItem.getCourseOfStudy().equals(changedCourseOfStudy)) {
        courseOfStudyItem.setPicked(selection);
      }
    }
    getCoursesOfStudyList().postValue(copy);
  }

  //----------------getter and setter -----------------------------------------------------------

  @Override
  public ArrayList<CourseOfStudyItem> getElements() {
    return getCoursesOfStudyList().getValue();
  }

  @Override
  public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList() {
    if (coursesOfStudyList == null) {
      loadCoursesOfStudy();
    } else {
      updateSelectedCoursesOfStudy();
    }
    return coursesOfStudyList;
  }

  /**
   * @param coursesOfStudyList all existing courses of study.
   */
  public void setCoursesOfStudyList(
      MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList) {
    this.coursesOfStudyList = coursesOfStudyList;
  }

  public MutableLiveData<Bitmap> getCurrentImage() {
    if (currentImage == null) {
      currentImage = new MutableLiveData<>();
    }
    return currentImage;
  }


  public void nextImage() {
    currentImage.setValue(iterator.next());
  }

  public void previousImage() {
    currentImage.setValue(iterator.previous());
  }

  public void deleteImages() {
    iterator = null;
    getCurrentImage().setValue(null);
    getImages().setValue(null);
  }


  /**
   * @return the current {@link ThesisFormState}.
   */
  public MutableLiveData<ThesisFormState> getFormState() {
    if (formState == null) {
      formState = new MutableLiveData<>();
    }
    return formState;
  }


  /**
   * @return the title of the thesis.
   */
  public MutableLiveData<String> getTitle() {
    if (title == null) {
      title = new MutableLiveData<>();

    }
    return title;
  }


  /**
   * @param title the title of the thesis.
   */
  public void setTitle(MutableLiveData<String> title) {
    this.title = title;
  }

  /**
   * @return the task description of the thesis.
   */
  public MutableLiveData<String> getTask() {
    if (task == null) {
      task = new MutableLiveData<>();

    }
    return task;
  }

  /**
   * @param task the task description of the thesis.
   */
  public void setTask(MutableLiveData<String> task) {
    this.task = task;
  }

  /**
   * @return the motivation description of the thesis.
   */
  public MutableLiveData<String> getMotivation() {
    if (motivation == null) {
      motivation = new MutableLiveData<>();

    }
    return motivation;
  }

  /**
   * @param motivation the motivation description of the thesis.
   */
  public void setMotivation(
      MutableLiveData<String> motivation) {
    this.motivation = motivation;
  }

  /**
   * @return the supervising professor of the thesis.
   */
  public MutableLiveData<String> getProfessor() {
    if (professor == null) {
      professor = new MutableLiveData<>();

    }
    return professor;
  }

  /**
   * @param professor the supervising professor of the thesis.
   */
  public void setProfessor(MutableLiveData<String> professor) {
    this.professor = professor;
  }

  /**
   * @return the courses of study eligible for a thesis as a single {@link String}.
   */
  public MutableLiveData<String> getSelectedCoursesOfStudy() {
    if (selectedCoursesOfStudy == null) {
      selectedCoursesOfStudy = new MutableLiveData<>();

    }
    return selectedCoursesOfStudy;
  }

  /**
   * @param fieldsOfStudy the courses of study eligible for a thesis.
   */
  public void setSelectedCoursesOfStudy(
      MutableLiveData<String> fieldsOfStudy) {
    this.selectedCoursesOfStudy = fieldsOfStudy;
  }


  /**
   * @return the images added to the thesis.
   */
  public MutableLiveData<ArrayList<Bitmap>> getImages() {
    if (images == null) {
      images = new MutableLiveData<>();
    }
    return images;
  }

  /**
   * Use this method to replace the old images with new images.
   *
   * @param images the images for the thesis.
   */
  public void setImages(
      ArrayList<Bitmap> images) {
    new SetImagesTask().execute(images);
  }

  /**
   * @return the questions for the student.
   */
  public MutableLiveData<String> getQuestions() {
    if (questions == null) {
      questions = new MutableLiveData<>();
    }
    return questions;
  }

  /**
   * @param questions the questions for the student.
   */
  public void setQuestions(MutableLiveData<String> questions) {
    this.questions = questions;
  }

  /**
   * @return the {@link ViewModelResult} of the {@link #save()} operation.
   */
  public MutableLiveData<ViewModelResult> getSaveResult() {

    if (saveResult == null) {
      saveResult = new MutableLiveData<>();
    }
    return saveResult;
  }

  public MutableLiveData<Boolean> getIsLoading() {
    if (isLoading == null) {
      isLoading = new MutableLiveData<>();
      isLoading.setValue(false);
    }
    return isLoading;
  }

  //-----------------------------private methods---------------------------------------------------------------

  private void loadCoursesOfStudy() {
    ArrayList<Degree> allDegrees = degreeRepository.fetchAllCoursesOfStudy();
    ArrayList<CourseOfStudyItem> courseOfStudyItems = new ArrayList<>();
    for (Degree degree : allDegrees) {
      courseOfStudyItems.add(new CourseOfStudyItem(degree.getDegree(), degree.getId(), false));
    }
    coursesOfStudyList = new MutableLiveData<>();
    coursesOfStudyList.setValue(courseOfStudyItems);
  }

  private Integer checkTitle() {
    if (getTitle().getValue() == null || getTitle().getValue().equals("")) {
      return R.string.no_title_error;
    }
    return null;
  }

  private Integer checkTask() {
    if (getTask().getValue() == null || getTask().getValue().equals("")) {
      return R.string.no_task_description_error;
    }
    return null;
  }

  private Integer checkMotivation() {
    if (getMotivation().getValue() == null || getMotivation().getValue().equals("")) {
      return R.string.no_motivation_error;
    }
    return null;
  }

  private Integer checkQuestions() {
    if (getQuestions().getValue() == null || getQuestions().getValue().equals("")) {
      return R.string.no_questions_error;
    }
    return null;
  }

  private Integer checkProfessor() {
    if (getProfessor().getValue() == null || getProfessor().getValue().equals("")) {
      return R.string.no_professor_error;
    }
    return null;
  }

  private Integer checkCoursesOfStudy() {
    if (getSelectedCoursesOfStudy().getValue() == null ||
        getSelectedCoursesOfStudy().getValue().equals("")) {
      return R.string.no_courses_of_study_error;
    }
    return null;
  }

  private boolean checkImages() {
    return getImages().getValue() != null;
  }

  private void updateSelectedCoursesOfStudy() {
    ArrayList<String> selectedCourses = new ArrayList<>();
    for (CourseOfStudyItem courseOfStudyItem : coursesOfStudyList.getValue()) {
      if (courseOfStudyItem.isPicked()) {
        selectedCourses.add(courseOfStudyItem.getCourseOfStudy());
      }
    }
    selectedCoursesOfStudy.setValue(String.join(", ", selectedCourses));
  }


  private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
    int width = image.getWidth();
    int height = image.getHeight();

    float bitmapRatio = (float) width / (float) height;
    if (bitmapRatio > 1) {
      width = maxSize;
      height = (int) (width / bitmapRatio);
    } else {
      height = maxSize;
      width = (int) (height * bitmapRatio);
    }

    Bitmap resizedBitmap = Bitmap.createScaledBitmap(image, width, height, true);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    // quality is ignored for PNGs
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, out);
    byte[] compressedBitmap = out.toByteArray();

    return BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length);
  }

  private class SetImagesTask extends AsyncTask<ArrayList<Bitmap>, Void, ArrayList<Bitmap>> {

    @Override
    protected ArrayList<Bitmap> doInBackground(ArrayList<Bitmap>... arrayLists) {
      ArrayList<Bitmap> compressedImages = new ArrayList<>();
      for (Bitmap image : arrayLists[0]) {
        compressedImages.add(getResizedBitmap(image, COMPRESSION_SIZE));
      }
      return compressedImages;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
      iterator = new ImageListIterator<>(bitmaps);
      getCurrentImage().setValue(iterator.current());
      getImages().setValue(bitmaps);
    }
  }
}



