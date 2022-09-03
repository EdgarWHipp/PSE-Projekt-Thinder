package com.hfad.thinder.data.source.remote.okhttp;

import static android.content.ContentValues.TAG;

import android.util.ArraySet;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisDTO;
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
    private static final ApiUtils apiUtils = ApiUtils.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();

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
                        new AuthInterceptor(userRepository.
                                getUser().getMail(), userRepository.getPassword()))
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
                .put("type", userRepository.getType().toString());
        RequestBody body = RequestBody.create(studentJson.toString(), JSON);

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    userRepository.login(userRepository.getPassword(), userRepository.getUser().getMail());
                    ((Student)userRepository.getUser()).setFirstName(firstName);
                    ((Student)userRepository.getUser()).setLastName(lastName);
                    ((Student)userRepository.getUser()).setDegrees(degrees);
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }
            }
        });
        return resultCompletableFuture;
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
                        new AuthInterceptor(userRepository.
                                getUser().getMail(), userRepository.
                                getPassword()))
                .build();

        String json = new Gson().toJson(ratings);
        RequestBody body = RequestBody.create(json, JSON);
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }
            }
        });
        return resultCompletableFuture;
    }

    /**
     * This function parses the arraylist of ThesisDTO object that the backend returns to a Hashmap<UUID,Thesis> (this is necessary for the frontend)
     * @param theses
     * @return HashMap<UUID,Thesis>
     */
    private HashMap<UUID,Thesis> parseHashMap(ArrayList<ThesisDTO> theses){
        ArrayList<Thesis> returnTheses = new ArrayList<>();
        Thesis thesis=new Thesis();
        Set<Image> images= new ArraySet<>();
        Set<Degree> degrees= new ArraySet<>();
        for(ThesisDTO thesisIter : theses){
            thesis.setId(thesisIter.getId());
            thesis.setForm(new Form(thesisIter.getQuestions()));
            for(String string : thesisIter.getImages()){
                byte[] image = java.util.Base64.getDecoder().decode(string);
                images.add(new Image(image));
            }
            for(Degree degree : thesisIter.getPossibleDegrees()){
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
            thesis = new Thesis();
            images = new ArraySet<>();
            degrees = new ArraySet<>();
        }
        HashMap<UUID, Thesis> thesisHashMap = (HashMap<UUID, Thesis>) returnTheses.stream().collect(Collectors.toMap(v -> v.getId(), v -> v));
        return thesisHashMap;
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
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        CompletableFuture<HashMap<UUID, Thesis>> thesisListFuture = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
                thesisListFuture.complete(null);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String body = response.body().string();
                    ArrayList<ThesisDTO> theses = gson.fromJson(body, new TypeToken<List<ThesisDTO>>() {
                    }.getType());
                    //if the theses returned are null (which is a normal response), return a correct result value.
                    if(theses==null){
                        resultCompletableFuture.complete(new Result(true));
                        thesisListFuture.complete(null);
                        Log.e("","this is called");
                        return;
                    }


                    HashMap<UUID, Thesis> thesisHashMap = parseHashMap(theses);
                    resultCompletableFuture.complete(new Result(true));
                    thesisListFuture.complete(thesisHashMap);
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                    thesisListFuture.complete(null);
                }
            }
        });

        Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> resultCompletableFuturePair
                = new Pair<>(thesisListFuture, resultCompletableFuture);
        return resultCompletableFuturePair;
    }

    /**
     * This function parses the returned ArrayList<ThesisDTO> from the backend into a ArrayList<Thesis> (which is necessary for the frontend)
     * @param theses
     * @return ArrayList<Thesis>
     */
    private ArrayList<Thesis> parseThesisArrayList(ArrayList<ThesisDTO> theses){
        ArrayList<Thesis> returnTheses = new ArrayList<>();

        Thesis thesis=new Thesis();
        Set<Image> images= new ArraySet<>();
        Set<Degree> degrees=new ArraySet<>();
        for(ThesisDTO thesisIter : theses){
            thesis.setId(thesisIter.getId());
            thesis.setForm(new Form(thesisIter.getQuestions()));
            for(String string : thesisIter.getImages()){
                byte[] image = java.util.Base64.getDecoder().decode(string);
                images.add(new Image(image));

            }
            for(Degree degree : thesisIter.getPossibleDegrees()){
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
            thesis = new Thesis();
            images = new ArraySet<>();
            degrees = new ArraySet<>();
        }
        return returnTheses;
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
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
                .build();

        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        CompletableFuture<ArrayList<Thesis>> resultThesisFuture = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
                resultThesisFuture.complete(null);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String body = response.body().string();
                    ArrayList<ThesisDTO> theses = (ArrayList<ThesisDTO>) gson.fromJson(body, new TypeToken<List<ThesisDTO>>() {
                    }.getType());
                    if (theses == null) {
                        resultCompletableFuture.complete(new Result(true));
                        resultThesisFuture.complete(null);
                    }

                    ArrayList<Thesis> returnTheses = parseThesisArrayList(theses);
                    resultCompletableFuture.complete(new Result(true));
                    resultThesisFuture.complete(returnTheses);


                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
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
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        JSONObject formJson = null;
        formJson = new JSONObject().put("answers", form.getAnswers()).put("questions", form.getQuestions());
        String jsonBodyString = formJson.toString();
        RequestBody body = RequestBody.create(jsonBodyString, JSON);

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
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
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
                .addPathSegment("students")
                .addPathSegment("rated-theses")
                .addPathSegment(thesisId.toString())
                .addPathSegment("remove")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = clientAuth.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }

            }
        });
        return resultCompletableFuture;
    }

}
