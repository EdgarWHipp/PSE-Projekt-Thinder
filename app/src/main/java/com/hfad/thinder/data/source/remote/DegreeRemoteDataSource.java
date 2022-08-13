package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.remote.okhttp.DegreeApiService;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This class handles the exceptions of all functionalities that are related to the courses of study.
 *
 */
public class DegreeRemoteDataSource {
    private final DegreeApiService okHttpService = new DegreeApiService();

    /**
     * Handles the exceptions of the fetchAllCoursesOfStudyFuture in the DegreeApiService - This HTTP call fetches all courses of study,
     * such as "Bsc Informatik", that can then be used either by the student to search for theses regarding a certain course of study or make it possible
     * for the supervisors to select a range of courses of study that their created thesis could be relevant for.
     * @return Result
     */
    public Result fetchAllCoursesOfStudyFromAUniverisity() {
        try {
            Pair<CompletableFuture<ArrayList<Degree>>, CompletableFuture<Result>> list = okHttpService.fetchAllCoursesOfStudyFuture();
            if (list.getSecond().get().getSuccess()) {
                DegreeRepository.getInstance().setAllDegrees(list.getFirst().get());
                return list.getSecond().get();
            } else {
                return new Result("not successful", false);
            }
        } catch (ExecutionException e) {
            return new Result("not successful", false);
        } catch (InterruptedException e) {
            return new Result("not successful", false);
        }
    }

}
