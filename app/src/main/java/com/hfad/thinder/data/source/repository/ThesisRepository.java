package com.hfad.thinder.data.source.repository;

import android.media.Image;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository {
    private static ThesisRepository INSTANCE;
    private ThesisRemoteDataSource thesisRemoteDataSource =new ThesisRemoteDataSource();
    private Thesis currentlySelectedThesis;
    private HashMap<UUID,Thesis> thesisMap = new HashMap<>();

    public void setCurrentlySelectedThesis(Thesis currentlySelectedThesis) {
        this.currentlySelectedThesis = currentlySelectedThesis;
    }

    public Thesis getCurrentlySelectedThesis() {
        return currentlySelectedThesis;
    }
    public Result setThesis(final UUID thesisId){
            if (thesisMap.containsKey(thesisId)){
                setCurrentlySelectedThesis(thesisMap.get(thesisId));
                return new Result((true));
            }else {
                return new Result("not successful",false);
            }
    }
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


   public Optional<List<Thesis>> getAll() {
        return null;
    }


    public Optional<Thesis> getById(final int id) {
        return null;
    }

    public HashMap<UUID, Thesis> getThesisMap() {
        return thesisMap;
    }

    public Result addThesis(String supervisingProf, String name, String motivation, String task, String form, Set<Degree> degrees , Set<Image> images) {
        return thesisRemoteDataSource.createNewThesis(new Thesis(supervisingProf,name,motivation,task,form,images,(Supervisor) UserRepository.getInstance().getUser(),degrees));
    }
    //Das Viewmodel holt diese ja eigentlich direkt aus dem Model?
    public Tuple<Thesis,Result> getThesis(final UUID id){
        return thesisRemoteDataSource.getNewThesis(id);
    }


    public boolean delete(final int id) {
        return false;
    }

    public Result editThesis(final UUID thesisId){

        return thesisRemoteDataSource
                .editThesis(thesisId,ThesisRepository.getInstance().getThesisMap().get(thesisId));


    }
}
