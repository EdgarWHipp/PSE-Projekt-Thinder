package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.retrofit.ThesisApiService;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThesisRemoteDataSource {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    UsersApiService userService;
    OkHttpClient client = new OkHttpClient();
    String url = "http://localhost:8080";

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

    public Result createNewThesis(final Thesis thesis) {
        try {
            JSONObject thesisJson = new JSONObject()
                    .put("name",thesis.getName())
                    .put("body",thesis.getBody())
                    .put("form",thesis.getForm())
                    .put("images",thesis.getImages())
                    .put("supervisor",thesis.getSupervisor())
                    .put("studentRatings",thesis.getStudentRatings())
                    .put("possibleDegrees",thesis.getPossibleDegrees());



            RequestBody body=RequestBody.create(thesisJson.toString(),JSON);


            Request request= new Request.Builder()
                    .url(url+"/theses/")
                    .post(body)
                    .build();

            Call call = client.newCall(request);
            okhttp3.Response response = call.execute();
            if (response.isSuccessful()){
                return new Result(true);
            }else{
                return new Result("did not receive Statuscode 200",false);
            }
        } catch (Exception e) {
            return new Result(e.toString(),false);
        }

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
