package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.retrofit.UniversityApiService;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;

import java.util.List;
import java.util.Optional;

import retrofit2.Response;
import retrofit2.Retrofit;

public class UniversityRemoteDataSource {
  Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://sampleAddress.com")
          .build();

  UniversityApiService universityService = retrofit.create(UniversityApiService.class);

  public Optional<List<String>> getUnis() {
    try {
      Response<List<String>> result = universityService.getUnis();
      if (result.isSuccessful() && result.body() != null) {

        Optional.of(result.body());

      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return false return some error
      return Optional.ofNullable(null);
    }
    return Optional.ofNullable(null);
  }
}
