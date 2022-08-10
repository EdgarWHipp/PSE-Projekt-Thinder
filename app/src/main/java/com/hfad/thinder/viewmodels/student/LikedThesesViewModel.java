package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LikedThesesViewModel extends ViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<ArrayList<ThesisCardItem>> likedTheses;


  //--------------------getter and setter----------------------------------------------------


  public MutableLiveData<ArrayList<ThesisCardItem>> getLikedTheses() {
    if (likedTheses == null) {
      likedTheses = new MutableLiveData<>();
      loadLikedTheses();
    }
    return likedTheses;
  }

  //----------------private methods--------------------------
  private void loadLikedTheses() {
    ArrayList<ThesisCardItem> thesisCards = new ArrayList<>();
    HashMap<UUID, Thesis> likedTheses = thesisRepository.getThesisMap();
    for (Thesis thesis : likedTheses.values()) {
      byte[] byteArray = thesis.getImages().iterator().next().getImage();
      Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
      ThesisCardItem thesisCardItem =
          new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), image);
      thesisCards.add(thesisCardItem);
    }
    this.likedTheses.setValue(thesisCards);
  }


}
