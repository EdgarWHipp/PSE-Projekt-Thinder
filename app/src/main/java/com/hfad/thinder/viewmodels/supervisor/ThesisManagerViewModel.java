package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.Thesis;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThesisManagerViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<List<Thesis>> thesisManagerItems;

  //Todo: Databinding
  //-------------------getter and setter-----------------------------

  public MutableLiveData<List<Thesis>> getThesisManagerItems() {
    if (thesisManagerItems == null) {
      thesisManagerItems = new MutableLiveData<>();
      loadThesisManagerItems();
    }
    return thesisManagerItems;
  }

  private void loadThesisManagerItems() {
    List<com.hfad.thinder.data.model.Thesis> thesisList = thesisRepository.getAll().orElse(null);
    List<Thesis> newThesisList = new ArrayList<>();
    if (thesisList != null) {
      for (com.hfad.thinder.data.model.Thesis thesis : thesisList) {
        Set<String> degrees = new HashSet<>();
        for (Degree degree : thesis.getPossibleDegrees()) {
          degrees.add(degree.getDegree());
        }
        newThesisList.add(
            new Thesis(thesis.getName(), thesis.getBody(), thesis.getForm().getQuestions(),
                thesis.getBody(), degrees, thesis.getImages(), null, null));
        //Todo: überarbeite Thesis erstellen

      }
    }
  }


}
