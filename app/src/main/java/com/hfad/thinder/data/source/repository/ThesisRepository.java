package com.hfad.thinder.data.source.repository;

import android.media.Image;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.ThesisRemoteDataSource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Singleton instance of a ThesisRepository.
 * Database access to fetch theses is launched over this class.
 */
public final class ThesisRepository {
    private static ThesisRepository INSTANCE;
    private ThesisRemoteDataSource thesisRemoteDataSource =new ThesisRemoteDataSource();

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


   public Optional<List<Thesis>> getAll() {return thesisRemoteDataSource.getAllTheses();
    }


    public Optional<Thesis> getById(final int id) {
        return thesisRemoteDataSource.getThesis(id);
    }

//Supervisor entfernen? bzw nur variablen annehmen?
    public boolean addThesis(String name, String body, Form form, Supervisor supervisor, Set<Degree> degrees , Set<Image> images) {
        return thesisRemoteDataSource.createNewThesis(new Thesis(name,body,form,images,supervisor,null,degrees));
    }


    public boolean delete(final int id) {
        return false;
    }
}
