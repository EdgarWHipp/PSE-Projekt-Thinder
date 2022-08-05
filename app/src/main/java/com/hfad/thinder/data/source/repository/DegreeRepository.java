package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;

import java.util.ArrayList;
import java.util.List;

public class DegreeRepository {
    public List<String> getAcademicTitles(){
        List<String> list = null;
        list.add("Dr.");
        list.add("Prof.");
        list.add("Dr. Prof.");
        list.add("Msc");
        list.add("Bsc");
        list.add("Dr. Dr.");
        list.add("Dr. Dr. Prof");
        return list;
    }

    public ArrayList<String> getAllStudentDegrees(){
        return null;

    }


}
