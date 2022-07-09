package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UsersApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  com.hfad.thinder.data.source.remote.retrofit.UsersApiService userService;
  com.hfad.thinder.data.source.remote.okhttp.UsersApiService okHttpService;
  OkHttpClient client = new OkHttpClient();
  String url = "http://localhost:8080";

  public Response usersLoginResponse(String password, String eMail) throws JSONException, IOException {
    JSONObject loginJson = new JSONObject()
            .put("password", password)
            .put("mail", eMail);
    RequestBody body = RequestBody.create(loginJson.toString(), JSON);
    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("localhost:8080")
            .addPathSegment("users")
            .addQueryParameter("password", password)
            .addQueryParameter("mail", eMail)
            .build();
    System.out.println(url.toString());
    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();

    Call call = client.newCall(request);
    return call.execute();
  }
  public Response extendUserToSupervisorResponse(String degree, String location, String institute, String phoneNumber) throws JSONException, IOException {
    JSONObject supervisorJson = new JSONObject()
            .put("academicDegree",degree)
            .put("location",location)
            .put("institute",institute)
            .put("phoneNumber",phoneNumber)
            .put("theses",null);

    RequestBody body=RequestBody.create(supervisorJson.toString(),JSON);

    Request request= new Request.Builder()
            .url(url+"/supervisor/"+ UserRepository.getInstance().getCurrentUUID())
            .put(body)
            .build();

    Call call = client.newCall(request);
    return call.execute();
  }
  public Response loginResponse(String token) throws JSONException, IOException {

    JSONObject tokenJson = new JSONObject()
            .put("token",token);
    RequestBody body=RequestBody.create(tokenJson.toString(),JSON);


    Request request= new Request.Builder()
            .url(url+"/users/verify/")
            .put(body)
            .build();

    Call call = client.newCall(request);

    return call.execute();
  }
  public Response createNewUserResponse(User user) throws JSONException, IOException {
    JSONObject userJson = new JSONObject()
            .put("firstName",user.getFirstName())
            .put("lastName",user.getLastName())
            .put("password",user.getPassword())
            .put("mail",user.geteMail());

    RequestBody body=RequestBody.create(userJson.toString(),JSON);


    Request request= new Request.Builder()
            .url(url+"/users/")
            .post(body)
            .build();

    Call call = client.newCall(request);
    return call.execute();
  }
  public Response deleteUserResponse() throws IOException {
    Request request= new Request.Builder()
            .url(url+"/users/"+UserRepository.getInstance().getCurrentUUID())
            .delete()
            .build();
    Call call = client.newCall(request);
    return call.execute();
  }
}
