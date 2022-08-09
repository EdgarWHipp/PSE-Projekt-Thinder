package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.remote.DegreeRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface for the Viewmodel to
 */
public class DegreeRepository {
    public static DegreeRepository INSTANCE;
    private ArrayList<Degree> allCoursesOfStudy= new ArrayList<>();
    private DegreeRemoteDataSource degreeRemoteDataSource = new DegreeRemoteDataSource();
    private DegreeRepository(){}

    public void setAllDegrees(ArrayList<Degree> allDegrees) {
        this.allCoursesOfStudy = allDegrees;
    }

    public ArrayList<Degree> getAllCoursesOfStudy() {
        return allCoursesOfStudy;
    }

    public void setAllCoursesOfStudy(ArrayList<Degree> allCoursesOfStudy) {
        this.allCoursesOfStudy = allCoursesOfStudy;
    }

    /**
     * @return current instance of DegreeRepository singleton class.
     */
    public static DegreeRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DegreeRepository();
        }
        return INSTANCE;
    }

    public  ArrayList<String> getAcademicTitles(){
        ArrayList<String> list = new ArrayList<>();
        list.add("M. Sc.");
        list.add("B. Sc.");
        list.add("Dr.");
        list.add("Prof.");
        list.add("Prof. Dr.");
        list.add("Dr. Dr.");
        list.add("Prof. Dr. Dr.");
        return list;
    }

    public ArrayList<String> getAllDegrees(){
        //Todo löschen
        ArrayList<String> selectedDegrees = new ArrayList<>();
        selectedDegrees.add("Bachelor Informatik");
        selectedDegrees.add("Master Mathematik");
        selectedDegrees.add("Bachelor Mathematik");
        selectedDegrees.add("Master Chemie");
        selectedDegrees.add("Master Germanistik");
        return selectedDegrees;

    }

    /**
     * Fetches all Degrees from the specified university of the user.
     * @return Result
     */
    public Result fetchAllDegrees(){
        return degreeRemoteDataSource.fetchAllCoursesOfStudyFromAUniverisity();
    }



}
