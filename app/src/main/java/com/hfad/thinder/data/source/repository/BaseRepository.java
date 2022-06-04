package com.hfad.thinder.data.source.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    List<T> getAll();

    Optional<T> getById(int id);

    Exception save(T obj);

    Exception delete(int id);

}
