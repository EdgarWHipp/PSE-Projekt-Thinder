package com.hfad.thinder.data.source.repository;


import android.util.Log;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository {
  private static ThesisRepository INSTANCE;
  private final ThesisRemoteDataSource thesisRemoteDataSource = new ThesisRemoteDataSource();
  private final SupervisorRemoteDataSource supervisorRemoteDataSource =
      new SupervisorRemoteDataSource();
  private final StudentRemoteDataSource studentRemoteDataSource = new StudentRemoteDataSource();
  private final boolean flag = false;
  private Thesis currentlySelectedThesis;
  private HashMap<UUID, Thesis> thesisMap = new HashMap<>();
  private ArrayList<Thesis> theses = new ArrayList<>();
  private boolean thesesDirty;
  private boolean swipeDirty;

  private ThesisRepository() {
  }

  /**
   * @return current instance of ThesisRepository singleton class.
   */
  public static ThesisRepository getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ThesisRepository();
    }
    return INSTANCE;
  }

  public ArrayList<Thesis> getTheses() {
    return theses;
  }

  public void setTheses(ArrayList<Thesis> theses) {
    this.theses = theses;
  }

  /**
   * This function is used by the viewmodel to return a previously selected thesis for the detailed thesis screen.
   *
   * @return Thesis
   */
  public Thesis getCurrentlySelectedThesis() {
    return currentlySelectedThesis;
  }

  /**
   * Sets the thesis that the viewmodel identified through the id.
   * This function is used by the viewmodel to gain information about the currently opened thesis.
   *
   * @param currentlySelectedThesis
   */
  public void setCurrentlySelectedThesis(Thesis currentlySelectedThesis) {
    this.currentlySelectedThesis = currentlySelectedThesis;
  }

  /**
   * This function sets the Thesis for the getCurrentlySelectedThesis by taking the id that the viewmodel provides
   * and setting the currentlySelectedThesis value.
   *
   * @param thesisId
   * @return
   */
  public Result setThesis(final UUID thesisId) {
    if (thesisMap.containsKey(thesisId)) {
      setCurrentlySelectedThesis(thesisMap.get(thesisId));
      return new Result((true));
    } else {
      return new Result("not successful", false);
    }
  }

  public HashMap<UUID, Thesis> returnThesisMap() {
    return thesisMap;
  }

  /**
   * Returns a hashmap of all theses that the currently active student has liked.
   *
   * @return HashMap<UUID, Thesis>
   */
  public HashMap<UUID, Thesis> getThesisMap(boolean fromBackend) {
    if (fromBackend) {
      Result result;
      if (UserRepository.getInstance().getType() == USERTYPE.STUDENT) {
        result = StudentRepository.getInstance().fetchAllLikedThesis();
        if (result.getSuccess()) {
          return this.getThesisMap(false);
        } else {
          return null;
        }
      } else if (UserRepository.getInstance().getType() == USERTYPE.SUPERVISOR) {
        result = SupervisorRepository.getInstance().getAllCreatedTheses();
        if (result.getSuccess()) {
          return this.getThesisMap(false);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else {
      return returnThesisMap();
    }
  }

  public void setThesisMap(HashMap<UUID, Thesis> thesisMap) {
    this.thesisMap = thesisMap;
  }

  /**
   * Simply returns all theses that the student should be able to select from.
   * Taken directly from the thesisRepository.
   *
   * @return ArrayList<Thesis>
   */
  public ArrayList<Thesis> getAllSwipeableTheses() {
    Result result;
    if (UserRepository.getInstance().getType() == USERTYPE.STUDENT) {
      result = StudentRepository.getInstance().fetchAllSwipeableThesis();
      if (result.getSuccess()) {
        return this.getTheses();
      }
    } else {
      return null;
    }
    return null;
  }


  /**
   * Adds a new thesis to the global list of all thesis that the students can
   *
   * @param supervisingProf
   * @param name
   * @param motivation
   * @param task
   * @param questions
   * @param degrees
   * @param images
   * @return Result class including success value and error message
   */
  public Result addThesis(String supervisingProf, String name, String motivation, String task,
                          String questions, Set<Degree> degrees, Set<Image> images) {
    Log.e("", "value in repo" + questions);
    return thesisRemoteDataSource.createNewThesis(
        new Thesis(supervisingProf, name, motivation, task, new Form(questions), images,
            (Supervisor) UserRepository.getInstance().getUser(), degrees));
  }

  public Result addThesis(Thesis thesis){
    return thesisRemoteDataSource.createNewThesis(thesis);
  }


  public Pair<Thesis, Result> getThesis(final UUID id) {
    return thesisRemoteDataSource.getNewThesis(id);
  }


  /**
   * Edit an existing thesis.
   *
   * @param thesisId
   * @return
   */
  public Result editThesis(final UUID thesisId, final Thesis thesis) {

    return supervisorRemoteDataSource
        .editThesis(thesisId, thesis);


  }

  /**
   * Delete an existing thesis.
   *
   * @param thesisId
   * @return
   */
  public Result deleteThesis(UUID thesisId) {
    return thesisRemoteDataSource.deleteThesis(thesisId);
  }

  public boolean isThesesDirty() {
    return thesesDirty;
  }

  public void setThesesDirty(boolean thesesDirty) {
    this.thesesDirty = thesesDirty;
  }

  public boolean isSwipeDirty() {
    return swipeDirty;
  }

  public void setSwipeDirty(boolean swipeDirty) {
    this.swipeDirty = swipeDirty;
  }
}
