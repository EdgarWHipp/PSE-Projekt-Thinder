package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

import java.util.UUID;

/**
 * Defines the interface between the frontend and backend, such that the ViewModel can use to interact with backend data.
 * All interactions that regard the user and are not special functionalities for either the supervisor or the student are saved inside this repository.
 */
public final class UserRepository {
    @SuppressWarnings("checkstyle:StaticVariableName")
    private static UserRepository INSTANCE;
    private final UsersRemoteDataSource usersDataSource = new UsersRemoteDataSource();
    private final ThesisRemoteDataSource thesisRemoteDataSource = new ThesisRemoteDataSource();
    private String password = null;
    private UUID currentId = null;
    private USERTYPE type = null;
    private User user = null;
    private Result result = null;

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
     * handles the login - sends the password and the mail and checks if such a user is already registered.
     *
     * @return the id of the user
     */

    public Result login(String password, String mail) {
        return usersDataSource.login(new Login(mail, password));
    }

    /**
     * Used to verify the token, returns the necessary Result class.
     *
     * @param token
     * @return The result class with error message null and success value true or, when the call is unsuccessful, a full error message and a success value of false.
     */

    public Result verifyUser(String token) {
        return usersDataSource.verify(token);
    }

    /**
     * Adds the given user to the private local users list.
     *
     * @param firstName
     * @param secondName
     * @param password
     * @param mail
     * @return
     */
    public Result register(String firstName, String secondName, String password, String mail) {
        return usersDataSource.createNewUser(new UserCreation(firstName, secondName, mail, password));
    }

    /**
     * Deletes the user that is currently using the application.
     * if the call is not successful false is returned.
     *
     * @param
     * @return Result
     */

    public Result delete() {
        return usersDataSource.deleteUser();
    }


    /**
     * The Backend sends the code that the user needs to reset their email.
     *
     * @param email
     * @return Result
     */
    public Result sendRecoveryEmail(String email) {
        return usersDataSource.resetPassword(email);
    }

    /**
     * Sends the token that was received over the users mail to the backend
     * and changes the password of the user in the DB.
     *
     * @param token
     * @param newPassword
     * @return Result
     */
    public Result resetPasswordWithToken(String token, String newPassword) {
        return usersDataSource.sendNewPassword(token, newPassword);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public USERTYPE getType() {
        return type;
    }

    public void setType(USERTYPE type) {
        this.type = type;
    }
}
