package com.hfad.thinder.viewmodels.supervisor;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Tuple;
import com.hfad.thinder.ui.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ImageGalleryPicker;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import com.hfad.thinder.data.model.Thesis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import java.nio.ByteBuffer;
import java.util.UUID;

public class EditThesisViewModel extends ViewModel implements CoursesOfStudyPicker, ImageGalleryPicker {
  private static final DegreeRepository degreeRepository = DegreeRepository.getInstance();
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> title;
  private MutableLiveData<String> task;
  private MutableLiveData<String> motivation;
  private MutableLiveData<String> professor;
  private MutableLiveData<ArrayList<Bitmap>> images;
  private MutableLiveData<String> questions;

  private MutableLiveData<Bitmap> currentImage;
  private ListIterator<Bitmap> iterator;
  private UUID thesisId;
  // likes, dislikes
  private Tuple<Integer, Integer> thesisStatistics;

  private MutableLiveData<String> selectedCoursesOfStudy; //ausgewählte Studiengänge
  private MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList; //alle Studiengänge

  private MutableLiveData<EditThesisFormState> formState;
  private MutableLiveData<ViewModelResult> addThesisResult;
  private MutableLiveData<ViewModelResult> editThesisResult;
  private MutableLiveData<ViewModelResult> deleteThesisResult;


