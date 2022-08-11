package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThesesApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  private String scheme = "http";
  private String host = "10.0.2.2";
  private int port = 8080;
  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }





  /**
   * This function creates the actual HTTP POST request to the backend that leads to a created thesis object inside the database.
   * @param thesis
   * @return A CompletableFuture<Result> that is later evaluated in the RemoteDataSource classes
   * @throws JSONException
   */
  public CompletableFuture<Result> createNewThesisFuture(Thesis thesis) throws JSONException {
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor
                    (UserRepository.getInstance().getUser().getMail(),
                            UserRepository.getInstance().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    List<byte[]> byteArrayImages = thesis.getImages().stream().map(x->x.getImage()).collect(Collectors.toList());
    List<UUID> degreeUUIDS = (List<UUID>) thesis.getPossibleDegrees().stream().map(x->x.getId()).collect(Collectors.toList());
    JSONObject thesisJSON = new JSONObject()
            .put("name", thesis.getName())
            .put("motivation", thesis.getMotivation())
            .put("task", thesis.getTask())
            .put("questionForm", thesis.getForm().getQuestions())
            .put("images", byteArrayImages)
            .put("possibleDegrees", degreeUUIDS)
            .put("id", UserRepository.getInstance().getUser().getId())
            .put("supervisingProfessor", thesis.getSupervisingProfessor());


    RequestBody body = RequestBody.create(thesisJSON.toString(), JSON);

    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("thesis")
            .build();
    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
    Call call = clientAuth.newCall(request);
    Log.e("",thesisJSON.toString());
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("failure", false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
          resultCompletableFuture.complete(new Result(true));
        } else {
          resultCompletableFuture.complete(new Result("not successful", false));
        }
      }

    });
    return resultCompletableFuture;
  }

  /**
   * This function creates the actual HTTP GET request that returns a specific thesis from the backend.
   * @param thesisId
   * @return A Pair of <CompletableFuture<Thesis>,CompletableFuture<Result>
   */
  public Pair<CompletableFuture<Thesis>,CompletableFuture<Result>>  getSpecificThesisFuture(UUID thesisId){
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor
                    (UserRepository.getInstance().getUser().getMail(),
                            UserRepository.getInstance().getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    CompletableFuture<Thesis> resultThesis = new CompletableFuture<>();

    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("thesis")
            .addPathSegment(thesisId.toString()).build();
    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("failure",false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if(response.isSuccessful()){
              //Parse thesis object from thesis json.
              Gson gson = new Gson();
              Thesis thesis = gson.fromJson(response.body().string(), Thesis.class);
              resultThesis.complete(thesis);
              resultCompletableFuture.complete(new Result(true));
            }else {
              resultCompletableFuture.complete(new Result("not successful",false));
            }
      }
    });




    return new Pair<>(resultThesis,resultCompletableFuture);
  }

  /**
   * This function creates the actual HTTP DELETE request that deletes a specific thesis.
   * @param thesisId
   * @return CompletableFuture<Result>
   */
  public CompletableFuture<Result> deleteThesisFuture(final UUID thesisId){
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor
                    (UserRepository.getInstance().getUser().getMail(),
                            UserRepository.getInstance().getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("thesis")
            .addPathSegment(thesisId.toString()).build();

    Request request = new Request.Builder()
            .url(url)
            .delete()
            .build();

    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("error",false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
          resultCompletableFuture.complete(new Result(true));
        }else{
          resultCompletableFuture.complete(new Result("not successful",false));
        }
      }
    });

    return resultCompletableFuture;
  }




}
