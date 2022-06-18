package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThesisApiService {


  // api/Theses HTTP requests

  /**
   * Adds a new thesis to the list of all available ones
   */

  @POST("/api/Theses")
  Call<Thesis> postNewThesis();
  /**
   * Returns a specific thesis that exists inside the global list of added theses
   *
   * @return call
   */
  @GET("/api/Theses/{Uuid}")
  Call<List<Thesis>> getThesis(@Path("id") int thesisId);
  /**
   * Changes a specified Thesis inside the global list of all theses
   *
   * @return call
   */
  @PUT("/api/Theses/{Uuid}")
  Call<Thesis> putNewThesis(@Path("id") int thesisId);
  /**
   * Deletes a specified thesis inside the backend (global list of all theses)
   *
   * @return call
   */
  @DELETE("/api/Theses/{Uuid}")
  Call<Thesis> deleteThesis(@Path("id") int thesisId);




  // api/Unis HTTP requests {TO-DO -> Where do i put this?}
  /**
   *Returns a specific university?
   *
   * @return List of calls
   */
  @GET("api/Unis")
  Call<List<Thesis>> getUnis();


}
