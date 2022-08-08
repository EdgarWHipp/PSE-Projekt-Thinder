package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Pair;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

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
    private final UsersRemoteDataSource usersDataSource = new UsersRemoteDataSource();
    private final StudentRemoteDataSource studentRemoteDataSource =  new StudentRemoteDataSource();
    private final SupervisorRemoteDataSource supervisorRemoteDataSource = new SupervisorRemoteDataSource();
    private final ThesisRemoteDataSource thesisRemoteDataSource = new ThesisRemoteDataSource();
    private String password = null;
    private UUID currentId = null;
    private USERTYPE type = null;
    private User user = null;

    public boolean isProfileComplete() {
        //return profileComplete; todo remove slashes
        return true;
    }

    public void setProfileComplete(boolean profileComplete) {
        this.profileComplete = profileComplete;
    }

    private boolean profileComplete = false;

    private UserRepository() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UUID getCurrentUUID() {
        return currentId;
    }

    public void setCurrentUUID(UUID currentId) {
        this.currentId = currentId;
    }

    public USERTYPE getType() {
        return type;
    }

    public void setType(USERTYPE type) {
        this.type = type;
    }

    public UsersRemoteDataSource getDatasource() {
        return usersDataSource;
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

    public Pair<Thesis,Result> getUserThesis(UUID thesisId) {
        return thesisRemoteDataSource.getNewThesis(thesisId);

    }

    /**
     * handles the login -> sends the password and the mail and checks if such a user is already registrated.
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
    public Result getUserRole(){
        return usersDataSource.getUserRole();
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
    public Result register(String firstName, String secondName, String password, String mail)  {
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
     * @param email
     * @return Result
     */
    public Result sendRecoveryEmail(String email){
        return usersDataSource.resetPassword(email);
    }

    /**
     * Sends the token that was received over the users mail to the backend
     * and changes the password of the user in the DB.
     * @param token
     * @param newPassword
     * @return Result
     */
    public Result resetPasswordWithToken(String token, String newPassword){
        return usersDataSource.sendNewPassword(token, newPassword);
    }

}
