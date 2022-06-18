package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;

import java.util.List;

import retrofit2.Response;

public class UsersRemoteDataSource {
  private UsersApiService apiService;


  private List<Thesis> getThesesFromUser(int userId) {
    try {
      Response<List<Thesis>> result = apiService.getTheses();
      if (result.isSuccessful()) {
        List<Thesis> returnVal = result.body();
        return returnVal;

      } else {
        // TO DO - bad practise!, dont return null return some error
        return null;
      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return null return some error
      return null;
    }


  }

  private List<User> getUsers() {
    Response<List<User>> result = apiService.getUsers();
    try {
      if (result.isSuccessful()) {
        List<User> returnVal = result.body();
        return returnVal;

      } else {
        // TO DO - bad practise!, dont return null return some error
        return null;
      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return null return some error
      return null;
    }

  }

  private User postNewUser(User user) {
    try {
      Response<User> result = apiService.postNewUser(user);
      if (result.isSuccessful()) {
        User returnVal = result.body();
        return returnVal;

      } else {
        // TO DO - bad practise!, dont return null return some error
        return null;
      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return null return some error
      return null;
    }


  }

}
