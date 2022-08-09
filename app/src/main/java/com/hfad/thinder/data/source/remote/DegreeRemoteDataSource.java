package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.source.remote.okhttp.DegreeApiService;
import com.hfad.thinder.data.source.remote.okhttp.StudentApiService;
import com.hfad.thinder.data.source.repository.DegreeRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DegreeRemoteDataSource {
  private final DegreeApiService okHttpService = new DegreeApiService();

  /**
   *
   * @return Result
   */
  public Result fetchAllCoursesOfStudyFromAUniverisity(){
    try{
      Pair<CompletableFuture<ArrayList<Degree>>,CompletableFuture<Result>> list =okHttpService.fetchAllCoursesOfStudyFuture();
      if (list.getSecond().get().getSuccess()){
        DegreeRepository.getInstance().setAllDegrees(list.getFirst().get());
        return list.getSecond().get();
      }else{
        return new Result("not successful",false);
      }
    }
     catch (ExecutionException e) {
      return new Result("not successful",false);
    } catch (InterruptedException e) {
      return new Result("not successful",false);
    }
  }

}
