package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.Login;

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
     *
     * Used as login function -  also requests the user id.
     * @return List of Responses
     */
    @GET("/api/Users")
    Response<User> login(@Body Login login);

    /**
     * Returns all theses that the user has aready liked
     *
     * @return List of Responses
     */
    @GET("/api/Users/{Uuid}/Theses")
    Response<List<Thesis>> getTheses(@Path("id") int userId);

    /**
     *
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
    @GET("api/Users/{userId}Theses/{thesisId}")
    Response<Thesis> getUserThesis(@Path("userId") int userId,@Path("userId") int thesisId );

    /**
     * Applies changes to a specific thesis (determined through the id)
     *
     * @return List of Responses
     */
    @PUT("api/Users/{Uuid}Theses/{thesisId}")
    Response<Thesis> changeUserThesis(@Path("Uuid") int userId,@Path("thesisId") int thesisId,@Body Thesis thesis);

    /**
     * Deletes a specific thesis (determined through the id)
     *
     * @return List of Responses
     */
    @DELETE("api/Users/{userId}/Theses/{thesisId}")
    Response<Thesis> deleteUserThesis(@Path("userId") int userId,@Path("userId") int thesisId );

}
