package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;

import com.hfad.thinder.data.source.remote.Login;

import java.util.List;

import retrofit2.Call;
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
     * Posts the received token from the user that they received over an email and checks if it is correct (inside the backend).
     * @param token
     * @return true if the token is correct for the specific user id, else it returns false
     */
    @POST("users/verify")
    Response<Boolean> isVerifyToken(@Body String token);
    /**
     * Creates a new registrated user to the backend.
     *
     * @param user
     * @return List of Responses
     */

    @POST("/users/")
    Call<User> postNewUser(@Body User user);

    /**
     *
     * Used as login function -  also requests the user id.
     * @return List of Responses
     */
    @GET("users")
    Response<User> login(@Body Login login);

    /**
     * Returns all theses that the user has aready liked
     *
     * @return List of Responses
     */
    @GET("users/{Uuid}/Theses")
    Response<List<Thesis>> getTheses(@Path("id") int userId);

    /**
     *
     * Gets the information about a specific user
     *
     * @return List of Responses
     */
    @GET("users/{Uuid}")
    Response<User> getUser(@Path("id") int userId);

    /**
     * Changes the information of a specific user
     *
     * @return List of Responses
     */
    @PUT("students/{Uuid}")
    Response<Student> changeStudent(@Path("id") int studentId, @Body Student student);

    @PUT("supervisor/{Uuid}")
    Response<Supervisor> changeStudent(@Path("id") int supervisorId, @Body Supervisor supervisor);

    /**
     * Deletes a specific user
     *
     * @return List of Responses
     */
    @DELETE("users/{Uuid}")
    Response<User> deleteUser(@Path("id") int userId);



    /**
     * This function returns a specified thesis inside the users already liked theses
     *
     * @return List of Responses
     */
    @GET("users/{userId}/Theses/{thesisId}")
    Response<Thesis> getUserThesis(@Path("userId") int userId,@Path("userId") int thesisId );

    /**
     * Applies changes to a specific thesis (determined through the id)
     *
     * @return List of Responses
     */
    @PUT("users/{Uuid}/Theses/{thesisId}")
    Response<Thesis> changeUserThesis(@Path("Uuid") int userId,@Path("thesisId") int thesisId,@Body Thesis thesis);

    /**
     * Deletes a specific thesis (determined through the id)
     *
     * @return List of Responses
     */
    @DELETE("users/{userId}/Theses/{thesisId}")
    Response<Thesis> deleteUserThesis(@Path("userId") int userId,@Path("userId") int thesisId );

}
