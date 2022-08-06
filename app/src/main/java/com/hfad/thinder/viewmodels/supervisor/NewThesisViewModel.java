package com.hfad.thinder.viewmodels.supervisor;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.ui.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.CoursesOfStudyPicker;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

import java.util.ArrayList;
import java.util.Set;

public class NewThesisViewModel extends ThesisViewModel  {
    private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();


    public void save() {
        Set<Image> imageSet = ThesisUtility.getImageSet(getImages().getValue());
        Set<Degree> degreeSet = ThesisUtility.getSelectedDegreeSet(getCoursesOfStudyList().getValue());
        Result result = thesisRepository.addThesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(), getTask().getValue(), getQuestions().getValue(), degreeSet , imageSet);
        if (result.getSuccess()) {
            getSaveResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
        } else {
            getSaveResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
        }
    }



}
