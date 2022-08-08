package com.hfad.thinder.data.source.remote.okhttp;

import android.content.pm.ShortcutManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
import okhttp3.ResponseBody;

public class StudentApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080";
  private static final String  emulatorLocalHost = "http://10.0.2.2:8080";
  private String scheme = "http";
  private String host = "10.0.2.2";
  private int port = 8080;

  /**
   * This function creates the HTTP PUT request that completes the user profile by extending the profile through either the additional attributes from the student.
   *Checks if the asynchronous call return fails or responds.
   * @param degrees
   * @return CompletableFuture<Result>
   */
  public CompletableFuture<Result> editStudentProfileFuture(ArrayList<Degree> degrees, String firstName, String lastName) throws JSONException, IOException {
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().getMail(), UserRepository.getInstance().getPassword()))
            .build();

    //ArrayList<UUID> idOfDegrees = (ArrayList<UUID>) degrees.stream().map(v->v.getId());
    String uuidAsString = "b5e29596-1668-11ed-861d-0242ac120002";

    UUID sameUuid = UUID.fromString(uuidAsString);
    ArrayList<UUID> ids = new ArrayList<>();
    ids.add(sameUuid);
    ids.add(null);
    JSONObject studentJson = new JSONObject()
              .put("degrees", ids)
            .put("firstName",firstName)
            .put("lastName",lastName);
  Log.e("",studentJson.toString());
    RequestBody body = RequestBody.create(studentJson.toString(), JSON);

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
    Call call = clientAuth.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result(e.toString(),false));
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(response.isSuccessful()){
          Student current = (Student) UserRepository.getInstance().getUser();
          current.setFirstName(firstName);
          current.setLastName(lastName);
          current.setDegree(degrees);
          UserRepository.getInstance().setUser(current);
          UserRepository.getInstance().setProfileComplete(true);
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
                            getUser().getMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();
    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
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
                            getUser().getMail(), UserRepository.getInstance().
                            getUser().getPassword()))
                            .build();


    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
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

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }
  /**
   * This function creates the actual HTTP POST request that enables a student to like or dislike a thesis.
   * If the thesis is liked it can be found inside their liked-theses screen.
   * @return CompletableFuture<Result> The result is later fetched in the StudentRemoteDataSource class.
   * @throws JSONException
   */
  public CompletableFuture<Result> rateThesisFuture(final Collection<Tuple<UUID,Boolean>> ratings) throws JSONException {
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().getMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();

    String json = new Gson().toJson(ratings);
    RequestBody body = RequestBody.create(json,JSON);
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    HttpUrl url = new HttpUrl.Builder()
            .scheme(scheme)
            .host(host)
            .port(port)
            .addPathSegment("students")
            .addPathSegment("rated-theses").build();
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

  public CompletableFuture<Result> removeThesisFromLikedTheses(final UUID thesisId){
    OkHttpClient clientAuth = new OkHttpClient.Builder()
            .addInterceptor(
                    new AuthInterceptor(UserRepository.getInstance().
                            getUser().getMail(), UserRepository.getInstance().
                            getUser().getPassword()))
            .build();
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

    HttpUrl url = new HttpUrl.Builder()
            .host(host)
            .scheme(scheme)
            .port(port)
            .addPathSegment("students")
            .addPathSegment("rated-theses")
            .addPathSegment(thesisId.toString())
            .addPathSegment("remove").build();
    RequestBody body = RequestBody.create(null, new byte[]{});
    Request request =  new Request.Builder()
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
        if(response.isSuccessful()){
          resultCompletableFuture.complete(new Result(true));
        }else{
          resultCompletableFuture.complete(new Result("error",false));
        }
      }
    });
  return  resultCompletableFuture;
  }
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

}
