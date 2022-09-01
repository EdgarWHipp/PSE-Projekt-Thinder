package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.R;
import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.remote.okhttp.DegreeApiService;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This class handles the exceptions of all functionalities that are related to the courses of study.
 *
 */
public class DegreeRemoteDataSource {
    private final int TIMEOUT_SECONDS = 10000;
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
            Result result = list.getSecond().get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (result.getSuccess()) {
                DegreeRepository.getInstance().setAllDegrees(list.getFirst().get());
                return list.getSecond().get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            } else {
                return new Result(R.string.unsuccessful_response, false);
            }
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            return new Result(R.string.exception_during_HTTP_call, false);
        }
    }

}
