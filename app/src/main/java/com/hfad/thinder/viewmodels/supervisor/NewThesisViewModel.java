package com.hfad.thinder.viewmodels.supervisor;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;

import java.util.Set;

public class NewThesisViewModel extends ThesisViewModel  {
    


    public void save() {
        Set<Image> imageSet = ThesisUtility.getImageSet(getImages().getValue());
        Set<Degree> degreeSet = ThesisUtility.getSelectedDegreeSet(getCoursesOfStudyList().getValue());
        Result result = ThesisUtility.THESIS_REPOSITORY.addThesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(), getTask().getValue(), getQuestions().getValue(), degreeSet , imageSet);
        if (result.getSuccess()) {
            getSaveResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
        } else {
            getSaveResult().setValue(new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
        }
    }



}
