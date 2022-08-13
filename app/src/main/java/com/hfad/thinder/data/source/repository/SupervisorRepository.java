package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

public class SupervisorRepository {
    private static SupervisorRepository INSTANCE;
    private SupervisorRemoteDataSource supervisorRemoteDataSource = new SupervisorRemoteDataSource();

    private SupervisorRepository() {

    }

    /**
     * @return current instance of ThesisRepository singleton class.
     */
    public static SupervisorRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SupervisorRepository();
        }
        return INSTANCE;
    }

    public Result getAllCreatedTheses() {
        return supervisorRemoteDataSource.getCreatedThesisFromSupervisor();
    }

    /**
     * Edits the profile of a supervisor (this occurs during the edit profile screen where the user has to input additional information)
     *
     * @param degree
     * @param institute
     * @param phoneNumber
     * @return Result
     */
    public Result editProfilSupervisor(String degree, String officeNumber, String building, String institute, String phoneNumber, String firstName, String lastName) {
        return supervisorRemoteDataSource.extendUserToSupervisor(degree, officeNumber, building, institute, phoneNumber, firstName, lastName);
    }
}
