package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This Class is a collection of all HTTP requests that deal with courses of study in the backend.
 */
public class DegreeApiService {
  private static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");
  private final String host = "10.0.2.2";
  private final String scheme = "http";
  private final int port = 8080;

  /**
   * Implements the actual HTTP GET request that fetches all courses of study for the users university.
   *
   * @return Pair<CompletableFuture < ArrayList < Degree>>,CompletableFuture<Result>>
   */
  public Pair<CompletableFuture<ArrayList<Degree>>, CompletableFuture<Result>> fetchAllCoursesOfStudyFuture() {
    CompletableFuture<ArrayList<Degree>> degrees = new CompletableFuture<>();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
        .addInterceptor(
            new AuthInterceptor(UserRepository.getInstance().
                getUser().getMail(), UserRepository.getInstance().getPassword()))
        .build();
    HttpUrl url = new HttpUrl.Builder()
        .scheme(scheme)
        .host(host)
        .port(port)
        .addPathSegment("university")
        .addPathSegment(UserRepository.getInstance().getUser().getUniversityId().toString())
        .addPathSegment("degrees")
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
        if (response.isSuccessful()) {
          resultCompletableFuture.complete(new Result(true));
          String body = response.body().string();
          Gson gson = new Gson();
          Type listType = new TypeToken<ArrayList<Degree>>(){}.getType();

          ArrayList<Degree> list = gson.fromJson(body, listType);
          degrees.complete(list);
        } else {
          resultCompletableFuture.complete(new Result("not successful", false));
        }

      }
    });
    return new Pair<CompletableFuture<ArrayList<Degree>>, CompletableFuture<Result>>(degrees,
        resultCompletableFuture);
  }


}
