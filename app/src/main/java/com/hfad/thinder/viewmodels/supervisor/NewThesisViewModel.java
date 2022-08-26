package com.hfad.thinder.viewmodels.supervisor;

import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.Set;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.supervisor.NewThesisFragment NewThesisFragment}.
 */
public class NewThesisViewModel extends ThesisViewModel {


  public void save() {
    Set<Image> imageSet = ThesisUtility.getImageSet(getImages().getValue());
    Set<Degree> degreeSet = ThesisUtility.getSelectedDegreeSet(getCoursesOfStudyList().getValue());
    Thesis thesis = new Thesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(), getTask().getValue(), new Form(getQuestions().getValue()), imageSet, (Supervisor) UserRepository.getInstance().getUser(), degreeSet);
    new SaveTask().execute(thesis);

  }

  private class SaveTask extends AsyncTask<Thesis, Void, Result> {

    @Override
    protected void onPreExecute() {
      getIsLoading().setValue(true);
    }

    @Override
    protected Result doInBackground(Thesis... theses) {
      return ThesisUtility.THESIS_REPOSITORY.addThesis(theses[0]);
    }

    @Override
    protected void onPostExecute(Result result) {
      getIsLoading().setValue(false);
      if (result.getSuccess()) {
        getSaveResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
        ThesisUtility.THESIS_REPOSITORY.setThesesDirty(true);
        resetAllFields();
      } else {
        getSaveResult().setValue(
                new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
      }
      getSaveResult().setValue(null);
    }
  }

  /**
   * all Fields
   */
  private void resetAllFields(){
    getTitle().setValue("");
    getMotivation().setValue("");
    getTask().setValue("");
    getProfessor().setValue("");
    getQuestions().setValue("");
    getSaveResult().setValue(null);
    getImages().setValue(null);
    getSelectedCoursesOfStudy().setValue("");
  }
}
