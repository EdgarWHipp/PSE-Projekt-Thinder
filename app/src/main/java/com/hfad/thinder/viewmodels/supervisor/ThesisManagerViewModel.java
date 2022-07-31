package com.hfad.thinder.viewmodels.supervisor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.Thesis;
import com.hfad.thinder.viewmodels.ThesisCardItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThesisManagerViewModel extends ViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<ArrayList<ThesisCardItem>> thesisCardItems;

  //Todo: Databinding
  //-------------------getter and setter-----------------------------

  public MutableLiveData<ArrayList<ThesisCardItem>> getThesisCardItems() {
    if (thesisCardItems == null) {
      thesisCardItems = new MutableLiveData<>();
      loadThesisManagerItems();
    }
    return thesisCardItems;
  }

  private void loadThesisManagerItems() {
    List<com.hfad.thinder.data.model.Thesis> thesisList = thesisRepository.getAll().orElse(null);
    ArrayList<ThesisCardItem> newThesisList = new ArrayList<>();
    if (thesisList != null) {
      for (com.hfad.thinder.data.model.Thesis thesis : thesisList) {
        newThesisList.add(new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), thesis.getImages().iterator().next()));
      }
    }
    thesisCardItems.setValue(newThesisList);
  }


}
