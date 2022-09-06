package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Defines the interface between the frontend and backend, such that the ViewModel can use to interact with backend data.
 * This class includes all functionalities that can solely be executed when a student is currently logged in.
 */
public class StudentRepository {

  private static StudentRepository INSTANCE;
  private final StudentRemoteDataSource studentRemoteDataSource = new StudentRemoteDataSource();

  private StudentRepository() {

  }

  /**
   * @return current instance of ThesisRepository singleton class.
   */
  public static StudentRepository getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new StudentRepository();
    }
    return INSTANCE;
  }


  /**
   * Updates the ratings in the backend - this function is called when the student closes the swipe screen.
   *
   * @param ratings
   * @return Result
   */
  public Result rateThesis(final Collection<Pair<UUID, Boolean>> ratings) {
    return studentRemoteDataSource.rateThesis(ratings);
  }

  /**
   * Gets all already liked thesis from the student.
   *
   * @return
   */
  public Result fetchAllLikedThesis() {
    return studentRemoteDataSource.getAllLikedThesesFromAStudent();
  }

  /**
   * Get all viable theses for the currently active student and save them inside the ThesisRepository.
   */
  public ArrayList<Thesis> fetchAllSwipeableThesis() {
    Result result = studentRemoteDataSource.getAllThesisForAStudent();
    if (result.getSuccess()) {
      return ThesisRepository.getInstance().getTheses();

    } else {
      return new ArrayList<Thesis>();
    }
  }

  /**
   * Extends a user to a student (this occurs during the edit profile screen where the user has to input aditional information)
   *
   * @param degrees
   * @return Result
   */
  public Result editProfileStudent(ArrayList<Degree> degrees, String firstName, String lastName) {
    return studentRemoteDataSource.extendUserToStudent(degrees, firstName, lastName);
  }

  /**
   * Sends the form that is attached to each thesis (that the user has completed with answers) to the supervisors email.
   *
   * @param form
   * @param thesisId
   * @returnResult
   */
  public Result sendForm(final Form form, final UUID thesisId) {
    return studentRemoteDataSource.sendTheFormToTheSupervisor(form, thesisId);
  }

  /**
   * Removes an already liked thesis from a student profile.
   *
   * @param thesisId
   * @return Result
   */
  public Result removeLikedThesisFromStudent(UUID thesisId) {
    return studentRemoteDataSource.removeLikedThesisFromStudent(thesisId);
  }
}
