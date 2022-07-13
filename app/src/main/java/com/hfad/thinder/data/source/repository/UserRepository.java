package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

import java.util.Set;
import java.util.UUID;

import okhttp3.Response;

/**
 * Singleton instance of a StudentRepository.
 * User data is saved locally in a List
 * for the first local test run of the frontend.
 */
public final class UserRepository {
    /**
     * Defines the Userrepository singleton instance.
     */
    @SuppressWarnings("checkstyle:StaticVariableName")
    private static UserRepository INSTANCE;
    private final UsersRemoteDataSource dataSource = new UsersRemoteDataSource();
    public Response delete
    private UUID currentId;
    private USERTYPE type;

    private UserRepository() {
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

    /**
     * handles the login -> sends the password and the mail and checks if such a user is already registrated.
     *
     * @return the id of the user
     */

    public Result login(String password, String eMail) {
        setCurrentUUID(dataSource.login(new Login(password, eMail)).getSecond());
        return dataSource.login(new Login(password, eMail)).getFirst();

    }

    /**
     * Used to verify the token, returns the necessary Result class.
     *
     * @param token
     * @return The result class with error message null and success value true or, when the call is unsuccessful, a full error message and a success value of false.
     */
    public boolean verifyToken(String token) {

        return dataSource.isVerify(token).getSuccess();
    }


    /**
     * Adds the given user to the private local users list.
     *
     * @param firstName,secondName,university,password,eMail
     * @return true if the call succeeds and false otherwise.
     */

    public Result registrate(String firstName, String secondName, String password, String eMail) {
        return dataSource.createNewUser(new User(password, eMail, firstName, secondName));
    }

    /**
     * Deletes the user that is currently using the application.
     * if the call is not successful false is returned.
     *
     * @param
     * @return Result
     */

    public Result delete() {
        return dataSource.deleteUser();
    }

    /**
     * Extends a user to a student (this occurs during the edit profile screen where the user has to input aditional information)
     *
     * @param degrees
     * @return Result
     */
    public Result extendUserToStudent(Set<Degree> degrees) {
        return dataSource.extendUserToStudent(degrees);
    }

    /**
     * Extends a user to a supervisor (this occurs during the edit profile screen where the user has to input aditional information)
     *
     * @param degree
     * @param location
     * @param institute
     * @param phoneNumber
     * @return Result
     */
    public Result extendUserToSupervisor(String degree, String location, String institute, String phoneNumber) {
        return dataSource.extendUserToSupervisor(degree, location, institute, phoneNumber);
    }

    /**
     * Returns the specified thesis - the thesis has to already be liked by the user.
     *
     * @param thesisId
     * @return
     */
    public Thesis getUserThesis(UUID thesisId) {
        return dataSource.getUserThesis(thesisId);
    }

    public UUID getCurrentId() {
        return currentId;
    }

    public USERTYPE getType() {
        return type;
    }

    public void setType(USERTYPE type) {
        this.type = type;
    }

    public UUID getCurrentUUID() {
        return currentId;
    }

    public void setCurrentUUID(UUID currentId) {
        this.currentId = currentId;
    }


}
