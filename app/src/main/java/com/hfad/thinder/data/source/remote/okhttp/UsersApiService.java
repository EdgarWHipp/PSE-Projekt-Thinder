package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
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
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UsersApiService {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();
    private static final String url = "http://localhost:8080";
    private static final String  emulatorLocalHost = "http://10.0.2.2:8080";

    private Result setUserValues(String body) throws IOException, JSONException {
        UserRepository repository = UserRepository.getInstance();
        Gson gson = new Gson();
        User user;
        Student student=null;
        Supervisor supervisor = null;
        user = gson.fromJson(body, User.class);
        repository.setUser(user);
        switch(repository.getType().toString()){
            case "STUDENT":
                student = gson.fromJson(body, Student.class);
                repository.setUser(student);
                break;
            case "SUPERVISOR":
                supervisor = gson.fromJson(body, Supervisor.class);
                repository.setUser(supervisor);
                break;
            default:
                return new Result("type not correctly specified",false);
        }
        return new Result(true);


    }

    /**
     * This function creates a HTTP GET request with http basic authentication(email & password)
     * to set the USERTYPE of the user loggin in for the UserRepository.getInstance().getType() function.
     * Checks if the asynchronous call return fails or responds.
     * @param password
     * @param eMail
     * @return CompletableFuture<Result>
     */
    private CompletableFuture<Result> setUserRole(String password, String eMail){
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(eMail, password))
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(8080)
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
                                resultCompletableFuture.complete(new Result(true));
                                break;
                            case "STUDENT":
                                UserRepository.getInstance().setType(USERTYPE.STUDENT);
                                resultCompletableFuture.complete(new Result(true));
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
     * @param password of user loging in
     * @param eMail    of user loging in
     * @returnCompletableFuture<Result>
     * @throws JSONException
     * @throws IOException
     */
    public CompletableFuture<Result> usersLoginFuture(String password, String eMail) throws JSONException, IOException {
        setUserRole(password,eMail);
        OkHttpClient clientAuth = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(eMail, password))
                .build();

        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(8080)
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
    public CompletableFuture<Result> verifyFuture(String token) throws JSONException, IOException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();

        RequestBody body = RequestBody.create(null, new byte[]{});
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(8080)
                .addPathSegment("users")
                .addPathSegment("verify")
                .addQueryParameter("token",token)
                .build();
        // empty body
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);

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
     * This function creates the HTTP POST request and thus, if no error occurs, leads to the creation of a new user in the postgres database.
     * Also already defines the id and type of the registrated user for the UserRepository.
     * Checks if the asynchronous call return fails or responds.
     * @param user
     * @return  CompletableFuture<Result>
     * @throws JSONException
     */
    public CompletableFuture<Result> createNewUserFuture(UserCreation user) throws JSONException {
        //this.setUserRole(user.getPassword(),user.geteMail());
        JSONObject userJson = new JSONObject()
                .put("firstName", user.getFirstName())
                .put("lastName", user.getLastName())
                .put("password", user.getPassword())
                .put("mail", user.getEmail())
                .put("role","USER");



        RequestBody body = RequestBody.create(userJson.toString(), JSON);

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(8080)
                .addPathSegment("users")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                resultCompletableFuture.complete(new Result(e.toString(), false));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
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
     * This function creates the HTTP DELETE request that removes a user from the database (if successful)
     * Checks if the asynchronous call return fails or responds.
     * @return CompletableFuture<Result>
     * @throws IOException
     */
    public CompletableFuture<Result> deleteUserFuture() throws IOException {
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<Result>();

        Request request= new Request.Builder()
                .url(url+"/users/"+UserRepository.getInstance().getCurrentUUID())
                .delete()
                .build();
        Call call = client.newCall(request);

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
     * @return
     */
    public CompletableFuture<Result> resetPasswordFuture(String email){
        CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();


        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(8080)
                .addPathSegment("resetPassword")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = client.newCall(request);
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
    public CompletableFuture<Result> sendNewPasswordFuture(String token, String newPassword) throws JSONException {
        CompletableFuture<Result> resultCompletableFuture= new CompletableFuture<>();

        RequestBody body = RequestBody.create(null);
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("10.0.2.2")
                .port(8080)
                .addPathSegment("resetPassword")
                .addQueryParameter("token",token)
                .addQueryParameter("password",newPassword)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);

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




    /**
     * This function creates the HTTP DELETE request for a thesis that the user has unliked. Unliking it removes it
     * from the users liked theses page.
     *
     * @param thesisId
     * @return Response
     */
    public Response deleteUserThesisCall(final UUID thesisId) throws IOException {

        Request request = new Request.Builder()
                .url(url + "/users/thesis/" + thesisId)
                .delete()
                .build();
        Call call = client.newCall(request);
        return call.execute();

    }

    /**
     * This function creates the HTTP GET request for a thesis that the user has already liked - it is used to make sure
     * that the user can click on a liked thesis and receive a more detailed overview.
     *
     * @param thesisId
     * @return Response
     */
    public Response getUserThesisResponse(final UUID thesisId) throws IOException {
        Request request = new Request.Builder()
                .url(url + "/users/thesis/" + thesisId)
                .get()
                .build();
        Call call = client.newCall(request);
        return call.execute();
    }

}
