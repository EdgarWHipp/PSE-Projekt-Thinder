package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

import java.util.UUID;

public class StudentRepository {

  private static StudentRepository INSTANCE;

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

  private StudentRemoteDataSource studentRemoteDataSource =new StudentRemoteDataSource();
  public Result rateThesis(final UUID thesisId, final boolean rating){
    return studentRemoteDataSource.rateThesis(thesisId,rating);
  }
}
