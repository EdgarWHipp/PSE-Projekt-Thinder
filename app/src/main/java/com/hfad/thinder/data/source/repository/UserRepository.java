package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.ThesisTuple;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

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
    private UUID currentId=null;
    private USERTYPE type=null;
    private User user=null;


    public void setUser(User user) {
        this.user = user;
    }

    public ThesisTuple getUserThesis(UUID thesisId) {
        return usersDataSource.getUserThesis(thesisId);
    }
    // Relevant for the ViewModel
    public User getUser() {
        return user;
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
        return usersDataSource.login(new Login(password, eMail));

    }

    /**
     * Used to verify the token, returns the necessary Result class.
     *
     * @param token
     * @return The result class with error message null and success value true or, when the call is unsuccessful, a full error message and a success value of false.
     */
    public Result verifyToken(String token) {

        return usersDataSource.isVerify(token);
    }


    /**
     * Adds the given user to the private local users list.
     *
     * @param firstName,secondName,university,password,eMail
     * @return true if the call succeeds and false otherwise.
     */

    public Result registrate(String firstName, String secondName, String password, String eMail)  {
        return usersDataSource.createNewUser(new UserCreation(firstName, secondName, eMail, password));

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
     * Extends a user to a student (this occurs during the edit profile screen where the user has to input aditional information)
     *
     * @param degrees
     * @return Result
     */
    public Result editProfilStudent(Set<Degree> degrees, String firstName, String lastName) {
        return studentRemoteDataSource.extendUserToStudent(degrees,firstName,lastName);
    }

    /**
     * Edits the profile of a supervisor (this occurs during the edit profile screen where the user has to input additional information)
     *
     * @param degree
     * @param location
     * @param institute
     * @param phoneNumber
     * @return Result
     */
    public Result editProfilSupervisor(String degree, String location, String institute, String phoneNumber,String firstName,String lastName) {
        return supervisorRemoteDataSource.extendUserToSupervisor(degree, location, institute, phoneNumber,firstName,lastName);
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
