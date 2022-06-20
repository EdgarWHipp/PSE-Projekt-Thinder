package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.UserRepository;

import retrofit2.Retrofit;

public final class RetrofitThesisInstance {
  private static RetrofitThesisInstance INSTANCE;
  /**
   * Get an instance of the retrofit instance for the theses.
   *
   * @return the current singleton instance is returned
   */
  public static RetrofitThesisInstance getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new RetrofitThesisInstance();
    }
    return INSTANCE;
  }
  Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://sampleAddress.com/api/Theses")
          .build();

  ThesisApiService thesisService = retrofit.create(ThesisApiService.class);
}
