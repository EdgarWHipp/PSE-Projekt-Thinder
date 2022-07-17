package com.hfad.thinder.viewmodels.student;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.model.Thesis;
import java.util.ArrayList;

public class SwipeScreenViewModel {
  private MutableLiveData<ArrayList<Thesis>> thesises;

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


  public MutableLiveData<ArrayList<Thesis>> getThesises() {
    if (thesises == null || thesises.getValue().size() <= 5) {
      thesises = new MutableLiveData<>();
      loadThesises();
    }
    return thesises;
  }

  //----------------private methods-----------------------------------
  private void loadThesises() {
    //Todo: implement
  }
}
