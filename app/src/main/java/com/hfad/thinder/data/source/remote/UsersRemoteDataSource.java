package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;

import java.util.List;
import java.util.Optional;

import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersRemoteDataSource {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://sampleAddress.com")
            .build();

    UsersApiService userService = retrofit.create(UsersApiService.class);

    public List<Thesis> getThesesFromUser(int userId) {
        try {
            Response<List<Thesis>> result = userService.getTheses(userId);
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

    public Optional<List<User>> getUsers() {
        Response<List<User>> result = userService.getUsers();
        try {
            if (result.isSuccessful()) {
                List<User> returnVal = result.body();
                return Optional.of(returnVal);

            } else {
                // TO DO - bad practise!, dont return null return some error
                return Optional.ofNullable(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return null return some error
            return Optional.ofNullable(null);
        }

    }

    public boolean createNewUser(User user) {
        try {
            Response<User> result = userService.postNewUser(user);
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
            Response<User> result = userService.deleteUser(id);
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
            Response<User> result = userService.getUser(id);
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
            Response<User> result = userService.changeUser(id, user);
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
    public boolean deleteUserThesis(final int userId,final int thesisId){
        try {
            Response<Thesis> result = userService.deleteUserThesis(userId,thesisId);
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
    /* This function might be useless.

    public boolean changeLikedThesis(final int userId,final int thesisId, final Thesis thesis){
        try {
            Response<Thesis> result = userService.changeUserThesis(userId,thesisId,thesis);
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
    */

    public Optional<Thesis> getUserThesis(final int userId,final int thesisId){
        try {
            Response<Thesis> result = userService.getUserThesis(userId,thesisId);
            if (result.isSuccessful() && result.body() != null) {

                return Optional.of(result.body());

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(null);
    }
}