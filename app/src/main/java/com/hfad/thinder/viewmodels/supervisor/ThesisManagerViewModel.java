package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.StudentRepository;
import com.hfad.thinder.data.source.repository.SupervisorRepository;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.viewmodels.ThesisCardItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        HashMap<UUID, Thesis> thesisHashMap = thesisRepository.getThesisMap();
        ArrayList<ThesisCardItem> newThesisList = new ArrayList<>();
        if (!(thesisHashMap == null) && !(thesisHashMap.isEmpty())) {
            List<Thesis> thesisList =
                    thesisHashMap.values().stream().collect(
                            Collectors.toList());
            for (com.hfad.thinder.data.model.Thesis thesis : thesisList) {
                byte[] byteArray = thesis.getImages().iterator().next().getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                newThesisList.add(
                        new ThesisCardItem(thesis.getId(), thesis.getName(), thesis.getTask(), bitmap));
            }
            thesisCardItems.setValue(newThesisList);
        }


    }


}
