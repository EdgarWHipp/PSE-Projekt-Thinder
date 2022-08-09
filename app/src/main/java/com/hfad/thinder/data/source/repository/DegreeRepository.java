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
    private boolean flag=false;
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
    /**
     * Fetches all Degrees from the specified university of the user.
     * @return Result
     */
    public ArrayList<Degree> fetchAllCoursesOfStudy(){
        Result result= degreeRemoteDataSource.fetchAllCoursesOfStudyFromAUniverisity();
            if(result.getSuccess() || flag==true){
                flag=true;
                return allCoursesOfStudy;
            }else{
                return null;
            }
    }



}
