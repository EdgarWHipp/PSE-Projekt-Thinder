package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;

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
    private USERTYPE type;

    public UUID getCurrentId() {
        return currentId;
    }

    public USERTYPE getType() {
        return type;
    }

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




    /**
     * handles the login -> sends the password and the mail and checks if such a user is already registrated.
     * @return the id of the user
     */

    public Result login(String password, String eMail) {
        setCurrentId(dataSource.login(new Login(password,eMail)).getSecond());
        return dataSource.login(new Login(password,eMail)).getFirst();

    }

    public void setCurrentId(UUID currentId) {
        this.currentId = currentId;
    }

    public void setType(USERTYPE type) {
        this.type = type;
    }

    /**
     * Used to verify the token, returns the necessary Result class.
     * @param token
     * @return The result class with error message null and success value true or, when the call is unsuccessful, a full error message and a success value of false.
     */
    public boolean verifyToken(String token){

        if(dataSource.isVerify(token).getSuccess()){
            return true;
        }else {
            return false;
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
     * @param
     * @return
     */

    public Result delete() {
        return dataSource.deleteUser();
    }

    public Result extendUserToStudent(Set<Degree> degrees){
        return dataSource.extendUserToStudent(degrees);
    }
    public Result extendUserToSupervisor(String degree, String location, String institute,String phoneNumber){
        return dataSource.extendUserToSupervisor(degree,location,institute,phoneNumber);
    }

}
