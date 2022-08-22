package com.hfad.thinder.data.source.remote;

import android.util.Log;

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
 * This class handles all the errors that occur during HTTP requests that are call on students.
 */
public class StudentRemoteDataSource {
    private final StudentApiService okHttpService = new StudentApiService();

    /**
     * The purpose of this function is to parse the result of the HTTP request and handle all errors that might occur. All necessary parameters are passed
     * * to the extendUserToStudentResponse class in the UsersApiService.
     * he function returns the result - the success of the HTTP request and an appropriate error message in the case of failure are included in the result.
     *
     * @param degrees
     * @return Result
     */
    public Result extendUserToStudent(ArrayList<Degree> degrees, String firstName, String lastName) {

        try {
            CompletableFuture<Result> result = okHttpService.editStudentProfileFuture(degrees, firstName, lastName);
            return result.get(10000, TimeUnit.SECONDS);


        } catch (IOException e) {
            return new Result("not successful", false);
        } catch (JSONException j) {
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
     * Handles the exceptions for the rateThesisFuture call in the StudentApiService.
     *
     * @param ratings
     * @return
     */
    public Result rateThesis(final Collection<Pair<UUID, Boolean>> ratings) {
        try {
            CompletableFuture<Result> result =
                    okHttpService.rateThesisFuture(ratings);
            return result.get(10000, TimeUnit.SECONDS);


        } catch (JSONException j) {
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
     * This function handles all the exceptions from the getAllPositiveRatedThesesFuture call out of the ThesisApiService
     *
     * @return Result
     */
    public Result getAllLikedThesesFromAStudent() {
        try {
            Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> result = okHttpService.getAllPositiveRatedThesesFuture();
            Result resultValue = result.getSecond().get();
            HashMap<UUID,Thesis> map= result.getFirst().get();
            if (resultValue.getSuccess()) {
                Log.e("","thesismap is set");
                ThesisRepository.getInstance().setThesisMap(map);
                Log.e("",String.valueOf(resultValue.getSuccess()));
                return resultValue;
            } else {
                return new Result("not successful", false);
            }

        } catch (ExecutionException e) {
            return new Result("not successful", false);
        } catch (InterruptedException e) {
            return new Result("not successful", false);
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
            Result resultValue = result.getSecond().get();
            ArrayList<Thesis> list = result.getFirst().get();
            if (resultValue.getSuccess()) {
                ThesisRepository.getInstance().setTheses(list);
                return resultValue;
            } else {
                return new Result("not successful", false);
            }

        } catch (ExecutionException e) {
            return null;
        } catch (InterruptedException e) {
            return null;
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
            return result.get(10000, TimeUnit.SECONDS);

        } catch (JSONException j) {
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
     * This function handles all the exceptions that the removeALikedThesisFromAStudentFuture from the ThesisApiService function throws.
     *
     * @param thesisId
     * @return Result that includes a success value and an error message
     */
    public Result removeLikedThesisFromStudent(UUID thesisId) {
        try {
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
