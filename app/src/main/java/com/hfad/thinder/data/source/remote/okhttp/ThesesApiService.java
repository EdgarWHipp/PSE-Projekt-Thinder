package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;

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
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080";
  private static final String emulatorLocalHost = "http://10.0.2.2:8080";
  private String scheme = "http";

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

  private String host = "10.0.2.2";
  private int port = 8080;

  /**
   * Get all already liked thesis for the student. If the response is successful, set the Hashmap in the ThesisRepository for the viewmodel.
   * @return Tuple<CompletableFuture<HashMap<UUID,Thesis>>, CompletableFuture<Result>>
   */
  public Tuple<CompletableFuture<HashMap<UUID,Thesis>>, CompletableFuture<Result>> getAllPositiveRatedThesesFuture() {
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor
                    (UserRepository.getInstance().getUser().getMail(),
                            UserRepository.getInstance().getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    CompletableFuture<HashMap<UUID,Thesis>> thesisListFuture = new CompletableFuture<>();

    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("students")
            .addPathSegment("rated-theses")
            .build();

    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();

    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result(e.toString(), false));
        thesisListFuture.complete(null);
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
          Gson gson = new Gson();
          String body = response.body().string();
          ArrayList<Thesis> theses  = gson.fromJson(body, new TypeToken<List<Thesis>>(){}.getType());
          HashMap<UUID,Thesis> thesisHashMap = (HashMap<UUID, Thesis>) theses.stream().collect(Collectors.toMap(v->v.getId(), v->v));
          resultCompletableFuture.complete(new Result(true));
          thesisListFuture.complete(thesisHashMap);
        } else {
          resultCompletableFuture.complete(new Result("not successful", false));
          thesisListFuture.complete(null);
        }
      }
    });

    Tuple<CompletableFuture<HashMap<UUID,Thesis>>, CompletableFuture<Result>> resultCompletableFutureTuple
            = new Tuple<>(thesisListFuture, resultCompletableFuture);
    return resultCompletableFutureTuple;
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
                            UserRepository.getInstance().getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    JSONObject thesisJSON = new JSONObject()
            .put("name", thesis.getName())
            .put("motivation", thesis.getMotivation())
            .put("task", thesis.getTask())
            .put("questionForm", thesis.getForm())
            .put("images", thesis.getImages())
            .put("possibleDegrees", thesis.getPossibleDegrees())
            .put("supervisor", UserRepository.getInstance().getUser())
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
   * @return A Tuple of <CompletableFuture<Thesis>,CompletableFuture<Result>
   */
  public Tuple<CompletableFuture<Thesis>,CompletableFuture<Result>>  getSpecificThesisFuture(UUID thesisId){
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




    return new Tuple<>(resultThesis,resultCompletableFuture);
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

  /**
   * Returns all theses that a student swipes, based on a certain critiera (currently just implemented as random in the backend)
   * @return Tuple<CompletableFuture<ArrayList<Thesis>>,CompletableFuture<Result>>
   */
  public Tuple<CompletableFuture<ArrayList<Thesis>>,CompletableFuture<Result>> getAllThesesForTheStudentFuture(){
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor
                    (UserRepository.getInstance().getUser().getMail(),
                            UserRepository.getInstance().getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    CompletableFuture<ArrayList<Thesis>> resultThesisFuture = new CompletableFuture<>();
    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("students")
            .addPathSegment("theses")
            .addPathSegment("get-swipe-theses")
            .build();
    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();

    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("error",false));
        resultThesisFuture.complete(null);
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
          Gson gson = new Gson();
          String body = response.body().string();
          ArrayList<Thesis> theses  = gson.fromJson(body, new TypeToken<List<Thesis>>(){}.getType());
          ThesisRepository.getInstance().setTheses(theses);

          resultCompletableFuture.complete(new Result(true));
          resultThesisFuture.complete(theses);


        }else{
            resultCompletableFuture.complete(new Result("error", false));
            resultThesisFuture.complete(null);
          }

        }

        });
    return new Tuple<CompletableFuture<ArrayList<Thesis>>,CompletableFuture<Result>>(resultThesisFuture,resultCompletableFuture);
  }

  /**
   * This function creates the actual HTTP POST request that removes an already liked thesis from the list of liked theses from the student.
   * @param thesisId
   * @return CompletableFuture<Result>
   */

  public CompletableFuture<Result> removeALikedThesisFromAStudentFuture(final UUID thesisId){
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor
                    (UserRepository.getInstance().getUser().getMail(),
                            UserRepository.getInstance().getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    RequestBody body = RequestBody.create(null, new byte[]{});
    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("students")
            .addPathSegment("rated-theses")
            .addPathSegment(thesisId.toString())
            .addPathSegment("remove")
            .build();
    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();

    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("error",false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(response.isSuccessful()) {
          resultCompletableFuture.complete(new Result(true));
        }else{
          resultCompletableFuture.complete(new Result("error", false));
        }

      }
    });
    return resultCompletableFuture;
  }
}
