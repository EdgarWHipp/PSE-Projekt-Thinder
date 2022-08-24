package com.hfad.thinder.data.source.remote.okhttp;

import android.util.ArraySet;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisDTO;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
 * This class creates all HTTP requests that are relevant for a supervisor.
 */
public class SupervisorApiService {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final ApiUtils apiUtils = ApiUtils.getInstance();

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
        //Add HTTP BASIC authentication

        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(
                        new AuthInterceptor(UserRepository.getInstance().
                                getUser().getMail(), UserRepository.getInstance().
                                getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
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

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                    UserRepository.getInstance().login(UserRepository.getInstance().getPassword(), UserRepository.getInstance().getUser().getMail());
                    UserRepository.getInstance().getUser().setFirstName(firstName);
                    UserRepository.getInstance().getUser().setLastName(lastName);
                    ((Supervisor)UserRepository.getInstance().getUser()).setPhoneNumber(phoneNumber);
                    ((Supervisor)UserRepository.getInstance().getUser()).setBuilding(building);
                    ((Supervisor)UserRepository.getInstance().getUser()).setOfficeNumber(officeNumber);
                    ((Supervisor)UserRepository.getInstance().getUser()).setInstitute(institute);
                    ((Supervisor)UserRepository.getInstance().getUser()).setAcademicDegree(degree);
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
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
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

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        CompletableFuture<HashMap<UUID, Thesis>> thesisHashmap = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
                .addPathSegment("thesis").build();

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
                    Log.e("","call successful");
                    Gson gson = new Gson();
                    String body = response.body().string();
                    ArrayList<ThesisDTO> theses = gson.fromJson(body, new TypeToken<List<ThesisDTO>>() {
                    }.getType());
                    //convert ThesisDTO to Thesis Does this work mhh?
                    ArrayList<Thesis> returnTheses = new ArrayList<>();
                    Thesis thesis = new Thesis();
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
                    HashMap<UUID, Thesis> thesisHashMap = (HashMap<UUID, Thesis>) returnTheses.stream()
                            .collect(Collectors.toMap(v -> v.getId(), v -> v));
                    resultCompletableFuture.complete(new Result(true));
                    Log.e("",thesisHashMap.get(theses.get(0).getId()).toString());
                    thesisHashmap.complete(thesisHashMap);
                } else {
                    Log.e("",new String(String.valueOf(response.code())));
                    resultCompletableFuture.complete(new Result("not successful", false));
                }
            }
        });
        return new Pair<>(thesisHashmap, resultCompletableFuture);
    }
}
