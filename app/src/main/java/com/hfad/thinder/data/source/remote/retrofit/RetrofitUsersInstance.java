package com.hfad.thinder.data.source.remote.retrofit;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;

import retrofit2.Retrofit;

public class RetrofitUsersInstance {
  private static RetrofitUsersInstance INSTANCE;
  /**
   * Get an instance of the retrofit instance for the theses.
   *
   * @return the current singleton instance is returned
   */
  public static RetrofitUsersInstance getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new RetrofitUsersInstance();
    }
    return INSTANCE;
  }
  Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://sampleAddress.com/api/Users")
          .build();

  UsersApiService userService = retrofit.create(UsersApiService.class);
}
