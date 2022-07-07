package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;
import com.hfad.thinder.data.source.repository.LoginTuple;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Call;
import retrofit2.Response;

public class UsersRemoteDataSource {
    // Für die ganze HTTP Funktionalitäten noch neue Klassen hinzufügen!
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    UsersApiService userService;
    OkHttpClient client = new OkHttpClient();
    String url = "http://localhost:8080";


    public Result extendUserToStudent(Set<Degree> degrees){

        try {
            JSONObject studentJson = new JSONObject()
                    .put("degrees",degrees)
                    .put("thesesRatings",null);


            RequestBody body=RequestBody.create(studentJson.toString(),JSON);


            Request request= new Request.Builder()
                    .url(url+"/student/"+ UserRepository.getInstance().getCurrentUUID())
                    .put(body)
                    .build();

            Call call = client.newCall(request);
            okhttp3.Response response = call.execute();
            if (response.isSuccessful()){
                return new Result(true);
            }else{
                return new Result("did not receive Statuscode 200",false);
            }

        } catch (Exception e) {
            return new Result(e.toString(),false);
        }
    }
    public Result extendUserToSupervisor(String degree,String location, String institute,String phoneNumber){
        try {
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
            okhttp3.Response response = call.execute();
            if (response.isSuccessful()){
                return new Result(true);
            }else{
                return new Result("did not receive Statuscode 200",false);
            }

        } catch (Exception e) {
            return new Result(e.toString(),false);
        }
    }
    public boolean isVerify(String token){
        try {
            Response<Boolean> result = userService.isVerifyToken(token);
            if (result.isSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    private List<Thesis> getThesesFromUser(int userId) {
        try {
            Response<List<Thesis>> result = userService.getTheses(userId);
            if (result.isSuccessful()) {
                List<Thesis> returnVal = result.body();
                return returnVal;

            } else {
                // TO DO - bad practise!, dont return null return some error
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return null return some error
            return null;
        }


    }

    public LoginTuple login(String password, String eMail) {


        try {
            JSONObject loginJson = new JSONObject()
                    .put("password",password)
                    .put("mail",eMail);
            RequestBody body=RequestBody.create(loginJson.toString(),JSON);
            Request request= new Request.Builder()
                    .url(url+"/users/")
                    .get()
                    .build();

            Call call = client.newCall(request);
            okhttp3.Response response = call.execute();

            if (response.isSuccessful()){
                //parse response to get id!
                return new LoginTuple(new Result(true),null);
            }else{
                return new LoginTuple( new Result("did not receive Statuscode 200",false),null);
            }
        } catch (Exception e) {
            return new LoginTuple(new Result("login not successful due to : "+e.toString(),false),null);
        }

    }



    public Result createNewUser(User user) throws JSONException {
        try {
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
            okhttp3.Response response = call.execute();
            System.out.println(response.body().string());
            if (response.isSuccessful()){

                UserRepository.getInstance().setCurrentUUID(null);
                return new Result(true);
            }else{
                return new Result("did not receive Statuscode 200",false);
            }

        } catch (Exception e) {
            return new Result(e.toString(),false);
        }

    }

    public boolean deleteUser(final int id) {
        try {
            Response<User> result = userService.deleteUser(id);
            if (result.isSuccessful()) {
                //User returnVal = result.body();
                return true;

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return false;
        }
        return false;
    }

    public Optional<User> getUser(final int id) {
        try {
            Response<User> result = userService.getUser(id);
            if (result.isSuccessful() && result.body() != null) {
                User returnVal = result.body();
                return Optional.of(returnVal);

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(null);
    }

    public boolean updateStudent(final int id, final Student student) {
        try {
            Response<Student> result = userService.changeStudent(id, student);
            if (result.isSuccessful() && result.body() != null) {

                return true;

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return false;
        }
        return false;
    }
    public boolean updateSupervisor(final int id, final Supervisor supervisor) {
        try {
            Response<Supervisor> result = userService.changeStudent(id, supervisor);
            if (result.isSuccessful() && result.body() != null) {

                return true;

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return false;
        }
        return false;
    }
    public boolean deleteUserThesis(final int userId,final int thesisId){
        try {
            Response<Thesis> result = userService.deleteUserThesis(userId,thesisId);
            if (result.isSuccessful() && result.body() != null) {

                return true;

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return false;
        }
        return false;
    }
    /* This function might be useless.

    public boolean changeLikedThesis(final int userId,final int thesisId, final Thesis thesis){
        try {
            Response<Thesis> result = userService.changeUserThesis(userId,thesisId,thesis);
            if (result.isSuccessful() && result.body() != null) {

                return true;

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return false;
        }
        return false;
    }
    */

    public Optional<Thesis> getUserThesis(final int userId,final int thesisId){
        try {
            Response<Thesis> result = userService.getUserThesis(userId,thesisId);
            if (result.isSuccessful() && result.body() != null) {

                return Optional.of(result.body());

            }
        } catch (Exception e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return Optional.ofNullable(null);
        }
        return Optional.ofNullable(null);
    }
}
