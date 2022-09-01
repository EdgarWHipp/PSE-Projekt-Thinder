package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This class creates all the HTTP requests for the "general" user. This includes functionalities such as the registration, login, forgetting the password etc.
 */
public class UsersApiService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final ApiUtils API_UTILS = ApiUtils.getInstance();

    /**
     * This function creates the HTTP GET request that firstly makes sure the email, password tuple exists in the database and then fetches a JSON with attributes type and id.
     * Checks if the asynchronous call return fails or responds.
     *
     * @param login
     * @throws JSONException
     * @throws IOException
     * @returnCompletableFuture<Result>
     */
    public CompletableFuture<Result> getUserDetails(Login login) throws JSONException, IOException {
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(login.getMail(), login.getPassword()))
                .build();

        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        HttpUrl url = API_UTILS.getHttpUrlBuilder()
                .addPathSegment("users").addPathSegment("current").build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = clientAuth.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        UserRepository.getInstance().setPassword(login.getPassword());
                        Result valueSettingResult = setUserValues(response.body().string());
                        if (!valueSettingResult.getSuccess()) {
                            resultCompletableFuture.complete(valueSettingResult);
                        }
                        resultCompletableFuture.complete(new Result(true));
                    } catch (JSONException e) {
                        resultCompletableFuture.complete(new Result(R.string.exception_during_HTTP_call, false));
                    }

                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }
            }
        });

        return resultCompletableFuture;
    }


    /**
     * This function creates the HTTP POST request, tracks if the call was a failure or had a response and
     * if it was successful and returns an appropriate completable future that holds a Result.
     * Checks if the asynchronous call return fails or responds.
     *
     * @param token
     * @return CompletableFuture
     * @throws JSONException
     * @throws IOException
     */
    public CompletableFuture<Result> verifyUser(String token) throws JSONException, IOException, ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = API_UTILS.getHttpUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("verify")
                .addQueryParameter("token", token)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {

                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));

                }
            }
        });
        return resultCompletableFuture;
    }

    /**
     * This function creates the HTTP POST request and thus, if no error occurs, leads to the creation of a new user in the postgres database.
     * Also already defines the id and type of the registrated user for the UserRepository.
     * Checks if the asynchronous call return fails or responds.
     *
     * @param userCreation
     * @return CompletableFuture<Result>
     * @throws JSONException
     */
    public CompletableFuture<Result> createNewUserFuture(UserCreation userCreation) throws JSONException {

        JSONObject userJson = new JSONObject()
                .put("firstName", userCreation.getFirstName())
                .put("lastName", userCreation.getLastName())
                .put("password", userCreation.getPassword())
                .put("mail", userCreation.getMail())
                .put("type", "USER");
        UserRepository.getInstance()
                .setUser(new User(null, null, false, null,
                        userCreation.getMail(), userCreation.getFirstName(),
                        userCreation.getLastName(), false));
        UserRepository.getInstance().setPassword(userCreation.getPassword());
        RequestBody body = RequestBody.create(userJson.toString(), JSON);

        HttpUrl url = API_UTILS.getHttpUrlBuilder()
                .addPathSegment("users")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        Call call = CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {

                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }

            }

        });
        return resultCompletableFuture;
    }

    /**
     * This function creates the HTTP DELETE request that removes a user from the database (if successful)
     * Checks if the asynchronous call return fails or responds.
     *
     * @return CompletableFuture<Result> that is alter evaluated inside the UsersRemoteDataSource class
     * @throws IOException
     */
    public CompletableFuture<Result> deleteUserFuture() throws IOException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(
                        UserRepository.getInstance().getUser().getMail(),
                        UserRepository.getInstance().getPassword()))
                .build();

        HttpUrl url = API_UTILS.getHttpUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("current").build();

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        Call call = clientAuth.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {


                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }

            }

        });
        return resultCompletableFuture;

    }

    /**
     * This function creates the HTTP GET request that makes the backend send
     * a email to the users mail address, which they can then enter in the verify screen
     * to ultimately reset their password.
     *
     * @return CompletableFuture<Result> that is later evaluated in the UsersRemoteDataSource class
     */
    public CompletableFuture<Result> resetPassword(String email) {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = API_UTILS.getHttpUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("resetPassword")
                .addQueryParameter("mail", email)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.unsuccessful_response, false));
                }

            }
        });
        return resultCompletableFuture;
    }

    /**
     * This function creates the actual HTTP POST request that signals a new password for a currently logged in user to the backend.
     * The user has a new password after this call.
     *
     * @param token
     * @param newPassword
     * @return CompletableFuture<Result>
     * @throws JSONException
     */
    public CompletableFuture<Result> postNewPassword(String token, String newPassword) throws JSONException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        JSONObject passwordResetDTOJSON = new JSONObject();
        passwordResetDTOJSON.put("newPassword", newPassword);
        passwordResetDTOJSON.put("token", token);

        RequestBody body = RequestBody.create(passwordResetDTOJSON.toString(), JSON);

        HttpUrl url;
        url = API_UTILS.getHttpUrlBuilder()
                .addPathSegment("users")
                .addPathSegment("resetPassword")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result(R.string.failure_on_call, false));
                }
            }
        });
        return resultCompletableFuture;
    }

    /**
     * This function parses the returned JSON from the login HTTP GET request. All necessary user values are set inside the UserRepository.
     *
     * @param body the response.body().string() that is returned from the getUserDetails function.
     * @return Result
     * @throws JSONException, when the JSON is incorrectly parsed
     */
    private Result setUserValues(String body) throws JSONException { // TODO refactor whole method
        Gson gson = new Gson();
        UserRepository userRepository = UserRepository.getInstance();
        String type = new JSONObject(body).get("type").toString();
        Log.e("",type.toString());
        switch (type) {
            case "STUDENT":
                Student student = gson.fromJson(body, Student.class);
                userRepository.setType(USERTYPE.STUDENT);
                userRepository.setUser(student);
                break;
            case "SUPERVISOR":
                Supervisor supervisor = gson.fromJson(body, Supervisor.class);
                userRepository.setType(USERTYPE.SUPERVISOR);
                userRepository.setUser(supervisor);
                break;
            default:
                return new Result(R.string.exception_during_HTTP_call, false);
        }
        return new Result(true);
    }
}


