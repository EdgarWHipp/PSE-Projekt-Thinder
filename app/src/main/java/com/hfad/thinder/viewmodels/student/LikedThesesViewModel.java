package com.hfad.thinder.viewmodels.student;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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


    //--------------------getter and setter-----------------------------------------------------------

    /**
     * @return a {@link MutableLiveData} object containing an {@link ArrayList} of
     * {@link ThesisCardItem ThesisCardItems} of all the Theses previously liked by the student.
     */
    public MutableLiveData<ArrayList<ThesisCardItem>> getLikedTheses() {
        if (likedTheses == null) {
            likedTheses = new MutableLiveData<>();
            loadLikedTheses();
        }
        return likedTheses;
    }

    //----------------public methods-----------------------------------------------------------------

    public void loadLikedTheses() {
        ArrayList<ThesisCardItem> thesisCards = new ArrayList<>();
        HashMap<UUID, Thesis> likedTheses = thesisRepository.getThesisMap(true);
        if (likedTheses != null && !(likedTheses.isEmpty())) {
            for (Thesis thesis : likedTheses.values()) {
                Bitmap image = null;
                if(thesis.getImages().iterator().hasNext()){
                    byte[] byteArray = thesis.getImages().iterator().next().getImage();
                    image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                ThesisCardItem thesisCardItem =
                        new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), image);
                thesisCards.add(thesisCardItem);
            }
        }
        getLikedTheses().setValue(thesisCards);


    }
}