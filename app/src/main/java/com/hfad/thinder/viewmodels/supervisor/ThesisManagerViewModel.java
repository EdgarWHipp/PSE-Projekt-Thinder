package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.UUID;

public class ThesisManagerViewModel extends ViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<ArrayList<ThesisCardItem>> thesisCardItems;

  //Todo: Databinding
  //-------------------getter and setter-----------------------------

  public MutableLiveData<ArrayList<ThesisCardItem>> getThesisCardItems() {
    if (thesisCardItems == null) {
      thesisCardItems = new MutableLiveData<>();
      //todo remove
      Bitmap image = Bitmap.createBitmap(100, 50, Bitmap.Config.ARGB_8888);
      ThesisCardItem thesisCardItem = new ThesisCardItem(UUID.randomUUID(), "title", "task", image);
      ArrayList<ThesisCardItem> items = new ArrayList<>();
      items.add(thesisCardItem);
      thesisCardItems.setValue(items);
      //loadThesisManagerItems();
    }
    return thesisCardItems;
  }

  private void loadThesisManagerItems() {
    thesisRepository.fetchAllSwipeableThesis();
    List<com.hfad.thinder.data.model.Thesis> thesisList = thesisRepository.getAllSwipeableTheses();//Todo hole alle Thesis des Supervisors
    if(thesisList==null){
      //TODO
    }
    ArrayList<ThesisCardItem> newThesisList = new ArrayList<>();
    if (thesisList != null) {
      for (com.hfad.thinder.data.model.Thesis thesis : thesisList) {
        byte[] byteArray = thesis.getImages().iterator().next().getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        newThesisList.add(new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), bitmap));
      }
    }
    thesisCardItems.setValue(newThesisList);
  }


}
