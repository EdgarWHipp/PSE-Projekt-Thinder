package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThesisApiService {


    // api/Theses HTTP requests
    /**
     * Gets all theses that the supervisors have uploaded.
    */
    @GET("/api/Theses")
    Response<List<Thesis>> getAllTheses();
    /**
     * Adds a new thesis to the list of all available ones
     */

    @POST("/api/Theses")
    Response<Thesis> postNewThesis(@Body Thesis thesis);

    /**
     * Returns a specific thesis that exists inside the global list of added theses
     *
     * @return Response
     */
    @GET("/api/Theses/{Uuid}")
    Response<Thesis> getThesis(@Path("id") int thesisId);

    /**
     * Changes a specified Thesis inside the global list of all theses
     *
     * @return Response
     */
    @PUT("/api/Theses/{Uuid}")
    Response<Thesis> putNewThesis(@Path("id") int thesisId);

    /**
     * Deletes a specified thesis inside the backend (global list of all theses)
     *
     * @return Response
     */
    @DELETE("/api/Theses/{Uuid}")
    Response<Thesis> deleteThesis(@Path("id") int thesisId);





}
