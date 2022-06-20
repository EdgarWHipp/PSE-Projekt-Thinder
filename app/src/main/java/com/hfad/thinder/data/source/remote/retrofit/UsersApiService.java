package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsersApiService {


  // api/Users HTTP requests


  /**
   * Creates a new registrated user to the backend.
   *
   * @param user
   * @return List of Responses
   */

  @POST("/api/Users")
  Response<User> postNewUser(@Body User user);

  /**
   * Gets information about the users from the backend.
   *
   * @return List of Responses
   */
  @GET("/api/Users")
  Response<List<User>> getUsers();

  /**
   * Returns all theses that the user has aready liked
   *
   * @return List of Responses
   */
  @GET("/api/Users/{Uuid}/Theses")
  Response<List<Thesis>> getTheses(@Path("id") int userId);

  /**
   * Posts a new registrated user to the backend.
   * Gets the information about a specific user
   *
   * @return List of Responses
   */
  @GET("api/Users/{Uuid}")
  Response<User> getUser(@Path("id") int userId);

  /**
   * Changes the information of a specific user
   *
   * @return List of Responses
   */
  @PUT("api/Users/{Uuid}")
  Response<User> changeUser(@Path("id") int userId, @Body User user);

  /**
   * Deletes a specific user
   *
   * @return List of Responses
   */
  @DELETE("api/Users/{Uuid}")
  Response<User> deleteUser(@Path("id") int userId);

  //Was macht das hier eigentlich ? TO-DO
  @POST("api/Users/{Uuid}/Verify")
  Response<Boolean> isVerified();

  /**
   * This function returns a specified thesis inside the users already liked theses
   *
   * @return List of Responses
   */
  @GET("api/Users/Theses/{Uuid}")
  Response<Thesis> getUserThesis(@Path("id") int thesisId);

  /**
   * Applies changes to a specific thesis (determined through the id)
   *
   * @return List of Responses
   */
  @PUT("api/Users/Theses/{Uuid}")
  Response<Thesis> changeUserThesis(@Path("id") int thesisId);

  /**
   * Deletes a specific thesis (determined through the id)
   *
   * @return List of Responses
   */
  @DELETE("api/Users/Theses/{Uuid}")
  Response<List<Thesis>> deleteUserThesis(@Path("id") int thesisId);

}