  public void addThesis() {
    Result result = thesisRepository.addThesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(), getTask().getValue(), getQuestions().getValue(), getSelectedDegreeSet() , getImageSet());
    if (result.getSuccess()) {
      getAddThesisResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getAddThesisResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  public void editThesis() {
    Thesis thesis = new Thesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(), getTask().getValue(), new Form(getQuestions().getValue()), getImageSet(), (Supervisor) userRepository.getUser(), getSelectedDegreeSet());
    Result result = thesisRepository.editThesis(thesisId, thesis);
    if (result.getSuccess()) {
      getEditThesisResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getEditThesisResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  public void delete() {
    Result result = thesisRepository.deleteThesis(thesisId);
    if (result.getSuccess()) {
      getDeleteThesisResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getDeleteThesisResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  public void thesisDataChanged() {
    getFormState().setValue(new EditThesisFormState(checkTitle(), checkTask(), checkMotivation(), checkQuestions(), checkProfessor(), checkCoursesOfStudy(), chekImages()));
  }

  public boolean hasImages() {
    return getImages().getValue() != null;
  }

  @Override
  public void makeCourseOfStudySelection(int position, boolean selection) {
      ArrayList<CourseOfStudyItem> copy = getCoursesOfStudyList().getValue();
      copy.get(position).setPicked(selection);
      getCoursesOfStudyList().setValue(copy);
  }

  @Override
  public ArrayList<CourseOfStudyItem> getElements() {
    return getCoursesOfStudyList().getValue();
  }

  @Override
  public MutableLiveData<ArrayList<CourseOfStudyItem>> getCoursesOfStudyList() {
    if (coursesOfStudyList == null) {
      coursesOfStudyList = new MutableLiveData<>();
      loadCoursesOfStudy();
    }
    return coursesOfStudyList;
  }

  public MutableLiveData<Bitmap> getCurrentImage() {
    if(currentImage == null){
      currentImage = new MutableLiveData<>();
    }
    return currentImage;
  }

  public void nextImage(){
    if(iterator.hasNext())
      getCurrentImage().setValue(iterator.next());
  }

  public void previousImage(){
    if(iterator.hasPrevious())
      getCurrentImage().setValue(iterator.previous());
  }

  //------------------getter and setter-----------------------------------------------------


  public MutableLiveData<EditThesisFormState> getFormState() {
    if (formState == null) {
      formState = new MutableLiveData<>();
    }
    return formState;
  }

  public void setThesisId(UUID thesisId) {
    this.thesisId = thesisId;
    loadThesis();
  }

  public MutableLiveData<String> getTitle() {
    if (title == null) {
      title = new MutableLiveData<>();

    }
    return title;
  }


  public void setTitle(MutableLiveData<String> title) {
    this.title = title;
  }

  public MutableLiveData<String> getTask() {
    if (task == null) {
      task = new MutableLiveData<>();

    }
    return task;
  }

  public void setTask(MutableLiveData<String> task) {
    this.task = task;
  }

  public MutableLiveData<String> getMotivation() {
    if (motivation == null) {
      motivation = new MutableLiveData<>();

    }
    return motivation;
  }

  public void setMotivation(
      MutableLiveData<String> motivation) {
    this.motivation = motivation;
  }

  public MutableLiveData<String> getProfessor() {
    if (professor == null) {
      professor = new MutableLiveData<>();

    }
    return professor;
  }

  public void setProfessor(MutableLiveData<String> professor) {
    this.professor = professor;
  }

  public MutableLiveData<String> getSelectedCoursesOfStudy() {
    if (selectedCoursesOfStudy == null) {
      selectedCoursesOfStudy = new MutableLiveData<>();

    }
    return selectedCoursesOfStudy;
  }

  public void setSelectedCoursesOfStudy(
      MutableLiveData<String> fieldsOfStudy) {
    this.selectedCoursesOfStudy = fieldsOfStudy;
  }



  public MutableLiveData<ArrayList<Bitmap>> getImages() {
    if (images == null) {
      images = new MutableLiveData<>();
    }
    return images;
  }

  public void setImages(
      MutableLiveData<ArrayList<Bitmap>> images) {
    iterator = images.getValue().listIterator();
    Log.i(TAG, "setImages: " + images.getValue().size());
    getCurrentImage().setValue(iterator.next());
    this.images = images;
  }

  public MutableLiveData<String> getQuestions() {
    if (questions == null) {
      questions = new MutableLiveData<>();
    }
    return questions;
  }

  public void setQuestions(MutableLiveData<String> questions) {
    this.questions = questions;
  }

  public MutableLiveData<ViewModelResult> getAddThesisResult() {
    if (addThesisResult == null) {
      addThesisResult = new MutableLiveData<>();
    }
    return addThesisResult;
  }

  public MutableLiveData<ViewModelResult> getDeleteThesisResult() {
    if (deleteThesisResult == null) {
      deleteThesisResult = new MutableLiveData<>();
    }
    return deleteThesisResult;
  }

  public MutableLiveData<ViewModelResult> getEditThesisResult() {
    if (editThesisResult == null) {
      editThesisResult = new MutableLiveData<>();
    }
    return editThesisResult;
  }

  public Tuple<Integer, Integer> getThesisStatistics() {
    return thesisStatistics;
  }



  //-------------private methods-----------------------------------------------------


  private void loadCoursesOfStudy() {
    ArrayList<String> allDegrees = degreeRepository.getAllDegrees();
    ArrayList<String> selectedDegrees = thesisRepository.getSelectedDegrees();
    ArrayList<CourseOfStudyItem> courseOfStudyItems = new ArrayList<>();
    for (String degree : allDegrees) {
      if (selectedDegrees.contains(degree)) {
        courseOfStudyItems.add(new CourseOfStudyItem(degree, true));
        selectedDegrees.add(degree);
      }else{
        courseOfStudyItems.add(new CourseOfStudyItem(degree, false));
      }
    }
    coursesOfStudyList = new MutableLiveData<>();
    coursesOfStudyList.setValue(courseOfStudyItems);

    String degreesAsString = String.join(", ", selectedDegrees);

    selectedCoursesOfStudy = new MutableLiveData<>();
    selectedCoursesOfStudy.setValue(degreesAsString);
  }

  private Set<Degree> getSelectedDegreeSet() {
    HashSet<Degree> degrees = new HashSet<>();
    for (CourseOfStudyItem courseOfStudyItem : getCoursesOfStudyList().getValue()) {
      if (courseOfStudyItem.isPicked()) {
        degrees.add(new Degree(courseOfStudyItem.getCourseOfStudy()));
      }
    }
    return degrees;
  }

  private Set<Image> getImageSet() {//Todo in Utility klasse
    HashSet<Image> images = new HashSet<>();
    for (Bitmap bitmap : getImages().getValue()) {
      int size = bitmap.getRowBytes() * bitmap.getHeight();
      ByteBuffer byteBuffer = ByteBuffer.allocate(size);
      bitmap.copyPixelsToBuffer(byteBuffer);
      byte[] byteArray;
      byteArray = byteBuffer.array();

      images.add(new Image(byteArray));

    }
    return images;
  }

  private void loadThesis() {
    //UUID uuid = UUID.fromString(thesisId);
    //Thesis thesis = thesisRepository.getThesis(uuid).x; //todo uncomment
    Thesis thesis = generateThesis();//Todo löschen

    getTitle().setValue(thesis.getName());
    getTask().setValue(thesis.getTask());
    getMotivation().setValue(thesis.getMotivation());
    getQuestions().setValue(thesis.getForm().getQuestions());

    //courses of Study
    getSelectedCoursesOfStudy().setValue(coursesOfStudyStringAdapter(thesis.getPossibleDegrees()));
    getCoursesOfStudyList().setValue(coursesOfStudyListAdapter(degreeRepository.getAllDegrees(), thesis.getPossibleDegrees()));

    //images
    //images = convertImages(thesis.getImages()); //todo: uncomment
    if(getImages().getValue() != null) {
      iterator = getImages().getValue().listIterator();
      getCurrentImage().setValue(iterator.next());
    }




    thesisStatistics = thesisRepository.getThesisStatistics(thesisId); //todo set actual uuid

  }

  private ArrayList<CourseOfStudyItem> coursesOfStudyListAdapter(ArrayList<String> allDegrees, Set<Degree> selectedDegrees) {
    ArrayList<CourseOfStudyItem> convertedDegrees = new ArrayList<>();
    for (String degree : allDegrees) {
      if (selectedDegrees.contains(new Degree(degree))) {
        convertedDegrees.add(new CourseOfStudyItem(degree, true));
      } else {
        convertedDegrees.add(new CourseOfStudyItem(degree, false));
      }
    }
    return convertedDegrees;
  }

  private String coursesOfStudyStringAdapter(Set<Degree> degrees) {
    ArrayList<String> selectedCoursesOfStudy = new ArrayList<>();
    for(Degree degree : degrees) {
      selectedCoursesOfStudy.add(degree.getDegree());
    }
    return String.join(", ", selectedCoursesOfStudy);
  }

  private ArrayList<Bitmap> convertImages(java.util.Set<Image> imageSet) {
    ArrayList<Bitmap> convertedImages = new ArrayList<>();
    for (Image image : imageSet) {
      byte[] byteArray = image.getImage();
      Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
      convertedImages.add(bitmap);
    }
    return convertedImages;
  }

  // todo: remove
  Thesis generateThesis(){
    Bitmap image1 = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888);
    Bitmap image2 = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888);
    ArrayList<Bitmap> newImages = new ArrayList<>();
    newImages.add(image1);
    newImages.add(image2);
    Supervisor supervisor = new Supervisor(USERTYPE.SUPERVISOR, UUID.randomUUID(), true, UUID.randomUUID(), "gubert", "gubert@mail", "firstname", "lastName", "academicDegree", "building", "officeNumber", "insitute", "phoneNumber" );
    HashSet<Degree> possibleDegrees = new HashSet<>();
    possibleDegrees.add(new Degree("Bachelor Informatik"));
    possibleDegrees.add(new Degree("Bachelor Mathematik"));

    Thesis thesis = new Thesis("Prof. Hartmann", "", "", "task", new Form("questions"), null, supervisor, possibleDegrees);

    return thesis;
  }

  private Integer checkTitle() {
    if (getTitle().getValue() == null || getTitle().getValue().equals("")) {
      return R.string.no_title_error;
    }
    return null;
  }

  private Integer checkTask() {
    if(getTask().getValue() == null || getTask().getValue().equals("")) {
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
    if (getSelectedCoursesOfStudy().getValue() == null || getSelectedCoursesOfStudy().getValue().equals("")) {
      return R.string.no_courses_of_study_error;
    }
    return null;
  }

  private boolean chekImages() {
    return getImages().getValue() != null;
  }
}
