package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.Thesis;

public class SwipeDetailsViewModel {
  private MutableLiveData<Thesis> thesis;

  //-----------getter and setter--------------------------

  public MutableLiveData<Thesis> getThesis() {
    if (thesis == null) {
      thesis = new MutableLiveData<>();
      loadThesis();
    }
    return thesis;
  }

  //-----------private Methods----------------------------

  private void loadThesis() {
    //Todo: implement
  }
}
