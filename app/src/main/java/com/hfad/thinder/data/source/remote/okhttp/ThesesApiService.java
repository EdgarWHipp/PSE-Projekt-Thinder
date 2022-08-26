package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
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
import java.util.Base64;
import java.util.HashSet;
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
 * This class created all HTTP request that are relevant for the theses inside the application.
 * These include the create and delete thesis functionalities in addition to a function that simply returns a specified thesis.
 */
public class ThesesApiService {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final ApiUtils apiUtils = ApiUtils.getInstance();

    /**
     * This function creates the actual HTTP POST request to the backend that leads to a created thesis object inside the database.
     *
     * @param thesis
     * @return A CompletableFuture<Result> that is later evaluated in the RemoteDataSource classes
     * @throws JSONException
     */
    public CompletableFuture<Result> createNewThesisFuture(Thesis thesis) throws JSONException {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        List<byte[]> byteArrayImages = new ArrayList<>();
        if (thesis.getImages() != null) {
            byteArrayImages = thesis.getImages().stream().map(x -> x.getImage()).collect(Collectors.toList());
        }

        JSONArray possibleDegreeJson = new JSONArray();
        for (Degree degree : thesis.getPossibleDegrees()) {
            JSONObject degreeJsoon = new JSONObject();
            degreeJsoon.put("id", degree.getId());
            degreeJsoon.put("degree", degree.getDegree());
            possibleDegreeJson.put(degreeJsoon);
        }
        List<UUID> degreeUUIDS = (List<UUID>) thesis.getPossibleDegrees().stream().map(x -> x.getId()).collect(Collectors.toList());
        JSONArray degreeUUIDSArr = new JSONArray(degreeUUIDS);
        JSONArray images = new JSONArray();
        String encodedImage= new String();
        for(byte[] byeArr : byteArrayImages){
            encodedImage = java.util.Base64.getEncoder().encodeToString(byeArr);
            images.put(encodedImage);
        }

        JSONObject supervisorJson = new JSONObject();
        supervisorJson.put("id", UserRepository.getInstance().getUser().getId());
        supervisorJson.put("type", "SUPERVISOR");

        JSONObject thesisJSON = new JSONObject()
                .put("name", thesis.getName())
                .put("motivation", thesis.getMotivation())
                .put("task", thesis.getTask())
                .put("questions", thesis.getForm().getQuestions())
                .put("images", images)
                .put("possibleDegrees", possibleDegreeJson)
                .put("supervisor", supervisorJson)
                .put("supervisingProfessor", thesis.getSupervisingProfessor());


        RequestBody body = RequestBody.create(thesisJSON.toString(), JSON);

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result(e.toString(), false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(response.message(), false));
                }
            }

        });
        return resultCompletableFuture;
    }

    /**
     * This function creates the actual HTTP GET request that returns a specific thesis from the backend.
     *
     * @param thesisId
     * @return A Pair of <CompletableFuture<Thesis>,CompletableFuture<Result>
     */
    public Pair<CompletableFuture<Thesis>, CompletableFuture<Result>> getSpecificThesisFuture(UUID thesisId) {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        CompletableFuture<Thesis> resultThesis = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result("failure", false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    ThesisDTO thesis = gson.fromJson(response.body().string(), ThesisDTO.class);
                    resultThesis.complete(parseDTOtoThesis(thesis));
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result("not successful", false));
                }
            }
        });
        return new Pair<>(resultThesis, resultCompletableFuture);
    }

    /**
     * This function creates the actual HTTP DELETE request that deletes a specific thesis.
     *
     * @param thesisId
     * @return CompletableFuture<Result>
     */
    public CompletableFuture<Result> deleteThesisFuture(final UUID thesisId) {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (UserRepository.getInstance().getUser().getMail(),
                                UserRepository.getInstance().getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = apiUtils.getHttpUrlBuilder()
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
                resultCompletableFuture.complete(new Result("error", false));
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

    private Thesis parseDTOtoThesis(ThesisDTO dtoObject){
        Set<Image> images = new HashSet<>(); //todo Is a set prone to errors if a thesis has duplicate images?
        for (String encdoedImage : dtoObject.getImages()){
            images.add(new Image(Base64.getDecoder().decode(encdoedImage)));
        }
        Set<Degree> possibleDegrees = new HashSet<>(dtoObject.getPossibleDegrees());
        return new Thesis(dtoObject.getSupervisingProfessor(), dtoObject.getName()
                , dtoObject.getMotivation(), dtoObject.getTask(), new Form(dtoObject.getQuestions())
                , images, dtoObject.getSupervisor(), possibleDegrees);
    }

}
