package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.retrofit.ThesisApiService;

import java.util.List;
import java.util.Optional;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThesisRemoteDataSource {
    Retrofit retrofit = new Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://thinder-api.herokuapp.com")
            .build();

    ThesisApiService thesisService = retrofit.create(ThesisApiService.class);

    public Optional<List<Thesis>> getAllTheses() {
        try {
            Response<List<Thesis>> result = thesisService.getAllTheses();
            if (result.isSuccessful() && result.body() != null) {
                List<Thesis> returnVal = result.body();
                return Optional.of(returnVal);

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(null);
    }
    public Optional<Thesis> getThesis(final int id) {
        try {
            Response<Thesis> result = thesisService.getThesis(id);
            if (result.isSuccessful() && result.body() != null) {
                Thesis returnVal = result.body();
                return Optional.of(returnVal);

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(null);
    }

    public boolean createNewThesis(final Thesis thesis) {
        try {
            Response<Thesis> result = thesisService.postNewThesis(thesis);
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

    public boolean changeThesis(final int id) {
        try {
            Response<Thesis> result = thesisService.putNewThesis(id);
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
    public boolean deleteThesis(final int id) {
        try {
            Response<Thesis> result = thesisService.putNewThesis(id);
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
