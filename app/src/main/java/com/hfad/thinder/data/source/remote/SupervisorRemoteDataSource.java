package com.hfad.thinder.data.source.remote;

import com.google.gson.Gson;
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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * This class handles all the errors that occur during HTTP requests that are call on supervisors.
 */
public class SupervisorRemoteDataSource {
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
  public Result extendUserToSupervisor( String degree, String officeNumber, String building, String institute, String phoneNumber, String firstName, String lastName) {
    try {

      CompletableFuture<Result> result = supervisorApiService.editSupervisorProfileFuture(degree, officeNumber,building, institute, phoneNumber, firstName, lastName);
      return result.get(10000, TimeUnit.SECONDS);
    } catch (JSONException j) {
      return new Result("not successful", false);
    } catch (IOException i) {
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
   * Handles the errors that occur during the HTTP PUT request that changes a thesis, that is specified through the id, to the given newer thesis.
   * @param thesisId
   * @param thesis
   * @return Result that includes a success value and an error message
   */
  public Result editThesis (final UUID thesisId,final Thesis thesis) {
    try{
      CompletableFuture<Result> result = supervisorApiService.editThesisFuture(thesisId, thesis);
      return result.get(100, TimeUnit.SECONDS);
    } catch (ExecutionException e) {
      return new Result("not successful", false);
    } catch (InterruptedException e) {
      return new Result("not successful", false);
    } catch (TimeoutException e) {
      return new Result("not successful", false);
    } catch (JSONException e) {
      return new Result("not successful", false);
    }

  }

  /**
   * Handles the exceptions for the getCreatedThesisFromSupervisorFuture call in der SupervisorApiService Klasse.
   * @return Result that includes a success value and an error message
   */
  public Result getCreatedThesisFromSupervisor(){
    try {
      Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> result = supervisorApiService.getCreatedThesisFromSupervisorFuture();
      if (result.getSecond().get().getSuccess()) {
        ThesisRepository.getInstance().setThesisMap(result.getFirst().get());
        return result.getSecond().get();
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
