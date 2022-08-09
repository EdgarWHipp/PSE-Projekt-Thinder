package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.SupervisorRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Result;

import com.hfad.thinder.viewmodels.ThesisCardItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ThesisManagerViewModel extends ViewModel {
  private static final SupervisorRepository supervisorRepository =
      SupervisorRepository.getInstance();
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final StudentRepository studentRepository = StudentRepository.getInstance();
  private MutableLiveData<ArrayList<ThesisCardItem>> thesisCardItems;

  //-------------------getter and setter-----------------------------

  public MutableLiveData<ArrayList<ThesisCardItem>> getThesisCardItems() {
    if (thesisCardItems == null) {
      thesisCardItems = new MutableLiveData<>();
      loadThesisManagerItems();
    }
    return thesisCardItems;
  }

  private void loadThesisManagerItems() {
    studentRepository.fetchAllSwipeableThesis();
    Result result =
        supervisorRepository.getAllCreatedTheses();//Todo ist dies variante nicht sehr umständlich??
    if (result.getSuccess()) {
      List<com.hfad.thinder.data.model.Thesis> thesisList =
          thesisRepository.getThesisMap().entrySet().stream().map(e -> e.getValue()).collect(
              Collectors.toList());
      ArrayList<ThesisCardItem> newThesisList = new ArrayList<>();
      if (!thesisList.isEmpty()) {
        for (com.hfad.thinder.data.model.Thesis thesis : thesisList) {
          byte[] byteArray = thesis.getImages().iterator().next().getImage();
          Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
          newThesisList.add(
              new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), bitmap));
        }
      }
      thesisCardItems.setValue(newThesisList);
    }

  }


}
