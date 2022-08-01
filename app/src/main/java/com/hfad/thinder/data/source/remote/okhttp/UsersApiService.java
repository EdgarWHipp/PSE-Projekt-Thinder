package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UsersApiService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private String scheme = "http";
    private String host = "10.0.2.2";
    private int port = 8080;

    /**
     * This function creates a HTTP GET request with http basic authentication(email & password)
     * to set the USERTYPE of the user loggin in for the UserRepository.getInstance().getType() function.
     * Checks if the asynchronous call return fails or responds.
     *
     * @param login
     * @return CompletableFuture<Result>
     */
    public CompletableFuture<Result> getUserRole(Login login){
        Log.e("", login.getMail()+login.getPassword());
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(login.getMail(), login.getPassword()))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("users")
                .addPathSegment("current")
                .addPathSegment("getRole")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = clientAuth.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(e.toString(),false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        String returnValue = response.body().string();
                        String userType =  returnValue.substring(1, returnValue.length() - 1);

                        switch(userType) {
                            case "SUPERVISOR":
                                UserRepository.getInstance().setType(USERTYPE.SUPERVISOR);
                                Log.e("","WORKED");
                                resultCompletableFuture.complete(new Result(true));
                                Log.e("","resutl is true)");
                                break;
                            case "STUDENT":
                                UserRepository.getInstance().setType(USERTYPE.STUDENT);
                                resultCompletableFuture.complete(new Result(true));
                                break;
                            default:
                                resultCompletableFuture.complete(new Result("type not correctly specified",false));
                                break;
                        }
                    }else {
                        resultCompletableFuture.complete(new Result("not successful",false));
                    }
            }
        });
        return resultCompletableFuture;
    }
    /**
     * This function creates the HTTP GET request that firstly makes sure the email, password tuple exists in the database and then fetches a JSON with attributes type and id.
     * Checks if the asynchronous call return fails or responds.
     * @param login
     * @returnCompletableFuture<Result>
     * @throws JSONException
     * @throws IOException
     */
    public CompletableFuture<Result> getUserDetails(Login login) throws JSONException, IOException {
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(login.getMail(), login.getPassword()))
                .build();

        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("users")
                .addPathSegment("current")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = clientAuth.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(e.toString(),false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        Result valueSettingResult = setUserValues(response.body().string());
                        resultCompletableFuture.complete(new Result(valueSettingResult.getErrorMessage(),true));
                    } catch (JSONException e) {
                        resultCompletableFuture.complete(new Result("user values not correctly received",false));
                    }

                }else{
                    resultCompletableFuture.complete(new Result("not successful",false));
                }
            }
        });

        return resultCompletableFuture;
    }




    /**
     * This function creates the HTTP POST request, tracks if the call was a failure or had a response and
     * if it was successful and returns an appropriate completable future that holds a Result.
     * Checks if the asynchronous call return fails or responds.
     * @param token
     * @return CompletableFuture
     * @throws JSONException
     * @throws IOException
     */
    public CompletableFuture<Result> verifyUser(String token) throws JSONException, IOException, ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        CompletableFuture<Result> getUserRoleResult= getUserRole(new Login(UserRepository.getInstance().getUser().getMail(),UserRepository.getInstance().getUser().getPassword()));


        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("users")
                .addPathSegment("verify")
                .addQueryParameter("token",token)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(e.toString(),false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){

                    resultCompletableFuture.complete(new Result(true));
                }else{
                    resultCompletableFuture.complete(new Result("not successful",false));
                }
            }
        });
        return getUserRoleResult;
    }

    /**
     * This function creates the HTTP POST request and thus, if no error occurs, leads to the creation of a new user in the postgres database.
     * Also already defines the id and type of the registrated user for the UserRepository.
     * Checks if the asynchronous call return fails or responds.
     * @param user
     * @return  CompletableFuture<Result>
     * @throws JSONException
     */
    public CompletableFuture<Result> postNewUser(UserCreation user) throws JSONException {

        JSONObject userJson = new JSONObject()
                .put("firstName", user.getFirstName())
                .put("lastName", user.getLastName())
                .put("password", user.getPassword())
                .put("mail", user.getMail())
                .put("role","USER");
        UserRepository.getInstance()
                .setUser(new User(null,null,false,null,
                        user.getPassword(),user.getMail(),user.getFirstName(),user.getLastName()));


        RequestBody body = RequestBody.create(userJson.toString(), JSON);

        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
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

                resultCompletableFuture.complete(new Result(e.toString(), false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                        //setUserRole(user.getPassword(),user.getEmail());
                        resultCompletableFuture.complete(new Result(true));
                } else {

                    resultCompletableFuture.complete(new Result("not successful", false));
                }

            }

        });
        return resultCompletableFuture;
    }

    /**
     * This function creates the HTTP DELETE request that removes a user from the database (if successful)
     * Checks if the asynchronous call return fails or responds.
     * @return CompletableFuture<Result> that is alter evaluated inside the UsersRemoteDataSource class
     * @throws IOException
     */
    public CompletableFuture<Result> deleteUser() throws IOException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<Result>();

        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("users")
                .addPathSegment(UserRepository.getInstance().getCurrentUUID().toString()).build();
        Request request= new Request.Builder()
                .url(url)
                .delete()
                .build();
        Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(e.toString(),false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {


                    resultCompletableFuture.complete(new Result(true));
                } else {
                    resultCompletableFuture.complete(new Result("not successful", false));
                }

            }

        });
        return resultCompletableFuture;

    }

    /**
     * This function creates the HTTP GET request that makes the backend send
     * a email to the users mail address, which they can then enter in the verify screen
     * to ultimately reset their password.
     * @return CompletableFuture<Result> that is later evaluated in the UsersRemoteDataSource class
     */
    public CompletableFuture<Result> resetPassword(String email){
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();


        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("resetPassword")
                .addQueryParameter("mail",email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(e.toString(),false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    resultCompletableFuture.complete(new Result(true));
                }else{
                    resultCompletableFuture.complete(new Result("not successful",false));
                }

            }
        });
        return resultCompletableFuture;
    }

    /**
     *
     * @param token
     * @param newPassword
     * @return CompletableFuture<Result>
     * @throws JSONException
     */
    public CompletableFuture<Result> postNewPassword(String token, String newPassword) throws JSONException {
        CompletableFuture<Result> resultCompletableFuture= new CompletableFuture<>();
        JSONObject passwordJSON = new JSONObject().put("password",newPassword);

        RequestBody body = RequestBody.create(passwordJSON.toString(), JSON);
        HttpUrl url = new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegment("resetPassword")
                .addQueryParameter("token",token)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = CLIENT.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                resultCompletableFuture.complete(new Result(e.toString(),false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    resultCompletableFuture.complete(new Result(true));
                }else {
                    resultCompletableFuture.complete(new Result("not successful",false));
                }
            }
        });
        return resultCompletableFuture;
    }

    private Result setUserValues(String body) throws JSONException {
        Gson gson = new Gson();
        UserRepository userRepository = UserRepository.getInstance();

        switch(userRepository.getType().toString()){
            case "STUDENT":
                Student student;
                student = gson.fromJson(body, Student.class);
                userRepository.setUser(student);
                break;
            case "SUPERVISOR":
                Supervisor supervisor;
                supervisor = gson.fromJson(body, Supervisor.class);
                userRepository.setUser(supervisor);
                break;
            default:
                return new Result("User Role not specified.",false);
        }
        return new Result(true);
    }


    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}


