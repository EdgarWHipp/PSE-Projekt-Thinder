package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.data.source.remote.SupervisorRemoteDataSource;
import com.hfad.thinder.data.source.result.Result;

/**
 * Defines the interface between the frontend and backend, such that the ViewModel can use to interact with backend data.
 * This class includes all functionalities that can solely be executed when a supervisor is logged in.
 */
public class SupervisorRepository {
    private static SupervisorRepository INSTANCE;
    private SupervisorRemoteDataSource supervisorRemoteDataSource = new SupervisorRemoteDataSource();

    /**
     * private Constructor
     */
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

    /**
     * This function fetches all the thesis that a supervisor already created and sets the ArrayList<Thesis> that is returned inside the ThesisRepository.
     * @return Result class, containing a success value and an error message.
     */
    public Result getAllCreatedTheses() {
        return supervisorRemoteDataSource.getCreatedThesisFromSupervisor();
    }

    /**
     * Edits the profile of a supervisor (this occurs during the edit profile screen where the user has to input additional information).
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
