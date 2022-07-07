package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThesisApiService {


    // api/Theses HTTP requests
    /**
     * Gets all theses that the supervisors have uploaded.
    */
    @GET("theses")
    Response<List<Thesis>> getAllTheses();
    /**
     * Adds a new thesis to the list of all available ones
     */
    @Headers("Accept: application/json")
    @POST("theses")
    Response<Thesis> postNewThesis(@Body Thesis thesis);

    /**
     * Returns a specific thesis that exists inside the global list of added theses
     *
     * @return Response
     */
    @GET("theses/{Uuid}")
    Response<Thesis> getThesis(@Path("id") int thesisId);

    /**
     * Changes a specified Thesis inside the global list of all theses
     *
     * @return Response
     */
    @PUT("theses/{Uuid}")
    Response<Thesis> putNewThesis(@Path("id") int thesisId);

    /**
     * Deletes a specified thesis inside the backend (global list of all theses)
     *
     * @return Response
     */
    @DELETE("theses/{Uuid}")
    Response<Thesis> deleteThesis(@Path("id") int thesisId);





}
