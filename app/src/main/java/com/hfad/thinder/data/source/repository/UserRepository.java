package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a StudentRepository.
 * User data is saved locally in a List for the first local test run of the frontend.
 */
public final class UserRepository implements BaseRepository {
    private static UserRepository INSTANCE;
    private List<User> users;

    private UserRepository() {

    }

    /**
     * Get an instance of the user repository.
     *
     * @return
     */
    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }

        return INSTANCE;
    }

    @Override
    public List getAll() {
        return users;
    }

    @Override
    public Optional getById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean save(Object obj) {
        return users.add((User) obj);
    }

    /**
     * Deletes the user with the given id, if the call is not successful false is returned.
     *
     * @param id userId in User class
     * @return
     */
    @Override
    public boolean delete(int id) {
        return users.removeIf(user -> user.getId() == id);
    }
}
