package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.viewmodels.LoginResult;
import com.hfad.thinder.viewmodels.RegistrationResult;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a StudentRepository.
 * User data is saved locally in a List
 * for the first local test run of the frontend.
 */
public final class UserRepository {
    private String currentId;
    /**
     * Defines the Userrepository singleton instance.
     */
    @SuppressWarnings("checkstyle:StaticVariableName")
    private static UserRepository INSTANCE;
    private UsersRemoteDataSource dataSource;

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
     * @return the id of the user
     */

    public Result login(String password, String eMail) {
        currentId=dataSource.login(password, eMail).getSecond();
        return dataSource.login(password, eMail).getFirst();

    }
    /*
    public Result verifyToken(Token token){
        if(token.isValid()){
            return new Result(true);
        }else {
            return new Result()
        }
    }
    */


    public Optional<User> getById(final int id) {
        return dataSource.getUser(id);
    }

    /**
     * Adds the given user to the private local users list.
     *
     * @param firstName,secondName,university,password,eMail
     * @return true if the call succeeds and false otherwise.
     */

    public Result registrate(String firstName, String secondName, String university, String password, String eMail) {
        return dataSource.createNewUser(new User(password,eMail,firstName,secondName,university));
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
