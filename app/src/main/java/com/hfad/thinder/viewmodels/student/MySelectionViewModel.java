package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.model.Thesis;
import java.util.ArrayList;

public class MySelectionViewModel {
  private MutableLiveData<ArrayList<Thesis>> selectedThesises;

  public void viewDetails() {
    /*
     *Idee: id der Thesis wird an das Model übergeben. Im SwipeDetailsViewModel wird die etsprechende
     * Thesis dann aus dem Model geladen
     */

  }

  //--------------------getter and setter----------------------------------------------------

  public MutableLiveData<ArrayList<Thesis>> getSelectedThesises() {
    if (selectedThesises == null) {
      ArrayList<Thesis> elements = new ArrayList<>();
      selectedThesises = new MutableLiveData<>();
      loadSelectedThesises();
    }
    return selectedThesises;
  }


  //----------------private methods--------------------------
  private void loadSelectedThesises() {
    //Todo: implement
  }
}
