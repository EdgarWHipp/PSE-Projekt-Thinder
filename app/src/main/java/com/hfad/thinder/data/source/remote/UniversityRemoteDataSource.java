package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.University;
import com.hfad.thinder.data.source.remote.retrofit.UniversityApiService;
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
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UniversityRemoteDataSource {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  UsersApiService userService;
  OkHttpClient client = new OkHttpClient();
  String url = "http://localhost:8080";


  Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl("http:localhost:8080")
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


  public Result createUniversity(University university){

    try {
      JSONObject uniJson = new JSONObject()
              .put("name",university.getName())
              .put("studentMailRegex",university.getStudentMailRegex())
              .put("supervisorMailRegex",university.getSupervisorMailRegex());


      RequestBody body=RequestBody.create(uniJson.toString(),JSON);


      Request request= new Request.Builder()
              .url(url+"/university/")
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
