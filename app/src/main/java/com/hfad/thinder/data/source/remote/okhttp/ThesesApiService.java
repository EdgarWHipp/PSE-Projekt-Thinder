package com.hfad.thinder.data.source.remote.okhttp;

import static android.content.ContentValues.TAG;

import static com.hfad.thinder.data.util.ParseUtils.parseDTOtoThesis;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisDTO;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.util.ParseUtils;

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
    private UserRepository userRepository = UserRepository.getInstance();

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
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        JSONObject thesisJSON = ParseUtils.thesisToThesisDtoJson(thesis);
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
     * This function creates the actual HTTP GET request that returns a specific thesis from the backend.
     *
     * @param thesisId
     * @return A Pair of <CompletableFuture<Thesis>,CompletableFuture<Result>
     */
    public Pair<CompletableFuture<Thesis>, CompletableFuture<Result>> getSpecificThesisFuture(UUID thesisId) {
        //Add HTTP BASIC authentication
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
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
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    ThesisDTO thesis = gson.fromJson(response.body().string(), ThesisDTO.class);
                    resultThesis.complete(parseDTOtoThesis(thesis));
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
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
                        (userRepository.getUser().getMail(),
                                userRepository.getPassword()))
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
