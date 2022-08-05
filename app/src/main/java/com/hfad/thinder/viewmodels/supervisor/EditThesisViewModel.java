package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.graphics.BitmapFactory;

import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Tuple;
import com.hfad.thinder.ui.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ImageGalleryPicker;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import com.hfad.thinder.data.model.Thesis;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import java.nio.ByteBuffer;
import java.util.UUID;

public class EditThesisViewModel extends ViewModel implements CoursesOfStudyPicker, ImageGalleryPicker {
  private static final DegreeRepository degreeRepository = DegreeRepository.getInstance();
  private static final StudentRepository studentRepository = StudentRepository.getInstance();
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<String> name;
  private MutableLiveData<String> taskDescription;
  private MutableLiveData<String> motivationDescription;
  private MutableLiveData<String> professor;
  private MutableLiveData<ArrayList<Bitmap>> images;
  private MutableLiveData<String> questions;
  private MutableLiveData<ViewModelResult> addThesisResult;
  private MutableLiveData<ViewModelResult> editThesisResult;
  private MutableLiveData<ViewModelResult> deleteThesisResult;
  private MutableLiveData<Bitmap> currentImage;
  private ListIterator<Bitmap> iterator;
  private String thesisId;
  // likes, dislikes
  private Tuple<Integer, Integer> thesisStatistics;

  private MutableLiveData<String> selectedCoursesOfStudy;
  private MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList;


  public void addThesis() {
    Result result = thesisRepository.addThesis(getProfessor().getValue(), getName().getValue(), getMotivationDescription().getValue(), getTaskDescription().getValue(), getQuestions().getValue(), getSelectedDegreeSet() , getImageSet());
    if (result.getSuccess()) {
      getAddThesisResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getAddThesisResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
    }
  }

  public void editThesis() {
    //Todo: implement
  }

  public void delete() {
    //Todo: implementieren
  }

  @Override
  public void makeCourseOfStudySelection(int position, boolean selection) {

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



  public void setThesisId(String thesisId) {
    this.thesisId = thesisId;
    loadThesis();
  }

  public MutableLiveData<String> getName() {
    if (name == null) {
      name = new MutableLiveData<>();

    }
    return name;
  }


  public void setName(MutableLiveData<String> name) {
    this.name = name;
  }

  public MutableLiveData<String> getTaskDescription() {
    if (taskDescription == null) {
      taskDescription = new MutableLiveData<>();

    }
    return taskDescription;
  }

  public void setTaskDescription(MutableLiveData<String> taskDescription) {
    this.taskDescription = taskDescription;
  }

  public MutableLiveData<String> getMotivationDescription() {
    if (motivationDescription == null) {
      motivationDescription = new MutableLiveData<>();

    }
    return motivationDescription;
  }

  public void setMotivationDescription(
      MutableLiveData<String> motivationDescription) {
    this.motivationDescription = motivationDescription;
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
    ArrayList<String> allDegrees = degreeRepository.getAllStudentDegrees();
    ArrayList<String> selectedDegrees = studentRepository.getSelectedDegrees();
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

  private Set<Image> getImageSet() {
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
    //images = convertImages(thesis.getImages()); //todo: uncomment
    getSelectedCoursesOfStudy().setValue(coursesOfStudyStringAdapter(thesis.getPossibleDegrees()));
    getCoursesOfStudyList().setValue(coursesOfStudyListAdapter(degreeRepository.getAllStudentDegrees(), thesis.getPossibleDegrees()));
    thesisStatistics = thesisRepository.getThesisStatistics(UUID.randomUUID()); //todo set actual uuid
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

    Supervisor supervisor = new Supervisor(USERTYPE.SUPERVISOR, UUID.randomUUID(), true, UUID.randomUUID(), "gubert", "gubert@mail", "firstname", "lastName", "academicDegree", "building", "officeNumber", "insitute", "phoneNumber" );
    HashSet<Degree> possibleDegrees = new HashSet<>();
    possibleDegrees.add(new Degree("Bachelor Informatik"));
    possibleDegrees.add(new Degree("Bachelor Mathematik"));

    Thesis thesis = new Thesis("Prof. Hartmann", "Florian", "motivation", "task", new Form("questions"), null, supervisor, possibleDegrees);

    return thesis;
  }
}
