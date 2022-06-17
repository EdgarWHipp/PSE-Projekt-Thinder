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
  Call<Thesis> postNewThesis();
  /**
   * Returns a specific thesis that exists inside the global list of added theses
   *
   * @return call
   */
  @GET("/api/Theses/{Uuid}")
  Call<List<Thesis>> getThesis();
  /**
   * Changes a specified Thesis inside the global list of all theses
   *
   * @return call
   */
  @PUT("/api/Theses/{Uuid}")
  Call<Thesis> putNewThesis();
  /**
   * Deletes a specified thesis inside the backend (global list of all theses)
   *
   * @return call
   */
  @DELETE("/api/Theses/{Uuid}")
  Call<Thesis> deleteThesis();



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
   * Returns all theses that the user has aready liked
   * @return List of calls
   */
  @GET("/api/Users/Theses")
  Call<List<Thesis>> getTheses();

  /**
   * Posts a new registrated user to the backend.
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
  @DELETE("api/Users/{Uuid}")
  Call<List<Thesis>> deleteUser();


  @POST("api/Users/{Uuid}/Verify")
  Call<Boolean> isVerified();

  /**
   * This function returns a specified thesis inside the users already liked theses
   *
   * @return List of calls
   */
  @GET("api/Users/Theses/{Uuid}")
  Call<Thesis> getUserThesis();

  /**
   * Applies changes to a specific thesis (determined through the id)
   *
   * @return List of calls
   */
  @PUT("api/Users/Theses/{Uuid}")
  Call<Thesis> changeUserThesis();

  /**
   * Deletes a specific thesis (determined through the id)
   *
   * @return List of calls
   */
  @DELETE("api/Users/Theses/{Uuid}")
  Call<List<Thesis>> deleteUserThesis();

  // api/Unis HTTP requests
  /**
   *Returns a specific university?
   *
   * @return List of calls
   */
  @GET("api/Unis")
  Call<List<Thesis>> getUnis();


}
