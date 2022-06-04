package com.hfad.thinder.data.source.repository;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository implements BaseRepository {
    private static ThesisRepository INSTANCE;

    private ThesisRepository() {

    }

    public static ThesisRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThesisRepository();
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
