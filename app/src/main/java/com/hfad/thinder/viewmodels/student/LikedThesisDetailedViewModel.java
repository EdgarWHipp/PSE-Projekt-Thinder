package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.ThesisRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;
import java.util.UUID;

public class LikedThesisDetailedViewModel extends ViewModel {

  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private ArrayList<Bitmap> images;
  private ListIterator<Bitmap> iterator;
  private MutableLiveData<String> task;
  private MutableLiveData<String> motivation;
  private MutableLiveData<String> coursesOfStudy;
  private ArrayList<String> coursesOfStudyList;
  private MutableLiveData<String> supervisorName;
  private MutableLiveData<String> mail;
  private MutableLiveData<String> phoneNumber;
  private MutableLiveData<String> building;
  private MutableLiveData<String> supervisingProf;
  private MutableLiveData<String> institute;

  private String thesisId;

  private MutableLiveData<Bitmap> currentImage;

  public void delete() {
    //Todo: implement
  }

  //todo: fill images arraylist with images from thesisId

  public MutableLiveData<Bitmap> getCurrentImage() {
    if(currentImage == null){
      currentImage = new MutableLiveData<>();
    }
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
  //todo: remove images
  public void setThesisId(String thesisId, ArrayList<Bitmap> images) {
    this.images = images;
    this.thesisId = thesisId;
    loadThesis();
  }

  //-----------------------getter and setter-----------------------------

  public MutableLiveData<String> getCoursesOfStudy() {
    if(coursesOfStudy == null){
      coursesOfStudy = new MutableLiveData<>();
    }
    return coursesOfStudy;
  }

  public MutableLiveData<String> getTask() {
    if(task == null){
      task = new MutableLiveData<>();
    }
    return task;
  }

  public MutableLiveData<String> getMotivation() {
    if(motivation == null){
      motivation = new MutableLiveData<>();
    }
    return motivation;
  }

  public MutableLiveData<String> getSupervisorName() {
    if(supervisorName == null){
      supervisorName = new MutableLiveData<>();
    }
    return supervisorName;
  }

  public MutableLiveData<String> getPhoneNumber() {
    if(phoneNumber == null){
      phoneNumber = new MutableLiveData<>();
    }
    return phoneNumber;
  }

  public MutableLiveData<String> getBuilding() {
    if(building == null){
      building = new MutableLiveData<>();
    }
    return building;
  }

  public MutableLiveData<String> getSupervisingProf() {
    if(supervisingProf == null){
      supervisingProf = new MutableLiveData<>();
    }
    return supervisingProf;
  }

  public MutableLiveData<String> getInstitute() {
    if (institute == null) {
      institute = new MutableLiveData<>();
    }
    return institute;
  }

  public MutableLiveData<String> getMail() {
    if(mail == null){
      mail = new MutableLiveData<>();
    }
    return mail;
  }

  //-----------------------private methods--------------------------------

  private void loadThesis() {
    UUID uuid = UUID.fromString(thesisId);
    Thesis thesis = thesisRepository.getThesis(uuid).x;
    Supervisor supervisor = thesis.getSupervisor();
    //images = convertImages(thesis.getImages()); //todo: uncomment
    iterator = images.listIterator();
    getTask().setValue(thesis.getTask());
    getMotivation().setValue(thesis.getMotivation());
    coursesOfStudyList = coursesOfStudyAdapter(thesis.getPossibleDegrees());
    getCoursesOfStudy().setValue(String.join("\n", coursesOfStudyList));
    getSupervisorName().setValue(supervisor.getFirstName() + " " + supervisor.getLastName());
    getMail().setValue(supervisor.getMail());
    getBuilding().setValue(supervisor.getBuilding() + " " + supervisor.getBuilding());
    getPhoneNumber().setValue(supervisor.getPhoneNumber());
    getSupervisingProf().setValue(thesis.getSupervisingProfessor());
    getInstitute().setValue(supervisor.getInstitute());
    getCurrentImage().setValue(iterator.next());

  }
  // todo: remove
  Thesis generateThesis(){

    Supervisor supervisor = new Supervisor(USERTYPE.SUPERVISOR, UUID.randomUUID(), true, UUID.randomUUID(), "gubert", "gubert@mail", "firstname", "lastName", "academicDegree", "building", "officeNumber", "insitute", "phoneNumber" );
    HashSet<Degree> possibleDegrees = new HashSet<>();
    possibleDegrees.add(new Degree("Bachelor", "Informatik"));
    possibleDegrees.add(new Degree("Bachelor", "Mathematik"));

    Thesis thesis = new Thesis("Prof. Hartmann", "Florian", "motivation", "task", "form", null, supervisor, possibleDegrees);

    return thesis;
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

  private ArrayList<String> coursesOfStudyAdapter(Set<Degree> degrees) {
    ArrayList<String> convertedDegrees = new ArrayList<>();
    for (Degree degree : degrees) {
      String degreeString = degree.getDegree() + " " + degree.getName();
      convertedDegrees.add(degreeString);
    }
    return convertedDegrees;
  }

}
