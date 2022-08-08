package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.okhttp.ThesesApiService;
import com.hfad.thinder.data.source.remote.okhttp.UsersApiService;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class handles all the errors that occur through HTTP requests on the /thesis/ endpoint.
 */
public class ThesisRemoteDataSource {
    private final ThesesApiService okHttpService = new ThesesApiService();

    /**
     * Handles the errors that occur during the HTTP POST that creates a new thesis. Also fetches the result of the future.
     * @param thesis
     * @return Result that includes a success value and an error message
     */
    public Result createNewThesis(final Thesis thesis) {

        try {
            CompletableFuture<Result> result = okHttpService.createNewThesisFuture(thesis);
            return result.get(100, TimeUnit.SECONDS);
        } catch (JSONException e) {
            return new Result("not successful", false);
        } catch (ExecutionException e) {
            return new Result("not successful", false);
        } catch (InterruptedException e) {
            return new Result("not successful", false);
        } catch (TimeoutException e) {
            return new Result("not successful", false);
        }


    }

    /**
     * Handles the errors that occur during the HTTP GET request that gets a thesis with a specific id from the backend.
     * @param id
     * @return A Pair of both the fetched thesis object and a Result object
     */
    public Pair<Thesis,Result> getNewThesis(final UUID id){
            try {
                Pair<CompletableFuture<Thesis>,CompletableFuture<Result>> result = okHttpService.getSpecificThesisFuture(id);
                return new Pair<>(result.getFirst().get(10000, TimeUnit.SECONDS),result.getSecond().get(10000, TimeUnit.SECONDS));

            } catch (ExecutionException e) {
                return new Pair<>(null,new Result("not successful", false));
            } catch (InterruptedException e) {
                return new Pair<>(null,new Result("not successful", false));
            } catch (TimeoutException e) {
                return new Pair<>(null,new Result("not successful", false));
            }
    }



    /**
     * Handles the errors that occur during the HTTP DELETE request that deletes the thesis (specified through the id)
     * @param thesisId
     * @return Result that includes a success value and an error message
     */
    public Result deleteThesis(final UUID thesisId){
        try{
            CompletableFuture<Result> result = okHttpService.deleteThesisFuture(thesisId);
            return result.get(100, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            return new Result("not successful", false);
        } catch (InterruptedException e) {
            return new Result("not successful", false);
        } catch (TimeoutException e) {
            return new Result("not successful", false);
        }
    }



    /**
     * This function handles all the exceptions that the removeALikedThesisFromAStudentFuture from the ThesisApiService function throws.
     * @param thesisId
     * @return Result that includes a success value and an error message
     */
    public Result removeLikedThesisFromStudent(UUID thesisId){
        try{
            CompletableFuture<Result> result = okHttpService.removeALikedThesisFromAStudentFuture(thesisId);
            return result.get(100, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            return new Result("not successful", false);
        } catch (InterruptedException e) {
            return new Result("not successful", false);
        } catch (TimeoutException e) {
            return new Result("not successful", false);
        }

    }


}
