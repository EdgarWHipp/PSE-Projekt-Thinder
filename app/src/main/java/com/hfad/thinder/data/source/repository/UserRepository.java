package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.LoginResult;
import com.hfad.thinder.viewmodels.RegistrationResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Singleton instance of a StudentRepository.
 * User data is saved locally in a List
 * for the first local test run of the frontend.
 */
public final class UserRepository {
    private UUID currentId;
    /**
     * Defines the Userrepository singleton instance.
     */
    @SuppressWarnings("checkstyle:StaticVariableName")
    private static UserRepository INSTANCE;
    private UsersRemoteDataSource dataSource= new UsersRemoteDataSource();

    private UserRepository() {

    }

    public UUID getCurrentUUID() {
        return currentId;
    }
    public void setCurrentUUID(UUID id) {
        currentId=id;
    }



    /**
     * Get an instance of the user repository.
     *
     * @return the current singleton instance is returned
     */
    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }

        return INSTANCE;
    }



    public Result studentRegistration(Set<Degree> degrees){
        return dataSource.extendUserToStudent(degrees);
    }
    /**
     * handles the login -> sends the password and the mail and checks if such a user is already registrated.
     * @return the id of the user
     */

    public Result login(String password, String eMail) {
        currentId=dataSource.login(password, eMail).getSecond();
        return dataSource.login(password, eMail).getFirst();

    }

    /**
     * Used to verify the token, returns the necessary Result class.
     * @param token
     * @return The result class with error message null and success value true or, when the call is unsuccessful, a full error message and a success value of false.
     */
    public Result verifyToken(String token){

        if(dataSource.isVerify(token)){
            return new Result(true);
        }else {
            return new Result("You entered the wrong token",false);
        }
    }



    public Optional<User> getById(final int id) {
        return dataSource.getUser(id);
    }

    /**
     * Adds the given user to the private local users list.
     *
     * @param firstName,secondName,university,password,eMail
     * @return true if the call succeeds and false otherwise.
     */

    public Result registrate(String firstName, String secondName, String password, String eMail) throws JSONException {
        return dataSource.createNewUser(new User(password,eMail,firstName,secondName));
    }

    /**
     * Deletes the user with the given id,
     * if the call is not successful false is returned.
     *
     * @param id userId in User class
     * @return
     */

    public boolean delete(final int id) {
        return dataSource.deleteUser(id);
    }


}
