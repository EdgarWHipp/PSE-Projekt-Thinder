package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;

public class LikedThesisFormViewModel {
  private MutableLiveData<String> questions;
  private MutableLiveData<String> answers;

  public void send() {
    //Todo: implement
  }

  //--------------getter and setter----------------------------


  public MutableLiveData<String> getQuestions() {
    if (questions == null) {
      questions = new MutableLiveData<>();
      loadQuestions();
    }
    return questions;
  }

  public MutableLiveData<String> getAnswers() {
    if (answers == null) {
      answers = new MutableLiveData<>();
    }
    return answers;
  }

  public void setAnswers(MutableLiveData<String> answers) {
    this.answers = answers;
  }

  //-------------private methods--------------------------------

  private void loadQuestions() {
    //Todo: implement
  }
}
