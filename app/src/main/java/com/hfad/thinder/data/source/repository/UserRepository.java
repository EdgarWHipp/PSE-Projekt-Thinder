package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a StudentRepository.
 * User data is saved locally in a List
 * for the first local test run of the frontend.
 */
public final class UserRepository implements BaseRepository<User> {
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
     * only used for local testing - ID information is handled in the backend.
     * returns an incrementing userId for each user object to use.
     *
     * @return the next counter value (old counter value +1)
     */
    public int iterUserId() {
        return getInstance().userId++;
    }

    /**
     * @return all users that are registered are returned.
     */
    @Override
    public List<User> getAll() {
        return dataSource.getUsers();

    }

    @Override
    public Optional<User> getById(final int id) {
        return dataSource.getUser(id);

    }

    /**
     * Adds the given user to the private local users list.
     *
     * @param user
     * @return true if the call succeeds and false otherwise.
     */
    @Override
    public boolean save(final User user) {
        return dataSource.postNewUser(user);
    }

    /**
     * Deletes the user with the given id,
     * if the call is not successful false is returned.
     *
     * @param id userId in User class
     * @return
     */
    @Override
    public boolean delete(final int id) {
        return dataSource.deleteUser(id);
    }


}
