package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ThesisApiService {
    /**
     * This function gets a list of the Thesis objects
     * that the user needs to continue swiping.
     * @return List of calls
     */
    @GET("api/Theses")
    Call<List<Thesis>> getTheses();
    /**
     * Posts a new registrated user to the backend.
     * @return List of calls
     */
    @POST("api/Users")
    Call<List<Thesis>> postNewUser();
}
