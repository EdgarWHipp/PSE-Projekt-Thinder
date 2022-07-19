package com.hfad.thinder.data.source.remote;

import android.app.VoiceInteractor;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.LoginTuple;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.ThesisTuple;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Response;

public class UsersRemoteDataSource {
    // Für die ganze HTTP Funktionalitäten noch neue Klassen hinzufügen!
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final com.hfad.thinder.data.source.remote.okhttp.UsersApiService okHttpService = new com.hfad.thinder.data.source.remote.okhttp.UsersApiService();
    private final Gson gson = new Gson();
    UsersApiService userService;
    OkHttpClient client = new OkHttpClient();
    String url = "http://localhost:8080";
    public UsersRemoteDataSource(){

    }
    /**
     * The purpose of this function is to parse the result of the HTTP request and handle all errors that might occur. All necessary parameters are passed
     * * to the extendUserToStudentResponse class in the UsersApiService.
     * he function returns the result - the success of the HTTP request and an appropriate error message in the case of failure are included in the result.
     *
     * @param degrees
     * @return Result
     */
    public Result extendUserToStudent(Set<Degree> degrees,String firstName,String lastName) {

        try {
            CompletableFuture<Result> result = okHttpService.editStudentProfileFuture(degrees,firstName,lastName);
             return result.get(10000,TimeUnit.SECONDS);


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

    /**
     * The purpose of this function is to parse the result of the HTTP request and handle all errors that might occur. All necessary parameters are passed
     * to the extendUserToSupervisorResponse class in the UsersApiService.
     * The function returns the result - the success of the HTTP request and an appropriate error message in the case of failure are included in the result.
     *
     * @param degree
     * @param location
     * @param institute
     * @param phoneNumber
     * @return Result
     */
    public Result extendUserToSupervisor(String degree, String location, String institute, String phoneNumber,String firstName,String lastName) {
        try {

            CompletableFuture<Result> result = okHttpService.editSupervisorProfileFuture(degree, location, institute, phoneNumber,firstName,lastName);
            return result.get(10000,TimeUnit.SECONDS);
        }catch(JSONException j){
            return new Result("error",false);
        }catch(IOException i){
            return new Result("error",false);
        } catch (ExecutionException e) {
            return new Result("error",false);
        } catch (InterruptedException e) {
            return new Result("error",false);
        } catch (TimeoutException e) {
            return new Result("error",false);
        }

    }

    /**
     * Handles the error messages of the verifyResponse HTTP request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param token
     * @return Result
     */
    public Result isVerify(String token) {

    try {
        CompletableFuture<Result> result = okHttpService.verifyFuture(token);
        return result.get(10000, TimeUnit.SECONDS);
        //only added for troubleshooting
    }catch(JSONException j){
        return new Result("error",false);
    }catch(IOException i){
        return new Result("error",false);
    }catch(ExecutionException e){
        return new Result("error",false);
    }catch(InterruptedException ie){
        return new Result("error",false);
    }catch(TimeoutException t){
        return new Result("error",false);
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

            CompletableFuture<Result> result = okHttpService.usersLoginFuture(login.getPassword(), login.geteMail());

           return result.get(30,TimeUnit.SECONDS);
        } catch (IOException e) {
            return new Result(e.toString(),false);
        } catch (JSONException e) {
            return new Result(e.toString(),false);
        } catch (ExecutionException e){
            return new Result(e.toString(),false);
        }catch(TimeoutException e){
            return new Result(e.toString(),false);
        } catch (InterruptedException e) {
            return new Result(e.toString(),false);
        }

    }

    /**
     * Handles the error messages of the createNewUserResponse HTTP POST request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param user
     * @return Result
     */
    public Result createNewUser(User user) {


        try {
            CompletableFuture<Result> result = okHttpService.createNewUserFuture(user);
            return result.get(10000, TimeUnit.SECONDS);
        }catch (JSONException e){
            return new Result("error",false);
        } catch (ExecutionException e) {
            return new Result("error",false);
        } catch (InterruptedException | TimeoutException e) {
            return new Result("error",false);
        }


    }
    /**
     * Handles the error messages of the deleteUserResponse HTTP DELETE request in the UsersApiService class. Also checks if the response is successful.
     *
     * @return Result
     */
    public Result deleteUser() {
        try {
            CompletableFuture<Result> result = okHttpService.deleteUserFuture();
            return result.get(10000, TimeUnit.SECONDS);
        } catch (IOException e) {
            return new Result("error",false);
        } catch (ExecutionException e) {
            return new Result("error",false);
        } catch (InterruptedException e) {
            return new Result("error",false);
        } catch (TimeoutException e) {
            return new Result("error",false);
        }


    }



    public Result deleteUserThesis(final UUID thesisId) {
        try {
            okhttp3.Response result = okHttpService.deleteUserThesisCall(thesisId);
            if (result.isSuccessful()) {

                return new Result(true);

            }
        } catch (Exception e) {
            System.out.println(e);
            return new Result("Error occurred in the API", false);
        }
        return new Result("Did not receive status code 200", false);
    }


    public ThesisTuple getUserThesis(final UUID thesisId) {
        try {
            okhttp3.Response result = okHttpService.getUserThesisResponse(thesisId);
            if (result.isSuccessful() && result.body() != null) {

                Thesis thesis = gson.fromJson(result.body().toString(), Thesis.class);

                return new ThesisTuple(thesis, new Result(true));

            }
        } catch (Exception e) {
            return new ThesisTuple(null, new Result("Error occurred in the API", false));
        }
        return new ThesisTuple(null, new Result("Did not receive status code 200", false));
    }

}
