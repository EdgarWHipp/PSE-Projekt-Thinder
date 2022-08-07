package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.okhttp.ThesesApiService;
import com.hfad.thinder.data.source.remote.okhttp.UsersApiService;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;

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
     * @return A tuple of both the fetched thesis object and a Result object
     */
    public Tuple<Thesis,Result> getNewThesis(final UUID id){
            try {
                Tuple<CompletableFuture<Thesis>,CompletableFuture<Result>> result = okHttpService.getSpecificThesisFuture(id);
                return new Tuple<>(result.x.get(10000, TimeUnit.SECONDS),result.y.get(10000, TimeUnit.SECONDS));

            } catch (ExecutionException e) {
                return new Tuple<>(null,new Result("error", false));
            } catch (InterruptedException e) {
                return new Tuple<>(null,new Result("error", false));
            } catch (TimeoutException e) {
                return new Tuple<>(null,new Result("error", false));
            }
    }



    /**
     * Handles the errors that occur during the HTTP DELETE request that deletes the thesis (specified through the id)
     * @param thesisId
     * @return
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
     * This function handles all the exceptions that the getAllThesesForTheStudentFuture function throws.
     * @return ArrayList<Thesis>
     */
    public Result getAllThesisForAStudent(){
        try{
            Tuple<CompletableFuture<ArrayList<Thesis>>,CompletableFuture<Result>> result = okHttpService.getAllThesesForTheStudentFuture();
            if(result.y.get().getSuccess()){
                ThesisRepository.getInstance().setTheses(result.x.get());
                return result.y.get();
            }else {
                return new Result("not successful",false);
            }

        } catch (ExecutionException e) {
            return null;
        } catch (InterruptedException e) {
            return null;
        }
    }

    /**
     * This function handles all the exceptions that the removeALikedThesisFromAStudentFuture from the ThesisApiService function throws.
     * @param thesisId
     * @return Result
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

    /**
     * This function handles all the exceptions from the getAllPositiveRatedThesesFuture call out of the ThesisApiService
     * @return Result
     */
    public Result getAllLikedThesesFromAStudent() {
        try {
            Tuple<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> result = okHttpService.getAllPositiveRatedThesesFuture();
            if (result.y.get().getSuccess()) {
                ThesisRepository.getInstance().setThesisMap(result.x.get());
                return result.y.get();
            } else {
                return new Result("not successful", false);
            }

        } catch (ExecutionException e) {
            return new Result("not successful", false);
        } catch (InterruptedException e) {
            return new Result("not successful", false);
        }
    }
}
