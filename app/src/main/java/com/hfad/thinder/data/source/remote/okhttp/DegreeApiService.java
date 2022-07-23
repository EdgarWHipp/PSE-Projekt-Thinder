package com.hfad.thinder.data.source.remote.okhttp;

import java.util.List;

public class DegreeApiService {

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
}
