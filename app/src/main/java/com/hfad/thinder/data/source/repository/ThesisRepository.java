package com.hfad.thinder.data.source.repository;


import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Defines the interface between the frontend and backend, such that the ViewModel can use to interact with backend data.
 * This class includes all functionalities that interact with thesis objects.
 */
public final class ThesisRepository {
  private static ThesisRepository INSTANCE;
  private final ThesisRemoteDataSource thesisRemoteDataSource = new ThesisRemoteDataSource();
  private final SupervisorRemoteDataSource supervisorRemoteDataSource =
      new SupervisorRemoteDataSource();
  private Thesis currentlySelectedThesis;
  private HashMap<UUID, Thesis> thesisMap = new HashMap<>();
  private ArrayList<Thesis> theses = new ArrayList<>();
  private boolean thesesDirty;
  private boolean swipeDirty;

  /**
   * private constructor
   */
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

  /**
   * Returns the saved Arraylist of Thesis objects inside the singleton ThesisRepository instance. This is initially set during the fetching of all liked thesis of the student
   * and the created thesis for the supervisors.
   * @return ArrayList<Thesis>
   */
  public ArrayList<Thesis> getTheses() {
    return theses;
  }

  /**
   * Sets the Arraylist of Thesis objects inside the singleton ThesisRepository instance.
   * @param theses
   */
  public void setTheses(ArrayList<Thesis> theses) {
    this.theses = theses;
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
   * @return Result class, including success value and error message
   */
  public Result setThesis(final UUID thesisId) {
    if (thesisMap.containsKey(thesisId)) {
      setCurrentlySelectedThesis(thesisMap.get(thesisId));
      return new Result(true);
    } else {      return new Result(R.string.exception_during_HTTP_call, false);
    }
  }

  /**
   * Returns the HashMap of UUID's and Theses. This hashmap is necessary for the students swipe screen.
   * @return HashMap<UUID, Thesis>
   */
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

  /**
   * Sets the Hashmap of UUID's and Theses inside the ThesisRepository singleton instance.
   * @param thesisMap
   */
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
      return new ArrayList<Thesis>();
    }
    return new ArrayList<Thesis>();
  }


  /**
   * Adds a new thesis to the global list of all thesis that the students can access inside the swipe screen,
   * if their profile settings (courses of study) match the thesis courses of study.
   *
   * @param thesis
   * @return Result class, including success value and error message
   */
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
   * @return Result class, including success value and error message
   */
  public Result editThesis(final UUID thesisId, final Thesis thesis) {

    return supervisorRemoteDataSource
        .editThesis(thesisId, thesis);
  }

  /**
   * Delete an existing thesis.
   *
   * @param thesisId
   * @return Result class, including success value and error message
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
