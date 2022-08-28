package com.hfad.thinder.viewmodels.supervisor;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.loader.content.AsyncTaskLoader;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.CourseOfStudyItem;
import com.hfad.thinder.viewmodels.ViewModelResult;
import com.hfad.thinder.viewmodels.ViewModelResultTypes;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * A class providing a {@link ViewModel} for the {@link com.hfad.thinder.ui.supervisor.EditThesisFragment EditThesisFragment}.
 */
public class EditThesisViewModel extends ThesisViewModel {
  private static final ThesisRepository thesisRepository = ThesisRepository.getInstance();
  private static final UserRepository userRepository = UserRepository.getInstance();
  private MutableLiveData<String> totalRating;
  private UUID thesisId;
  // likes, dislikes
  private Pair<Integer, Integer> thesisStatistics;
  private MutableLiveData<ViewModelResult> deleteThesisResult;


  public void save() {
    Thesis thesis =
        new Thesis(getProfessor().getValue(), getTitle().getValue(), getMotivation().getValue(),
            getTask().getValue(), null, null, (Supervisor) userRepository.getUser(),
                null);
    new SaveTask().execute(thesisId, thesis, getCoursesOfStudyList().getValue());
  }

  /**
   * Use this method to delete the thesis from the users profile.
   */
  public void delete() {
    new DeleteTask().execute(thesisId);
  }


  //------------------getter and setter-----------------------------------------------------

  /**
   * @return the total number times this thesis has been rated.
   */
  public MutableLiveData<String> getTotalRating() {
    if (totalRating == null) {
      totalRating = new MutableLiveData<String>();
    }
    return totalRating;
  }

  /**
   * @param thesisId the {@link UUID} of this thesis.
   */
  public void setThesisId(UUID thesisId) {
    if (this.thesisId == null || !this.thesisId.equals(thesisId)) {
      this.thesisId = thesisId;
      loadThesis();
    }

  }

  /**
   * @return the {@link ViewModelResult} of the {@link #delete()} operation.
   */
  public MutableLiveData<ViewModelResult> getDeleteThesisResult() {
    if (deleteThesisResult == null) {
      deleteThesisResult = new MutableLiveData<>();
    }
    return deleteThesisResult;
  }


  public Pair<Integer, Integer> getThesisStatistics() {
    return thesisStatistics;
  }


  //-------------private methods-----------------------------------------------------


  private void loadThesis() {
    new LoadThesisTask().execute(thesisId);
  }

  private ArrayList<CourseOfStudyItem> coursesOfStudyListAdapter(ArrayList<Degree> allDegrees,
                                                                 Set<Degree> selectedDegrees) {
    ArrayList<String> selectedDegreesTypeString = new ArrayList<>();
    for (Degree degree : selectedDegrees) {
      selectedDegreesTypeString.add(degree.getDegree());
    }
    ArrayList<CourseOfStudyItem> convertedDegrees = new ArrayList<>();
    for (Degree degree : allDegrees) {
      if (selectedDegreesTypeString.contains(degree.getDegree())) {
        convertedDegrees.add(
            new CourseOfStudyItem(degree.getDegree(), degree.getId(), true));
      } else {
        convertedDegrees.add(
            new CourseOfStudyItem(degree.getDegree(), degree.getId(), false));
      }
    }
    return convertedDegrees;
  }

  private String coursesOfStudyStringAdapter(Set<Degree> degrees) {
    ArrayList<String> selectedCoursesOfStudy = new ArrayList<>();
    for (Degree degree : degrees) {
      selectedCoursesOfStudy.add(degree.getDegree());
    }
    return String.join(", ", selectedCoursesOfStudy);
  }

  private ArrayList<Bitmap> convertImages(Set<Image> imageSet) {
    if (imageSet == null) {
      return null;
    }
    ArrayList<Bitmap> convertedImages = new ArrayList<>();
    for (Image image : imageSet) {
      byte[] byteArray = image.getImage();
      Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
      convertedImages.add(bitmap);
    }
    return convertedImages;
  }

