package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080";
  private static final String  emulatorLocalHost = "http://10.0.2.2:8080";
  /**
   * This function creates the HTTP PUT request that completes the user profile by extending the profile through either the additional attributes from the student.
   *Checks if the asynchronous call return fails or responds.
   * @param degrees
   * @return CompletableFuture<Result>
   */
  public CompletableFuture<Result> editStudentProfileFuture(Set<Degree> degrees, String firstName, String lastName) throws JSONException, IOException {
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().geteMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();
    JSONObject studentJson = new JSONObject()
            .put("degrees", degrees)
            .put("firstName",firstName)
            .put("lastName",lastName);


    RequestBody body = RequestBody.create(studentJson.toString(), JSON);


    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("users")
            .addPathSegment(UserRepository.getInstance().getCurrentUUID().toString())
            .build();

    Request request = new Request.Builder()
            .url(url)
            .put(body)
            .build();
    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result(e.toString(),false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(response.isSuccessful()){
          resultCompletableFuture.complete(new Result(true));
        }else{
          resultCompletableFuture.complete(new Result("not successful",false));
        }
      }
    });
    return resultCompletableFuture;
  }
  public Tuple<CompletableFuture<List<UUID>>,CompletableFuture<Result>> getSwipeOrder(final UUID id){
    CompletableFuture<Result> resultCompletableFuture=new CompletableFuture<>();
    CompletableFuture<List<UUID>> listOfIds = new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().geteMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();
    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("users")
            .addPathSegment(UserRepository.getInstance().getCurrentUUID().toString())
            .addPathSegment("thesis")
            .addPathSegment("get-swipe-stack")
            .build();

    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {

      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

      }
    });
    return null;
  }

  /**
   * This function creates the actual HTTP POST request that enables a student to like or dislike a thesis.
   * If the thesis is liked it can be found inside their liked-theses screen.
   * @param userId
   * @param thesisId
   * @param rating
   * @return CompletableFuture<Result> The result is later fetched in the StudentRemoteDataSource class.
   * @throws JSONException
   */
  public CompletableFuture<Result> rateThesisFuture(final UUID userId, final UUID thesisId,final boolean rating) throws JSONException {
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().geteMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();
    JSONObject booleanJSON = new JSONObject().put("rating",rating);
    RequestBody body = RequestBody.create(booleanJSON.toString(), JSON);
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("users")
            .addPathSegment(userId.toString())
            .addPathSegment("rated-thesis")
            .addPathSegment(thesisId.toString()).build();
    Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();

    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("not successful",false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(response.isSuccessful()){
          resultCompletableFuture.complete(new Result(true));
        }else {
          resultCompletableFuture.complete(new Result("not successful",false));
        }
      }
    });
    return resultCompletableFuture;
  }

  /**
   * Performs the actual HTTP GET Request that
   * returns a List<Thesis> of all the theses that a student has liked.
   * @param id
   * @return
   */
  public Tuple<CompletableFuture<List<Thesis>>,CompletableFuture<Result>> getLikedThesesFuture(final UUID id){
    CompletableFuture<List<Thesis>> listCompletableFuture = new CompletableFuture<>();
    CompletableFuture<Result> resultCompletableFuture= new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().geteMail(), UserRepository.getInstance().
                            getUser().getPassword()))
                            .build();


    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment(id.toString())
            .addPathSegment("rated-thesis").build();
    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
    Call call =clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("not successful",false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
          Gson gson = new Gson();

          List<Thesis> thesisList = (List<Thesis>)gson.fromJson(response.body().string(), new TypeToken<List<Thesis>>(){}.getType());
          listCompletableFuture.complete(thesisList);
          resultCompletableFuture.complete(new Result(true));
        }else{
          resultCompletableFuture.complete(new Result("not successful",false));
        }
      }
    });
    return new Tuple<>(listCompletableFuture,resultCompletableFuture);
  }
}
