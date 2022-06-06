package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a StudentRepository.
 * User data is saved locally in a List for the first local test run of the frontend.
 */
public final class UserRepository implements BaseRepository<User> {
  private static UserRepository INSTANCE;
  private int userId = -1;
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

  /** only used for local testing - ID information is handled in the backend.
   * returns an incrementing userId for each user object to use.
   *
   * @return
   */
  public int iterUserId() {
    return getInstance().userId++;
  }

  @Override
  public List getAll() {
    return users;
  }

  @Override
  public Optional getById(int id) {
    return Optional.empty();
  }

    /**
     * Adds the given user to the private local users list.
     * @param user
     * @return
     */
  @Override
  public boolean save(User user) {


    return users.add(user);
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

  /**
   * Checks if a user exists with the given password and email pair.
   *
   * @param password
   * @param email
   * @return
   */
  public boolean check(String password, String email) {
    return users.stream().anyMatch(user -> user.getPassword().equals(password) && user.geteMail().equals(email));
  }
}
