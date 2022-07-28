package com.hfad.thinder.data.source.remote;

import com.google.gson.Gson;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.remote.okhttp.StudentApiService;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

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
  public Result extendUserToStudent(Set<Degree> degrees, String firstName, String lastName) {

    try {
      CompletableFuture<Result> result = okHttpService.editStudentProfileFuture(degrees, firstName, lastName);
      return result.get(10000, TimeUnit.SECONDS);


    } catch (IOException e) {
      return new Result("error", false);
    } catch (JSONException j) {
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
