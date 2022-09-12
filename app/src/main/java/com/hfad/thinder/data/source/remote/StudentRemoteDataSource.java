package com.hfad.thinder.data.source.remote;

import android.util.Log;

import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisDTO;
import com.hfad.thinder.data.source.remote.okhttp.StudentApiService;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class handles all the errors that occur during HTTP requests that are called exclusively by students.
 */
public class StudentRemoteDataSource {
    private final int TIMEOUT_SECONDS = 10000;
    private final StudentApiService okHttpService = new StudentApiService();

    /**
     * The purpose of this function is to handle all errors that might occur. All necessary parameters are passed
     * * to the extendUserToStudentResponse class in the UsersApiService.
     * The function returns the result - the success of the HTTP request and an appropriate error message in the case of failure are included in the result.
     *
     * @param degrees
     * @return Result
     */
    public Result extendUserToStudent(ArrayList<Degree> degrees, String firstName, String lastName) {

        try {
            CompletableFuture<Result> result = okHttpService.editStudentProfileFuture(degrees, firstName, lastName);
            Result resultValue = result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return resultValue;


        } catch (InterruptedException | ExecutionException | JSONException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }


    /**
     * Handles the exceptions for the rateThesisFuture call in the StudentApiService.
     *
     * @param ratings
     * @return
     */
    public Result rateThesis(final Collection<Pair<UUID, Boolean>> ratings) {
        try {
            CompletableFuture<Result> result =
                    okHttpService.rateThesisFuture(ratings);
            Result resultValue = result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return resultValue;


        } catch (JSONException | InterruptedException | ExecutionException j) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }

    /**
     * This function handles all the exceptions from the getAllPositiveRatedThesesFuture call out of the ThesisApiService
     *
     * @return Result
     */
    public Result getAllLikedThesesFromAStudent() {
        try {
            Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> result = okHttpService.getAllPositiveRatedThesesFuture();
            Result resultValue = result.getSecond().get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            HashMap<UUID,Thesis> map= result.getFirst().get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            if (resultValue.getSuccess()) {
                ThesisRepository.getInstance().setThesisMap(map);
                return resultValue;
            } else {
                return new Result(R.string.unsuccessful_response, false);
            }

        } catch (ExecutionException | InterruptedException  e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }

    /**
     * This function handles all the exceptions that the getAllThesesForTheStudentFuture function throws.
     *
     * @return ArrayList<Thesis>
     */
    public Result getAllThesisForAStudent() {
        try {
            Pair<CompletableFuture<ArrayList<Thesis>>, CompletableFuture<Result>> result = okHttpService.getAllThesesForTheStudentFuture();
            Result resultValue = result.getSecond().get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            ArrayList<Thesis> list;
            if(!resultValue.getSuccess()){
                 list=new ArrayList<>();
            }else{
                list = result.getFirst().get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            }

            if (resultValue.getSuccess()) {
                ThesisRepository.getInstance().setTheses(list);
                return resultValue;
            } else {
                return new Result(R.string.unsuccessful_response, false);
            }

        } catch (ExecutionException | InterruptedException e) {
            return new Result(R.string.exception_during_HTTP_call, false);

        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }

    /**
     * This function handles all the exceptions that the sendThesisFormToSupervisorFuture in the StudentApiService class throws.
     *
     * @param form
     * @param thesisId
     * @return Result
     */
    public Result sendTheFormToTheSupervisor(final Form form, final UUID thesisId) {
        try {
            CompletableFuture<Result> result =
                    okHttpService.sendThesisFormToSupervisorFuture(form, thesisId);
            Result resultValue = result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return resultValue;

        } catch (JSONException | ExecutionException | InterruptedException j) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }

    /**
     * This function handles all the exceptions that the removeALikedThesisFromAStudentFuture from the ThesisApiService function throws.
     *
     * @param thesisId
     * @return Result that includes a success value and an error message
     */
    public Result removeLikedThesisFromStudent(UUID thesisId) {
        try {
            CompletableFuture<Result> result = okHttpService.removeALikedThesisFromAStudentFuture(thesisId);
            Result resultValue =result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return resultValue;
        } catch (ExecutionException | InterruptedException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }

    }

}
