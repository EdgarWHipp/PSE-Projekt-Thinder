package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
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
import org.json.JSONException;
import org.json.JSONObject;

public class SupervisorApiService {
  private static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");
  private String host = "10.0.2.2";
  private String scheme = "http";
  private int port = 8080;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  /**
   * This function creates the HTTP PUT request that completes the user profile by extending the profile through either the additional attributes from the supervisor.
   * Checks if the asynchronous call return fails or responds.
   *
   * @param degree
   * @param institute
   * @param phoneNumber
   * @return CompletableFuture<Result>
   * @throws JSONException
   * @throws IOException
   */
  public CompletableFuture<Result> editSupervisorProfileFuture(String degree, String officeNumber,
                                                               String building, String institute,
                                                               String phoneNumber, String firstName,
                                                               String lastName)
      throws JSONException, IOException {
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
        .addInterceptor(
            new AuthInterceptor(UserRepository.getInstance().
                getUser().getMail(), UserRepository.getInstance().
                getPassword()))
        .build();
    Log.e("", UserRepository.getInstance().
        getUser().getMail() + UserRepository.getInstance().
        getPassword());
    JSONObject supervisorJson = new JSONObject()
        .put("academicDegree", degree)
        .put("officeNumber", officeNumber)
        .put("building", building)
        .put("institute", institute)
        .put("phoneNumber", phoneNumber)
        .put("firstName", firstName)
        .put("lastName", lastName)
        .put("type", "SUPERVISOR");


    RequestBody body = RequestBody.create(supervisorJson.toString(), JSON);
    Log.e("", supervisorJson.toString());
    Log.e("", scheme);
    HttpUrl url = new HttpUrl.Builder()
        .scheme(scheme)
        .host(host)
        .port(port)
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
        resultCompletableFuture.complete(new Result(e.toString(), false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
          Supervisor current = (Supervisor) UserRepository.getInstance().getUser();
          current.setFirstName(firstName);
          current.setLastName(lastName);
          current.setInstitute(institute);
          current.setAcademicDegree(degree);
          current.setBuilding(building);
          current.setOfficeNumber(officeNumber);
          current.setPhoneNumber(phoneNumber);
          UserRepository.getInstance().setUser(current);
          resultCompletableFuture.complete(new Result("success", true));
        } else {
          resultCompletableFuture.complete(new Result("error", false));
        }
      }
    });

    return resultCompletableFuture;
  }

  /**
   * The function that creates the actual HTTP PUT request that edits a specific thesis in the backend.
   *
   * @param thesisId
   * @param thesis
   * @return A CompletableFuture<Result> that is later evaluated in the RemoteDataSource classes
   * @throws JSONException
   */
  public CompletableFuture<Result> editThesisFuture(final UUID thesisId, Thesis thesis)
      throws JSONException {
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
        .scheme(scheme)
        .host(host)
        .port(port)
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
        resultCompletableFuture.complete(new Result("error", false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) {
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
   * Performs the actual HTTP GET request that gets all already created theses from a supervisor.
   *
   * @return Pair<CompletableFuture < ArrayList < Thesis>>,CompletableFuture<Result>>
   */
  public Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> getCreatedThesisFromSupervisorFuture() {
    OkHttpClient clientAuth = new OkHttpClient.Builder()
        .addInterceptor(new AuthInterceptor
            (UserRepository.getInstance().getUser().getMail(),
                UserRepository.getInstance().getPassword()))
        .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    CompletableFuture<HashMap<UUID, Thesis>> thesisHashmap = new CompletableFuture<>();

    HttpUrl url = new HttpUrl.Builder()
        .scheme(scheme)
        .host(host)
        .port(port)
        .addPathSegment("theses").build();

    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result("not successful", false));

      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {

          resultCompletableFuture.complete(new Result(true));
          Gson gson = new Gson();
          String body = response.body().string();
          ArrayList<Thesis> theses = gson.fromJson(body, new TypeToken<List<Thesis>>() {
          }.getType());
          HashMap<UUID, Thesis> thesisHashMap = (HashMap<UUID, Thesis>) theses.stream()
              .collect(Collectors.toMap(v -> v.getId(), v -> v));
          resultCompletableFuture.complete(new Result(true));
          thesisHashmap.complete(thesisHashMap);
        } else {
          resultCompletableFuture.complete(new Result("not successful", false));
        }
      }
    });
    return new Pair<>(thesisHashmap, resultCompletableFuture);
  }
}
