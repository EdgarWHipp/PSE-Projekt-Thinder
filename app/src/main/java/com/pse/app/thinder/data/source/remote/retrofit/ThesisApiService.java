package com.pse.app.thinder.data.source.remote.retrofit;

import com.pse.app.thinder.data.model.Thesis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ThesisApiService {
    /**
     * This function gets a list of the Thesis objects that the user needs to continue swiping.
     */
    @GET("Theses")
    Call<List<Thesis>> getTheses();
}
