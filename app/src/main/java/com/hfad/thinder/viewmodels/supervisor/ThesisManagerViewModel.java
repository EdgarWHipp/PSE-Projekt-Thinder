package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.supervisor.ThesisManagerFragment ThesisManagerFragment}.
 */
public class ThesisManagerViewModel extends ViewModel {

  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<ArrayList<ThesisCardItem>> thesisCardItems;

  //-------------------getter and setter-----------------------------

  /**
   * @return all thesis created by the supervisor.
   */
  public MutableLiveData<ArrayList<ThesisCardItem>> getThesisCardItems() {
    if (thesisCardItems == null || thesisRepository.isThesesDirty()) {
      thesisCardItems = new MutableLiveData<>();
      loadThesisManagerItems();
      thesisRepository.setThesesDirty(false);
    }
    return thesisCardItems;
  }

  public void loadThesisManagerItems() {
    HashMap<UUID, Thesis> thesisHashMap = thesisRepository.getThesisMap(true);
    ArrayList<ThesisCardItem> newThesisList = new ArrayList<>();
    if (!(thesisHashMap == null) && !(thesisHashMap.isEmpty())) {
      List<Thesis> thesisList =
          thesisHashMap.values().stream().collect(
              Collectors.toList());
      for (com.hfad.thinder.data.model.Thesis thesis : thesisList) {
        Bitmap bitmap = null;
        if (!thesis.getImages().isEmpty()) {
          byte[] byteArray = thesis.getImages().iterator().next().getImage();
          bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        newThesisList.add(
            new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), bitmap));
      }
      thesisCardItems.setValue(newThesisList);
    }
  }


}
