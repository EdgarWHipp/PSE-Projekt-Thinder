package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;

import java.util.List;
import java.util.Optional;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository implements BaseRepository<Thesis> {
    private static ThesisRepository INSTANCE;
    private ThesisRemoteDataSource thesisRemoteDataSource;

    private ThesisRepository() {

    }

    /**
     * @return current instance of ThesisRepository singleton class.
     */
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
    public Optional getById(final int id) {
        return Optional.empty();
    }

    @Override
    public boolean save(final Thesis thesis) {
        return false;
    }

    @Override
    public boolean delete(final int id) {
        return false;
    }
}
