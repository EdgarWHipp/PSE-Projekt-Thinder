package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.okhttp.UsersApiService;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This Class handles all errors that the HTTP requests on the /users/ endpoint creates in addition to all necessary functions needed to verify a user/ a change in password.
 */
public class UsersRemoteDataSource {
    private final int TIMEOUT_SECONDS = 1;
    private final UsersApiService usersApiService = new UsersApiService();

    public UsersApiService getUsersApiService() {
        return usersApiService;
    }

    /**
     * Handles the error messages of the verifyResponse HTTP request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param token
     * @return Result that includes a success value and an error message
     */
    public Result verify(String token) {

        try {
            CompletableFuture<Result> result = usersApiService.verifyUser(token);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (JSONException | IOException | TimeoutException | InterruptedException | ExecutionException j) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }


    }


    /**
     * Handles the thrown exceptions of the usersLoginResponse HTTP GET request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param login
     * @return Result that includes a success value and an error message
     */
    public Result login(Login login) {
        try {
            CompletableFuture<Result> resultFuture = usersApiService.getUserDetails(login);
            return resultFuture.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (IOException | JSONException | ExecutionException
                | TimeoutException | InterruptedException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }
    }

    /**
     * Handles the error messages of the createNewUserResponse HTTP POST request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param user
     * @return Result that includes a success value and an error message
     */
    public Result createNewUser(UserCreation user) {
        try {
            CompletableFuture<Result> result = usersApiService.createNewUserFuture(user);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (JSONException | InterruptedException | TimeoutException | ExecutionException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }
    }

    /**
     * Handles the error messages of the deleteUserResponse HTTP DELETE request in the UsersApiService class. Also checks if the response is successful.
     *
     * @return Result that includes a success value and an error message
     */
    public Result deleteUser() {
        try {
            CompletableFuture<Result> result = usersApiService.deleteUserFuture();
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (IOException | ExecutionException | InterruptedException | TimeoutException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }
    }

    /**
     * Handles the error messages for the resetPasswordFuture HTTP request.
     *
     * @param email
     * @return Result that includes a success value and an error message
     */
    public Result resetPassword(String email) {
        try {
            CompletableFuture<Result> result = usersApiService.resetPassword(email);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }
    }

    /**
     * Handles the error messages for the sendNewPasswordFuture HTTP request.
     *
     * @param token, newPassword
     * @return Result that includes a success value and an error message
     */
    public Result sendNewPassword(String token, String newPassword) {
        try {
            CompletableFuture<Result> result = usersApiService.postNewPassword(token, newPassword);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (JSONException | ExecutionException | InterruptedException | TimeoutException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }
    }


}
