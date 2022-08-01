package com.hfad.thinder.data.source.remote;

import android.util.Log;

import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.okhttp.UsersApiService;
import com.hfad.thinder.data.source.repository.UserRepository;
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
    private final UsersApiService usersApiService = new UsersApiService();
    private final static int TIMEOUT_SECONDS = 10000;

    /**
     * Handles the error messages of the verifyResponse HTTP request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param token
     * @return Result
     */
    public Result verify(String token) {

        try {
            CompletableFuture<Result> result = usersApiService.verifyUser(token);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            //only added for troubleshooting
        } catch (JSONException j) {
            return new Result("error", false);
        } catch (IOException i) {
            return new Result("error", false);
        } catch (ExecutionException e) {
            return new Result("error", false);
        } catch (InterruptedException ie) {
            return new Result("error", false);
        } catch (TimeoutException t) {
            return new Result("error", false);
        }


    }

    public Result getUserRole(){
        try{
            Log.e("",UserRepository.getInstance().getUser().getMail() + " " + UserRepository.getInstance().getUser().getPassword());
        CompletableFuture<Result> resultCompletableFuture =
                usersApiService.getUserRole(new Login(UserRepository.getInstance().getUser().getMail(),UserRepository.getInstance().getUser().getPassword()));
        return resultCompletableFuture.get(TIMEOUT_SECONDS,TimeUnit.SECONDS);

        } catch (ExecutionException e) {
            return new Result("error", false);
        } catch (InterruptedException ie) {
            return new Result("error", false);
        } catch (TimeoutException t) {
            return new Result("error", false);
        }

    }

    /**
     * Handles the error messages of the usersLoginResponse HTTP GET request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param login
     * @return Result
     */
    public Result login(Login login) {
        try {
            CompletableFuture<Result> resultRole = usersApiService.getUserRole(login);
            resultRole.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            CompletableFuture<Result> resultFuture = usersApiService.getUserDetails(login);
            return resultFuture.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (IOException | JSONException | ExecutionException // TODO error messages
                | TimeoutException | InterruptedException e) {
            return new Result(e.toString(), false);
        }
    }

    /**
     * Handles the error messages of the createNewUserResponse HTTP POST request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param user
     * @return Result
     */
    public Result createNewUser(UserCreation user) {
        try {
            CompletableFuture<Result> result = usersApiService.postNewUser(user);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (JSONException e) {
            return new Result("json", false);
        } catch (ExecutionException e) {
            return new Result("exec", false);
        } catch (InterruptedException | TimeoutException e) {
            return new Result("inter", false);
        }
    }

    /**
     * Handles the error messages of the deleteUserResponse HTTP DELETE request in the UsersApiService class. Also checks if the response is successful.
     *
     * @return Result
     */
    public Result deleteUser() {
        try {
            CompletableFuture<Result> result = usersApiService.deleteUser();
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (IOException e) {
            return new Result("error", false);
        } catch (ExecutionException e) {
            return new Result("error", false);
        } catch (InterruptedException e) {
            return new Result("error", false);
        } catch (TimeoutException e) {
            return new Result("error", false);
        }
    }

    /**
     * Handles the error messages for the resetPasswordFuture HTTP request.
     * @param email
     * @return Result
     */
    public Result resetPassword(String email){
        try{
            CompletableFuture<Result> result = usersApiService.resetPassword(email);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        }
        catch (ExecutionException e) {
            return new Result("error", false);
        } catch (InterruptedException e) {
            return new Result("error", false);
        } catch (TimeoutException e) {
            return new Result("error", false);
        }
    }

    /**
     * Handles the error messages for the sendNewPasswordFuture HTTP request.
     * @param token, newPassword
     * @return Result
     */
    public Result sendNewPassword(String token, String newPassword) {
        try {
            CompletableFuture<Result> result = usersApiService.postNewPassword(token, newPassword);
            return result.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (JSONException e) {
            return new Result("error", false);
        } catch (ExecutionException e) {
            return new Result("error", false);
        } catch (InterruptedException e) {
            return new Result("error", false);
        } catch (TimeoutException e) {
            return new Result("error", false);
        }
    }

    public UsersApiService getUsersApiService() {
        return usersApiService;
    }
}
