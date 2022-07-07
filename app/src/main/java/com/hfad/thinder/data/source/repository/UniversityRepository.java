package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.University;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.remote.UniversityRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

import java.util.Set;

public final class UniversityRepository {
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

  public Result addUniversity(String name, String studentMailRegex, String supervisorMailRegex){
     return universityRemoteDataSource.createUniversity(new University(name,studentMailRegex,supervisorMailRegex));

  }
}
