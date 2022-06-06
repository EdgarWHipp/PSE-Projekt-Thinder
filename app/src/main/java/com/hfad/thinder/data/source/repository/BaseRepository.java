package com.hfad.thinder.data.source.repository;

import java.util.List;
import java.util.Optional;

/**
 * Base Repository interface that includes all necessary functions.
 *
 * @param <T> Type of fetched items
 */
public interface BaseRepository<T> {

  List<T> getAll();

  Optional<T> getById(int id);

  boolean save(T obj);

  boolean delete(int id);

}
