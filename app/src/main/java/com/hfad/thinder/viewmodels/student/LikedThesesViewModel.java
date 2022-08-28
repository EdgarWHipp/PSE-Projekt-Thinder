package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.ThesisCardItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.student.LikedThesesFragment LikedThesesFragment}.
 */
public class LikedThesesViewModel extends ViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private MutableLiveData<ArrayList<ThesisCardItem>> likedTheses;
  private MutableLiveData<Boolean> isLoading;


  //--------------------getter and setter-----------------------------------------------------------

  /**
   * @return a {@link MutableLiveData} object containing an {@link ArrayList} of
   * {@link ThesisCardItem ThesisCardItems} of all the Theses previously liked by the student.
   */
  public MutableLiveData<ArrayList<ThesisCardItem>> getLikedTheses() {
    if (likedTheses == null || thesisRepository.isThesesDirty()) {
      likedTheses = new MutableLiveData<>();
      loadLikedTheses();
      thesisRepository.setThesesDirty(false);
    }
    return likedTheses;
  }

  public MutableLiveData<Boolean> getIsLoading() {
    if(isLoading == null){
      isLoading = new MutableLiveData<>();
      isLoading.setValue(true);
    }
    return isLoading;
  }

//----------------public methods-----------------------------------------------------------------

  public void loadLikedTheses() {
    new LoadLikedThesesTask().execute();
  }

  private class LoadLikedThesesTask extends AsyncTask<Void, Void, ArrayList<ThesisCardItem>> {
    @Override
    protected void onPreExecute() {
      getIsLoading().setValue(true);
    }

    @Override
    protected ArrayList<ThesisCardItem> doInBackground(Void... voids) {
      ArrayList<ThesisCardItem> thesisCards = new ArrayList<>();
      HashMap<UUID, Thesis> likedTheses = thesisRepository.getThesisMap(true);
      if (likedTheses != null && !(likedTheses.isEmpty())) {
        for (Thesis thesis : likedTheses.values()) {
          Bitmap image = null;
          if (thesis.getImages().iterator().hasNext()) {
            byte[] byteArray = thesis.getImages().iterator().next().getImage();
            image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
          }
          ThesisCardItem thesisCardItem =
                  new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), image);
          thesisCards.add(thesisCardItem);
        }
      }
      return thesisCards;
    }

    @Override
    protected void onPostExecute(ArrayList<ThesisCardItem> thesisCardItems) {
      getLikedTheses().setValue(thesisCardItems);
      isLoading.setValue(false);
    }


  }
}