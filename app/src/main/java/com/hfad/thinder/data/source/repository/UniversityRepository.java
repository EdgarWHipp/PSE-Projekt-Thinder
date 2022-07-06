package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.remote.UniversityRemoteDataSource;

import java.util.Set;

public class UniversityRepository {
  private static UniversityRepository INSTANCE;
  private UniversityRemoteDataSource universityRemoteDataSource =new UniversityRemoteDataSource();

  private UniversityRepository() {

  }

  /**
   * @return current instance of UniversityRepository singleton class.
   */
  public static UniversityRepository getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UniversityRepository();
    }
    return INSTANCE;
  }

  public boolean addUniversity(String name, Set<User> users,String studentMailRegex,String supervisorMailRegex){
    return false;

  }
}
