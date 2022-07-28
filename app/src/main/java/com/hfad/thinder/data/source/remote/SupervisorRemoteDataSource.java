package com.hfad.thinder.data.source.remote;

import com.google.gson.Gson;
import com.hfad.thinder.data.source.remote.okhttp.SupervisorApiService;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

import java.io.IOException;
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
   * @return Result
   */
  public Result extendUserToSupervisor(UUID id, String degree, String officeNumber, String building, String institute, String phoneNumber, String firstName, String lastName) {
    try {

      CompletableFuture<Result> result = supervisorApiService.editSupervisorProfileFuture(id,degree, officeNumber,building, institute, phoneNumber, firstName, lastName);
      return result.get(10000, TimeUnit.SECONDS);
    } catch (JSONException j) {
      return new Result("error", false);
    } catch (IOException i) {
      return new Result("error", false);
    } catch (ExecutionException e) {
      return new Result("error", false);
    } catch (InterruptedException e) {
      return new Result("error", false);
    } catch (TimeoutException e) {
      return new Result("error", false);
    }

  }
}
