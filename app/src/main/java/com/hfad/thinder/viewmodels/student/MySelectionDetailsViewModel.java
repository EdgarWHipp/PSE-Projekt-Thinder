package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.Thesis;

public class MySelectionDetailsViewModel {

  private MutableLiveData<Thesis> thesis;

  public void delete() {
    //Todo: implement
  }

  //-----------------------getter and setter-----------------------------

  public MutableLiveData<Thesis> getThesis() {
    if (thesis == null) {
      thesis = new MutableLiveData<>();
      loadThesis();
    }
    return thesis;
  }

  //-----------------------private methods--------------------------------

  private void loadThesis() {
  }
}
