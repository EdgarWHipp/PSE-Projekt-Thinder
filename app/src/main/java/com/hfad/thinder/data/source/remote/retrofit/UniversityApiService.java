package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;

public interface UniversityApiService {
  /**
   * Returns a specific university?
   *
   * @return List of Responses
   */
  @GET("api/Unis")
  Response<List<String>> getUnis();

}
