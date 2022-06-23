package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
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
    static int id;
    /**
     * Defines the Userrepository singleton instance.
     */
    @SuppressWarnings("checkstyle:StaticVariableName")
    private static UserRepository INSTANCE;
    private UsersRemoteDataSource dataSource;
    /**
     * Declares the global id counter for users.
     */
    private int userId = -1;
    /**
     * List of all registered users.
     */
    private List<User> users;

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

    public LoginResult login(String password, String eMail) {
        return dataSource.login(password, eMail);

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

    public RegistrationResult registrate(String firstName, String secondName,String university,String password, String eMail) {
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
