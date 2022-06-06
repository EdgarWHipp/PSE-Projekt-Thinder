package com.hfad.thinder.data.source.repository;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a StudentRepository.
 */
public final class StudentRepository implements BaseRepository {
  private static StudentRepository INSTANCE;

  private StudentRepository() {

  }

  public static StudentRepository getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new StudentRepository();
    }

    return INSTANCE;
  }

  @Override
  public List getAll() {
    return null;
  }

  @Override
  public Optional getById(int id) {
    return Optional.empty();
  }

  @Override
  public boolean save(Object obj) {
    return false;
  }

  @Override
  public boolean delete(int id) {
    return false;
  }
}
