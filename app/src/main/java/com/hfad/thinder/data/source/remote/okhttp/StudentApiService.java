package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisDTO;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONArray;
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

/**
 * This class realises the HTTP calls that are relevant for the student.
 * These include editing the students profile, ratings theses through the swipe screen and getting already liked theses returned to the student.
 */
public class StudentApiService {
    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String scheme = "http";
    private String host = "10.0.2.2";
    private int port = 8080;

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
     * This function creates the HTTP PUT request that completes the user profile by extending the profile through either the additional attributes from the student.
     * Checks if the asynchronous call return fails or responds.
     *
     * @param degrees
     * @return CompletableFuture<Result>
     */
    public CompletableFuture<Result> editStudentProfileFuture(ArrayList<Degree> degrees, String firstName, String lastName) throws JSONException, IOException {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(
                        new AuthInterceptor(UserRepository.getInstance().
                                getUser().getMail(), UserRepository.getInstance().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        JSONArray degreesJsonArray = new JSONArray();
        JSONObject degreeJson;
        for (Degree degree : degrees) {
            degreeJson = new JSONObject().put("id", degree.getId());
            degreesJsonArray.put(degreeJson);
        }

        JSONObject studentJson = new JSONObject()
                .put("degrees", degreesJsonArray)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("type", UserRepository.getInstance().getUser().getRole().toString());
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
                resultCompletableFuture.complete(new Result(e.toString(), false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("","worked");
                    UserRepository.getInstance().login(UserRepository.getInstance().getPassword(), UserRepository.getInstance().getUser().getMail());
                    UserRepository.getInstance().getUser().setFirstName(firstName);
                    UserRepository.getInstance().getUser().setLastName(lastName);
                    ((Student)UserRepository.getInstance().getUser()).setDegrees(degrees);
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result("not successful", false));
                }
            }
        });
        return resultCompletableFuture;
    }


    /**
     * Performs the actual HTTP GET Request that
     * returns a List<Thesis> of all the theses that a student has liked.
     *
     * @param id
     * @return
     */
    public Pair<CompletableFuture<List<Thesis>>, CompletableFuture<Result>> getLikedThesesFuture(final UUID id) {
        //Add HTTP BASIC authentication
        CompletableFuture<List<Thesis>> listCompletableFuture = new CompletableFuture<>();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(
                        new AuthInterceptor(UserRepository.getInstance().
                                getUser().getMail(), UserRepository.getInstance().
                                getPassword()))
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
        Call call = clientAuth.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result("not successful", false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();

                    List<Thesis> thesisList = (List<Thesis>) gson.fromJson(response.body().string(), new TypeToken<List<Thesis>>() {
                    }.getType());
                    listCompletableFuture.complete(thesisList);
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result("not successful", false));
                }
            }
        });
        return new Pair<>(listCompletableFuture, resultCompletableFuture);
    }


    /**
     * This function creates the actual HTTP POST request that enables a student to like or dislike a thesis.
     * If the thesis is liked it can be found inside their liked-theses screen.
     *
     * @return CompletableFuture<Result> The result is later fetched in the StudentRemoteDataSource class.
     * @throws JSONException
     */
    public CompletableFuture<Result> rateThesisFuture(final Collection<Pair<UUID, Boolean>> ratings) throws JSONException {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(
                        new AuthInterceptor(UserRepository.getInstance().
                                getUser().getMail(), UserRepository.getInstance().
                                getPassword()))
                .build();

        String json = new Gson().toJson(ratings);
        RequestBody body = RequestBody.create(json, JSON);
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
                resultCompletableFuture.complete(new Result("not successful", false));
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
     * Get all already liked thesis for the student. If the response is successful, set the Hashmap in the ThesisRepository for the viewmodel.
     *
     * @return Pair<CompletableFuture < HashMap < UUID, Thesis>>, CompletableFuture<Result>>
     */
    public Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> getAllPositiveRatedThesesFuture() {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        CompletableFuture<HashMap<UUID, Thesis>> thesisListFuture = new CompletableFuture<>();

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
                    ArrayList<Thesis> theses = gson.fromJson(body, new TypeToken<List<Thesis>>() {
                    }.getType());
                    HashMap<UUID, Thesis> thesisHashMap = (HashMap<UUID, Thesis>) theses.stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
                    resultCompletableFuture.complete(new Result(true));
                    thesisListFuture.complete(thesisHashMap);
                } else {
                    resultCompletableFuture.complete(new Result("not successful", false));
                    thesisListFuture.complete(null);
                }
            }
        });

        Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> resultCompletableFuturePair
                = new Pair<>(thesisListFuture, resultCompletableFuture);
        return resultCompletableFuturePair;
    }

    /**
     * Returns all theses that a student swipes, based on a certain critiera (currently just implemented as random in the backend)
     *
     * @return Pair<CompletableFuture < ArrayList < Thesis>>,CompletableFuture<Result>>
     */
    public Pair<CompletableFuture<ArrayList<Thesis>>, CompletableFuture<Result>> getAllThesesForTheStudentFuture() {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
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
                resultCompletableFuture.complete(new Result("error", false));
                resultThesisFuture.complete(null);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String body = response.body().string();
                    ArrayList<ThesisDTO> theses = (ArrayList<ThesisDTO>) gson.fromJson(body, new TypeToken<List<Thesis>>() {
                    }.getType());
                    //convert ThesisDTO to Thesis Does this work mhh?
                    ArrayList<Thesis> returnTheses = new ArrayList<>();
                    Thesis thesis=null;
                    Set<Image> images= null;
                    Set<Degree> degrees=null;
                    for(ThesisDTO thesisIter : theses){
                        thesis.setId(thesisIter.getId());
                        thesis.setForm(new Form(thesisIter.getQuestions()));
                        for(Image image : thesis.getImages()){
                            images.add(image);
                        }
                        for(Degree degree : thesis.getPossibleDegrees()){
                            degrees.add(degree);
                        }
                        thesis.setImages(images);
                        thesis.setMotivation(thesisIter.getMotivation());
                        thesis.setName(thesisIter.getName());
                        thesis.setPossibleDegrees(degrees);
                        thesis.setSupervisingProfessor(thesisIter.getSupervisingProfessor());
                        thesis.setSupervisor(thesisIter.getSupervisor());
                        thesis.setTask(thesisIter.getTask());
                        returnTheses.add(thesis);
                    }

                    resultCompletableFuture.complete(new Result(true));
                    resultThesisFuture.complete(returnTheses);


                } else {
                    resultCompletableFuture.complete(new Result("error", false));
                    resultThesisFuture.complete(null);
                }

            }

        });
        return new Pair<CompletableFuture<ArrayList<Thesis>>, CompletableFuture<Result>>(resultThesisFuture, resultCompletableFuture);
    }

    /**
     * Performs the actual HTTP POST request that sends the form to the supervisor mail. Both questions and answers are included.
     *
     * @param form
     * @param thesisId
     * @return CompletableFuture<Result>
     * @throws JSONException
     */
    public CompletableFuture<Result> sendThesisFormToSupervisorFuture(Form form, final UUID thesisId) throws JSONException {

        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getUser().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        JSONObject formJson = null;
        formJson = new JSONObject().put("answers", form.getAnswers()).put("questions", form.getQuestions());
        String jsonBodyString = formJson.toString();
        RequestBody body = RequestBody.create(jsonBodyString, JSON);
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("students")
                .addPathSegment("rated-theses")
                .addPathSegment(thesisId.toString())
                .addPathSegment("form")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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
                } else {
                    resultCompletableFuture.complete(new Result("not successful", false));
                }
            }
        });
        return resultCompletableFuture;


    }

    /**
     * This function creates the actual HTTP POST request that removes an already liked thesis from the list of liked theses from the student.
     *
     * @param thesisId
     * @return CompletableFuture<Result>
     */

    public CompletableFuture<Result> removeALikedThesisFromAStudentFuture(final UUID thesisId) {
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
                resultCompletableFuture.complete(new Result("error", false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result("error", false));
                }

            }
        });
        return resultCompletableFuture;
    }

}
