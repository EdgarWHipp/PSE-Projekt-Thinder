package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Thesis;
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
  public CompletableFuture<Result> editSupervisorProfileFuture(String degree, String officeNumber,String building, String institute, String phoneNumber, String firstName, String lastName)
          throws JSONException, IOException {
    Log.i("", UserRepository.getInstance().
            getUser().getMail());
    Log.i("", UserRepository.getInstance().
            getUser().getPassword());
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().getMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();

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
    Call callSupervisor = clientAuth.newCall(request);
    callSupervisor.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result(e.toString(),false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()){
          resultCompletableFuture.complete(new Result("success",true));
        }else {
          resultCompletableFuture.complete(new Result("error",false));
        }
      }
    });

    return resultCompletableFuture;
  }
  /**
   * The function that creates the actual HTTP PUT request that edits a specific thesis in the backend.
   * @param thesisId
   * @param thesis
   * @return A CompletableFuture<Result> that is later evaluated in the RemoteDataSource classes
   * @throws JSONException
   */
  public CompletableFuture<Result> editThesisFuture(final UUID thesisId, Thesis thesis) throws JSONException {
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

    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("thesis")
            .addPathSegment(thesisId.toString()).build();

    RequestBody body = RequestBody.create(thesisJSON.toString(), JSON);
    Request request = new Request.Builder()
            .url(url)
            .put(body)
            .build();
    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("error",false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()){
          resultCompletableFuture.complete(new Result(true));
        }else {
          resultCompletableFuture.complete(new Result("not successful",false));
        }

      }
    });
    return resultCompletableFuture;
  }
}
