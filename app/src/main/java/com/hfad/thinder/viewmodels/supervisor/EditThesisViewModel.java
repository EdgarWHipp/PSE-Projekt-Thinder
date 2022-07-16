package com.hfad.thinder.viewmodels.supervisor;

import android.media.Image;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;

public class EditThesisViewModel {
  private MutableLiveData<String> name;
  private MutableLiveData<String> taskDescription;
  private MutableLiveData<String> motivationDescription;
  private MutableLiveData<String> professor;
  private MutableLiveData<ArrayList<String>> fieldsOfStudy;
  private MutableLiveData<ArrayList<Image>> images;

  public void safe() {
    //Todo: implementieren
  }

  //------------------getter and setter-----------------------------------------------------

  public MutableLiveData<String> getName() {
    if (name == null) {
      name = new MutableLiveData<>();
      loadName();
    }
    return name;
  }


  public void setName(MutableLiveData<String> name) {
    this.name = name;
  }

  public MutableLiveData<String> getTaskDescription() {
    if (taskDescription == null) {
      taskDescription = new MutableLiveData<>();
      loadTaskDescription();
    }
    return taskDescription;
  }

  public void setTaskDescription(MutableLiveData<String> taskDescription) {
    this.taskDescription = taskDescription;
  }

  public MutableLiveData<String> getMotivationDescription() {
    if (motivationDescription == null) {
      motivationDescription = new MutableLiveData<>();
      loadMotivationDescription();
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
      loadProfessor();
    }
    return professor;
  }

  public void setProfessor(MutableLiveData<String> professor) {
    this.professor = professor;
  }

  public MutableLiveData<ArrayList<String>> getFieldsOfStudy() {
    if (fieldsOfStudy == null) {
      fieldsOfStudy = new MutableLiveData<>();
      loadFieldsOfStudy();
    }
    return fieldsOfStudy;
  }

  public void setFieldsOfStudy(
      MutableLiveData<ArrayList<String>> fieldsOfStudy) {
    this.fieldsOfStudy = fieldsOfStudy;
  }

  public MutableLiveData<ArrayList<Image>> getImages() {
    if (images == null) {
      images = new MutableLiveData<>();
      loadImages();
    }
    return images;
  }

  public void setImages(
      MutableLiveData<ArrayList<Image>> images) {
    this.images = images;
  }

  //-------------private methods-----------------------------------------------------

  private void loadName() {
    //Todo: implement
  }

  private void loadTaskDescription() {
    //Todo: implement
  }

  private void loadMotivationDescription() {
    //Todo: implement
  }

  private void loadProfessor() {
    //Todo: implement
  }

  private void loadFieldsOfStudy() {
    //Todo: implement
  }

  private void loadImages() {
    //Todo: implement
  }

}
