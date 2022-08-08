package com.hfad.thinder.viewmodels.supervisor;


import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import android.graphics.BitmapFactory;

import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import com.hfad.thinder.data.model.Thesis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EditThesisViewModel extends ThesisViewModel {
  private static final DegreeRepository degreeRepository = DegreeRepository.getInstance();
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> totalRating;
  private UUID thesisId;
  // likes, dislikes
  private Pair<Integer, Integer> thesisStatistics;
  private MutableLiveData<ViewModelResult> deleteThesisResult;


  public void save() {
    Form form = new Form(getQuestions().getValue());
    Set<Image> imageSet = ThesisUtility.getImageSet(getImages().getValue());
    Set<Degree> degreeSet = ThesisUtility.getSelectedDegreeSet(getCoursesOfStudyList().getValue());
    Thesis thesis = new Thesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(), getTask().getValue(), form, imageSet, (Supervisor) userRepository.getUser(), degreeSet);
    Result result = thesisRepository.editThesis(thesisId, thesis);
    if (result.getSuccess()) {
      getSaveResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
    } else {
      getSaveResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
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


  //------------------getter and setter-----------------------------------------------------

  public MutableLiveData<String> getTotalRating() {
    if (totalRating == null) {
      totalRating = new MutableLiveData<String>();
    }
    return totalRating;
  }


  public void setThesisId(UUID thesisId) {
    if (this.thesisId == null || !this.thesisId.equals(thesisId)) {
      this.thesisId = thesisId;
      loadThesis();
    }

  }


  public MutableLiveData<ViewModelResult> getDeleteThesisResult() {
    if (deleteThesisResult == null) {
      deleteThesisResult = new MutableLiveData<>();
    }
    return deleteThesisResult;
  }


  public Pair<Integer, Integer> getThesisStatistics() {
    return thesisStatistics;
  }


  //-------------private methods-----------------------------------------------------



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
    MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList = new MutableLiveData<>();
    coursesOfStudyList.setValue(coursesOfStudyListAdapter(degreeRepository.getAllDegrees(), thesis.getPossibleDegrees()));
    setCoursesOfStudyList(coursesOfStudyList);

    //images
    getImages().setValue(convertImages(thesis.getImages())); //todo: uncomment

    thesisStatistics = thesisRepository.getThesisStatistics(thesisId); //todo set actual uuid
    getTotalRating().postValue(String.valueOf(thesisStatistics.getFirst() + thesisStatistics.getSecond()));

  }

  private ArrayList<CourseOfStudyItem> coursesOfStudyListAdapter(ArrayList<String> allDegrees, Set<Degree> selectedDegrees) {
    ArrayList<String> selectedDegreesTypeString = new ArrayList<>();
    for(Degree degree : selectedDegrees){
      selectedDegreesTypeString.add(degree.getDegree());
    }
    ArrayList<CourseOfStudyItem> convertedDegrees = new ArrayList<>();
    for (String degree : allDegrees) {
      if (selectedDegreesTypeString.contains(degree)){
        convertedDegrees.add(new CourseOfStudyItem(degree, true));
      } else {
        convertedDegrees.add(new CourseOfStudyItem(degree, false));
      }
    }
    return convertedDegrees;
  }

  private String coursesOfStudyStringAdapter(Set<Degree> degrees) {
    ArrayList<String> selectedCoursesOfStudy = new ArrayList<>();
    for (Degree degree : degrees) {
      selectedCoursesOfStudy.add(degree.getDegree());
    }
    return String.join(", ", selectedCoursesOfStudy);
  }

  private ArrayList<Bitmap> convertImages(Set<Image> imageSet) {
    if (imageSet == null) {
      return null;
    }
    ArrayList<Bitmap> convertedImages = new ArrayList<>();
    for (Image image : imageSet) {
      byte[] byteArray = image.getImage();
      Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
      convertedImages.add(bitmap);
    }
    return convertedImages;
  }

  // todo: remove
  Thesis generateThesis() {
    Bitmap image1 = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888);
    Bitmap image2 = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888);
    ArrayList<Bitmap> newImages = new ArrayList<>();
    newImages.add(image1);
    newImages.add(image2);
    Supervisor supervisor = new Supervisor(USERTYPE.SUPERVISOR, UUID.randomUUID(), true, UUID.randomUUID(), "gubert", "gubert@mail", "firstname", "lastName", "academicDegree", "building", "officeNumber", "insitute", "phoneNumber");
    HashSet<Degree> possibleDegrees = new HashSet<>();
    possibleDegrees.add(new Degree("Bachelor Informatik"));
    possibleDegrees.add(new Degree("Bachelor Mathematik"));

    Thesis thesis = new Thesis("Prof. Hartmann", "test", "test", "task", new Form("questions"), null, supervisor, possibleDegrees);

    return thesis;
  }
}


