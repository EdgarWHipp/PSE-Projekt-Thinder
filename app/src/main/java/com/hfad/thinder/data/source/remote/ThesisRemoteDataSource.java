package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.okhttp.ThesesApiService;
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
    OkHttpClient client = new OkHttpClient();
    String url = "http://localhost:8080";

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://thinder-api.herokuapp.com")
            .build();

    ThesesApiService thesisService = retrofit.create(ThesesApiService.class);


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


}
