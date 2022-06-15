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

public interface ThesisApiService {


  // api/Theses HTTP requests

  /**
   * Adds a new thesis to the list of all available ones
   */

  @POST("/api/Theses")
  Call<List<Thesis>> postNewThesis();


  @GET("api/Users/Theses/{Uuid}")
  Call<Thesis> getThesis();

  /**
   * Applies changes to a specific thesis (determined through the id)
   *
   * @return List of calls
   */
  @PUT("api/Users/Theses/{Uuid}")
  Call<Thesis> changeThesis();

  /**
   * Deletes a specific thesis (determined through the id)
   *
   * @return List of calls
   */
  @DELETE("api/Users/Theses/{Uuid}")
  Call<List<Thesis>> deleteThesis();


  // api/Users HTTP requests


  /**
   * Creates a new registrated user to the backend.
   *
   * @param user
   * @return List of calls
   */

  @POST("/api/Users")
  Call<User> postNewUser(@Body User user);

  /**
   * Gets information about the users from the backend.
   *
   * @return List of calls
   */
  @GET("/api/Users")
  Call<List<User>> getNewUser();

  /**
   * This function gets a list of the Thesis objects
   * that the user has already liked.
   *
   * @return List of calls
   */
  @GET("/api/Users/Theses")
  Call<List<Thesis>> getTheses();

  /**
   * Gets the information about a specific user
   *
   * @return List of calls
   */
  @GET("api/Users/{Uuid}")
  Call<User> getUser();

  /**
   * Changes the information of a specific user
   *
   * @return List of calls
   */
  @PUT("api/Users/{Uuid}")
  Call<User> changeUser();

  /**
   * Deletes a specific user
   *
   * @return List of calls
   */
  @POST("api/Users")
  Call<List<Thesis>> postNewUser();
  @DELETE("api/Users/{Uuid}")
  Call<List<Thesis>> deleteUser();


  @POST("api/Users/{Uuid}/Verify")
  Call<Boolean> isVerified();


  // api/Unis HTTP requests

  @GET("api/Unis")
  Call<List<Thesis>> getUnis();


}