  private class LoadThesisTask extends AsyncTask<UUID, Void, Pair<Thesis, Pair<Integer, Integer>>>{

    @Override
    protected void onPreExecute() {
      getIsLoading().setValue(true);
    }

    @Override
    protected Pair<Thesis, Pair<Integer, Integer>> doInBackground(UUID... uuids) {
      Thesis thesis = thesisRepository.getThesisMap(false).get(uuids[0]);
      Pair<Integer, Integer> thesisStatistics = thesisRepository.getThesisStatistics(thesisId);
      return new Pair<>(thesis, thesisStatistics);
    }

    @Override
    protected void onPostExecute(Pair<Thesis, Pair<Integer, Integer>> thesisStatisticsPair) {
      Thesis thesis = thesisStatisticsPair.getFirst();

      getTitle().setValue(thesis.getName());
      getTask().setValue(thesis.getTask());
      getMotivation().setValue(thesis.getMotivation());
      getQuestions().setValue(thesis.getForm().getQuestions());
      getProfessor().setValue(thesis.getSupervisingProfessor());

      //courses of Study
      getSelectedCoursesOfStudy().setValue(
              coursesOfStudyStringAdapter(thesis.getPossibleDegrees()));
      MutableLiveData<ArrayList<CourseOfStudyItem>> coursesOfStudyList = new MutableLiveData<>();
      ArrayList<Degree> allDegrees = ThesisUtility.DEGREE_REPOSITORY.fetchAllCoursesOfStudy();
      coursesOfStudyList.setValue(
              coursesOfStudyListAdapter(allDegrees, thesis.getPossibleDegrees()));
      setCoursesOfStudyList(coursesOfStudyList);

      //images
      if (!thesis.getImages().isEmpty()) {
        getImages().setValue(convertImages(thesis.getImages()));
        setImages(getImages().getValue());
      }

      // ratings

      thesisStatistics = thesisStatisticsPair.getSecond();

      getTotalRating().postValue(
              String.valueOf(thesisStatistics.getFirst() + thesisStatistics.getSecond()));
      getIsLoading().setValue(false);
    }
  }

  private class SaveTask extends AsyncTask<Object, Void, Result>{

    @Override
    protected Result doInBackground(Object... objects) {
      Form form = new Form(getQuestions().getValue());
      Set<Image> imageSet = ThesisUtility.getImageSet(getImages().getValue());
      Set<Degree> degreeSet =
              ThesisUtility.getSelectedDegreeSet((ArrayList<CourseOfStudyItem>)objects[2]);
      Thesis thesis = (Thesis) objects[1];
      thesis.setForm(form);
      thesis.setPossibleDegrees(degreeSet);
      thesis.setImages(imageSet);
      return thesisRepository.editThesis((UUID) objects[0], thesis);
    }

    @Override
    protected void onPostExecute(Result result) {
      if (result.getSuccess()) {
        thesisRepository.setThesesDirty(true);
        getSaveResult().setValue(new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      } else {
        getSaveResult().setValue(
                new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
      }
      getSaveResult().setValue(null);
    }
  }

  private class DeleteTask extends AsyncTask<UUID, Void, Result>{
    @Override
    protected Result doInBackground(UUID... uuids) {
      return thesisRepository.deleteThesis(uuids[0]);
    }

    @Override
    protected void onPostExecute(Result result) {
      if (result.getSuccess()) {
        thesisRepository.setThesesDirty(true);
        getDeleteThesisResult().setValue(
                new ViewModelResult(null, ViewModelResultTypes.SUCCESSFUL));
      } else {
        getDeleteThesisResult().setValue(
                new ViewModelResult(result.getErrorMessage(), ViewModelResultTypes.ERROR));
      }
      getDeleteThesisResult().setValue(null);
    }
  }

}


