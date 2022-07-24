package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;

import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class SupervisorApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080";
  private static final String  emulatorLocalHost = "http://10.0.2.2:8080";

  /**
   * This function creates the HTTP PUT request that completes the user profile by extending the profile through either the additional attributes from the supervisor.
   *Checks if the asynchronous call return fails or responds.
   * @param degree
   * @param institute
   * @param phoneNumber
   * @return CompletableFuture<Result>
   * @throws JSONException
   * @throws IOException
   */
  public CompletableFuture<Result> editSupervisorProfileFuture(UUID id, String degree, String officeNumber,String building, String institute, String phoneNumber, String firstName, String lastName)
          throws JSONException, IOException {
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    JSONObject supervisorJson = new JSONObject()
            .put("academicDegree", degree)
            .put("officeNumber", officeNumber)
            .put("building",building)
            .put("institute", institute)
            .put("phoneNumber", phoneNumber)
            .put("firstName",firstName)
            .put("lastName",lastName);


    RequestBody body = RequestBody.create(supervisorJson.toString(), JSON);

    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("users")
            .addPathSegment("current")
            .build();
    Request request = new Request.Builder()
            .url(url)
            .put(body)
            .build();
    Call callSupervisor = client.newCall(request);
    callSupervisor.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result(e.toString(),false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()){
          resultCompletableFuture.complete(new Result(true));
        }else {
          resultCompletableFuture.complete(new Result("error",false));
        }
      }
    });

    return resultCompletableFuture;
  }
}
