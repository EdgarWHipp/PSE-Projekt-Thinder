package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.University;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UniversityApiService {
  /**
   * Returns a specific university?
   *
   * @return List of Responses
   */
  @GET("university")
  Response<List<String>> getUnis();

  /**
   * Creates a new university entry
   * @return
   */
  @Headers({"Accept:application/json","Content-Type:application/json"})
  @FormUrlEncoded
  @POST("/university")
  Response<University> addUni(@Field("name") String name,
                              @Field("supervisorMailRegex") String supervisorMailRegex,
                              @Field("studentMailRegex") String studentMailRegex);

}
