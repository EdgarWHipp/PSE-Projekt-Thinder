package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import java.util.ArrayList;

public class SwipeScreenViewModel {
  private MutableLiveData<ArrayList<ThesisCardItem>> theses;

  public void like() {
    //Todo: implement
  }

  public void dislike() {
    //Todo: implement
  }

  public void viewDetails() {
    /*
     *Idee: id der Thesis wird an das Model übergeben. Im SwipeDetailsViewModel wird die etsprechende
     * Thesis dann aus dem Model geladen
     */

  }

  //--------------getter and setter---------------------------------


  public MutableLiveData<ArrayList<ThesisCardItem>> getTheses() {
    if (theses == null || theses.getValue().size() <= 5) {
      theses = new MutableLiveData<>();
      loadThesises();
    }
    return theses;
  }

  //----------------private methods-----------------------------------
  private void loadThesises() {
    //Todo: implement
  }
}
