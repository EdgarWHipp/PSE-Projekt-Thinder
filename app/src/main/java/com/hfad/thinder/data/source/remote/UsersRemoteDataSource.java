package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;

import java.util.List;
import java.util.Optional;

import retrofit2.Response;

public class UsersRemoteDataSource {
  private UsersApiService apiService;


  public List<Thesis> getThesesFromUser(int userId) {
    try {
      Response<List<Thesis>> result = apiService.getTheses(userId);
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

  public List<User> getUsers() {
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

  public boolean postNewUser(User user) {
    try {
      Response<User> result = apiService.postNewUser(user);
      if (result.isSuccessful()) {
        //User returnVal = result.body();
        return true;

      } else {
        // TO DO - bad practise!, dont return false return some error
        return false;
      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return false return some error
      return false;
    }


  }

  public boolean deleteUser(final int id) {
    try {
      Response<User> result = apiService.deleteUser(id);
      if (result.isSuccessful()) {
        //User returnVal = result.body();
        return true;

      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return false return some error
      return false;
    }
    return false;
  }

  public Optional<User> getUser(final int id) {
    try {
      Response<User> result = apiService.getUser(id);
      if (result.isSuccessful() && result.body() != null) {
        User returnVal = result.body();
        return Optional.of(returnVal);

      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return false return some error
      return Optional.ofNullable(null);
    }
    return Optional.ofNullable(null);
  }

  public boolean updateUser(final int id, final User user) {
    try {
      Response<User> result = apiService.changeUser(id, user);
      if (result.isSuccessful() && result.body() != null) {

        return true;

      }
    } catch (Exception e) {
      System.out.println(e);
      // TO DO - bad practise!, dont return false return some error
      return false;
    }
    return false;
  }

}
