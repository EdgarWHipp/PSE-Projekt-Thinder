package com.hfad.thinder.data.source.repository;


import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository {
    private static ThesisRepository INSTANCE;
    private ThesisRemoteDataSource thesisRemoteDataSource =new ThesisRemoteDataSource();
    private SupervisorRemoteDataSource supervisorRemoteDataSource = new SupervisorRemoteDataSource();
    private StudentRemoteDataSource studentRemoteDataSource = new StudentRemoteDataSource();
    private Thesis currentlySelectedThesis;
    private HashMap<UUID,Thesis> thesisMap = new HashMap<>();
    private ArrayList<Thesis> theses = new ArrayList<>();
    private boolean flag = false;

  /**
   * Sets the thesis that the viewmodel identified through the id.
   * This function is used by the viewmodel to gain information about the currently opened thesis.
   * @param currentlySelectedThesis
   */
    public void setCurrentlySelectedThesis(Thesis currentlySelectedThesis) {
        this.currentlySelectedThesis = currentlySelectedThesis;
    }

    /**
     * This function is used by the viewmodel to return a previously selected thesis for the detailed thesis screen.
     * @return Thesis
     */
    public Thesis getCurrentlySelectedThesis() {
        return currentlySelectedThesis;
    }

  public void setTheses(ArrayList<Thesis> theses) {
    this.theses = theses;
  }

  /**
     * This function sets the Thesis for the getCurrentlySelectedThesis by taking the id that the viewmodel provides
     * and setting the currentlySelectedThesis value.
     * @param thesisId
     * @return
     */
    public Result setThesis(final UUID thesisId){
            if (thesisMap.containsKey(thesisId)){
                setCurrentlySelectedThesis(thesisMap.get(thesisId));
                return new Result((true));
            }else {
                return new Result("not successful",false);
            }
    }
    private ThesisRepository() {}

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
   * Returns a hashmap of all theses that the currently active student has liked.
   * @return HashMap<UUID, Thesis>
   */
    public HashMap<UUID, Thesis> getThesisMap() {
        Result result;
        if (UserRepository.getInstance().getUser().getRole()== USERTYPE.STUDENT){
            result= StudentRepository.getInstance().fetchAllLikedThesis();
            if(result.getSuccess()){
                return this.getThesisMap();
            }else{
                return null;
            }
        }else if (UserRepository.getInstance().getUser().getRole()== USERTYPE.SUPERVISOR){
            result= SupervisorRepository.getInstance().getAllCreatedTheses();
            if(result.getSuccess()){
                return this.getThesisMap();
            }else{
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public void setThesisMap(HashMap<UUID, Thesis> thesisMap) {
        this.thesisMap = thesisMap;
    }

    /**
   * Simply returns all theses that the student should be able to select from.
   * Taken directly from the thesisRepository.
   * @return ArrayList<Thesis>
   */
  public ArrayList<Thesis> getAllSwipeableTheses(){

      return theses;}





  /**
   * Adds a new thesis to the global list of all thesis that the students can
   * @param supervisingProf
   * @param name
   * @param motivation
   * @param task
   * @param form
   * @param degrees
   * @param images
   * @return Result class including success value and error message
   */
    public Result addThesis(String supervisingProf, String name, String motivation, String task, String form, Set<Degree> degrees , Set<Image> images) {
        return thesisRemoteDataSource.createNewThesis(new Thesis(supervisingProf,name,motivation,task,new Form(form),images,(Supervisor) UserRepository.getInstance().getUser(),degrees));
    }


    public Pair<Thesis,Result> getThesis(final UUID id){
        return thesisRemoteDataSource.getNewThesis(id);
    }


    /**
     * Edit an existing thesis.
     * @param thesisId
     * @return
     */
    public Result editThesis(final UUID thesisId,final Thesis thesis){

        return supervisorRemoteDataSource
                .editThesis(thesisId,thesis);


    }

    /**
     * Delete an existing thesis.
     * @param thesisId
     * @return
     */
    public Result deleteThesis(UUID thesisId){
        return thesisRemoteDataSource.deleteThesis(thesisId);
    }



    public Pair<Integer, Integer> getThesisStatistics(UUID thesisId){
        return new Pair<>(1, 99);
    }




}
