package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.LoginTuple;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.retrofit.UsersApiService;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Response;

public class UsersRemoteDataSource {
    // Für die ganze HTTP Funktionalitäten noch neue Klassen hinzufügen!
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private final com.hfad.thinder.data.source.remote.okhttp.UsersApiService okHttpService = new com.hfad.thinder.data.source.remote.okhttp.UsersApiService();
    UsersApiService userService;
    OkHttpClient client = new OkHttpClient();
    String url = "http://localhost:8080";

    /**
     * The purpose of this function is to parse the result of the HTTP request and handle all errors that might occur. All necessary parameters are passed
     * * to the extendUserToStudentResponse class in the UsersApiService.
     * he function returns the result - the success of the HTTP request and an appropriate error message in the case of failure are included in the result.
     *
     * @param degrees
     * @return Result
     */
    public Result extendUserToStudent(Set<Degree> degrees) {

        try {
            okhttp3.Response response = okHttpService.extendUserToStudentResponse(degrees);

            if (response.isSuccessful()) {
                return new Result(true);
            } else {
                return new Result("did not receive Statuscode 200", false);
            }

        } catch (IOException e) {
            return new Result("Error occurred in the API", false);
        } catch (JSONException j) {
            return new Result("Wrong format for the API", false);
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
    public Result extendUserToSupervisor(String degree, String location, String institute, String phoneNumber) {
        try {

            okhttp3.Response response = okHttpService
                    .extendUserToSupervisorResponse(degree, location, institute, phoneNumber);

            if (response.isSuccessful()) {
                return new Result(true);
            } else {
                return new Result("did not receive Statuscode 200", false);
            }

        } catch (IOException e) {
            return new Result("Error occurred in the API", false);
        } catch (JSONException j) {
            return new Result("Wrong format for the API", false);
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

            okhttp3.Response response = okHttpService.verifyResponse(token);
            if (response.isSuccessful()) {
                return new Result(true);
            } else {
                return new Result("did not receive Statuscode 200", false);
            }

        } catch (Exception e) {
            return new Result("Error occurred in the API", false);
        }


    }

    /**
     * Handles the error messages of the usersLoginResponse HTTP GET request in the UsersApiService class. Also checks if the response is successful.
     *
     * @param login
     * @return SHOULD BE RESULT!! -> change this
     */
    public LoginTuple login(Login login) {


        try {

            okhttp3.Response response = okHttpService.usersLoginResponse(login.getPassword(), login.geteMail());
            if (response.isSuccessful()) {

                //parse response to get id!
                //REMOVE LOGIN TUPLE
                return new LoginTuple(new Result(true), null);
            } else {
                return new LoginTuple(new Result("did not receive Statuscode 200", false), null);
            }
        } catch (IOException e) {
            return new LoginTuple(new Result("Error occurred in the API", false), null);
        } catch (JSONException j) {
            return new LoginTuple(new Result("Wrong format for the API", false), null);
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

            okhttp3.Response response = okHttpService.createNewUserResponse(user);

            //user ID und Typ abfragen


            if (response.isSuccessful()) {

                UserRepository.getInstance().setCurrentUUID(null);
                return new Result(true);
            } else {
                return new Result("did not receive Statuscode 200", false);
            }

        } catch (IOException e) {
            return new Result("Error occurred in the API", false);
        } catch (JSONException j) {
            return new Result("Wrong format for the API", false);
        }

    }

    /**
     * Handles the error messages of the deleteUserResponse HTTP DELETE request in the UsersApiService class. Also checks if the response is successful.
     *
     * @return Result
     */
    public Result deleteUser() {
        try {
            okhttp3.Response response = okHttpService.deleteUserResponse();
            if (response.isSuccessful()) {
                //User returnVal = result.body();
                return new Result(true);

            } else {
                return new Result("did not receive Statuscode 200", false);
            }
        } catch (IOException e) {
            System.out.println(e);
            // TO DO - bad practise!, dont return false return some error
            return new Result("Error occurred in the API", false);
        }


    }


    //Wie implemtiert ich update student und supervisor richtig??

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

    public Result deleteUserThesis(final UUID thesisId) {
        try {
            okhttp3.Response result = okHttpService.deleteUserThesisResponse(thesisId);
            if (result.isSuccessful()) {

                return new Result(true);

            }
        } catch (Exception e) {
            System.out.println(e);
            return new Result("Error occurred in the API", false);
        }
        return new Result("Did not receive status code 200", false);
    }


    public Thesis getUserThesis(final UUID thesisId) {
        try {
            okhttp3.Response result = okHttpService.getUserThesisResponse(thesisId);
            if (result.isSuccessful()) {

                return new Result(true);

            }
        } catch (Exception e) {
            System.out.println(e);
            return new Result("Error occurred in the API", false);
        }
        return new Result("Did not receive status code 200", false);
    }
}
}
