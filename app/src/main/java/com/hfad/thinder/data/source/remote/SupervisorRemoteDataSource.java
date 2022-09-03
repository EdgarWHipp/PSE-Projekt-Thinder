package com.hfad.thinder.data.source.remote;


import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.okhttp.SupervisorApiService;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class handles all the errors that occur during HTTP requests that are call on supervisors.
 */
public class SupervisorRemoteDataSource {
    private final int TIMEOUT_SECONDS = 10000;
    private final SupervisorApiService supervisorApiService = new SupervisorApiService();


    /**
     * The purpose of this function is to parse the result of the HTTP request and handle all errors that might occur. All necessary parameters are passed
     * to the extendUserToSupervisorResponse class in the UsersApiService.
     * The function returns the result - the success of the HTTP request and an appropriate error message in the case of failure are included in the result.
     *
     * @param degree
     * @param institute
     * @param phoneNumber
     * @return Result that includes a success value and an error message
     */
    public Result extendUserToSupervisor(String degree, String officeNumber, String building, String institute, String phoneNumber, String firstName, String lastName) {
        try {

            CompletableFuture<Result> result = supervisorApiService.editSupervisorProfileFuture(degree, officeNumber, building, institute, phoneNumber, firstName, lastName);
            Result resultValue = result.get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            return resultValue;
        } catch (JSONException | IOException | ExecutionException | InterruptedException j) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }

    /**
     * Handles the errors that occur during the HTTP PUT request that changes a thesis, that is specified through the id, to the given newer thesis.
     *
     * @param thesisId
     * @param thesis
     * @return Result that includes a success value and an error message
     */
    public Result editThesis(final UUID thesisId, final Thesis thesis) {
        try {
            CompletableFuture<Result> result = supervisorApiService.editThesisFuture(thesisId, thesis);
            Result resultValue = result.get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            return resultValue;
        } catch (ExecutionException | InterruptedException | JSONException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }

    }

    /**
     * Handles the exceptions for the getCreatedThesisFromSupervisorFuture call in der SupervisorApiService Klasse.
     *
     * @return Result that includes a success value and an error message
     */
    public Result getCreatedThesisFromSupervisor() {
        try {
            Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> result = supervisorApiService.getCreatedThesisFromSupervisorFuture();
            Result resultValue = result.getSecond().get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            HashMap<UUID,Thesis> hashMap = result.getFirst().get(TIMEOUT_SECONDS,TimeUnit.SECONDS);
            if (resultValue.getSuccess()) {
                ThesisRepository.getInstance().setThesisMap(hashMap);
                return result.getSecond().get();
            } else {
                return new Result(R.string.unsuccessful_response, false);
            }

        } catch (ExecutionException | InterruptedException  e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }catch(TimeoutException e){
            return new Result(R.string.timeout_exception,false);
        }
    }
}
