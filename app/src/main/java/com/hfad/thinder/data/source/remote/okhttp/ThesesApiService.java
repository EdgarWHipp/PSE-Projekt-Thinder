package com.hfad.thinder.data.source.remote.okhttp;

import androidx.annotation.NonNull;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.result.Result;
import com.hfad.thinder.data.source.result.Tuple;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThesesApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080";
  private static final String  emulatorLocalHost = "http://10.0.2.2:8080";

  public Tuple<CompletableFuture<List<Thesis>>,CompletableFuture<Result>> getAllPositivRatedThesesFuture(UUID id){
    CompletableFuture<Result> resultCompletableFuture = new CompletableFuture<>();
    CompletableFuture<List<Thesis>> thesisListFuture = new CompletableFuture<>();


    HttpUrl url = new HttpUrl.Builder()
            .scheme("http")
            .host("10.0.2.2")
            .port(8080)
            .addPathSegment("users")
            .addPathSegment(id.toString())
            .addPathSegment("rated-theses")
            .build();

    Request request = new Request.Builder()
            .url(url)
            .get()
            .build();

    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultCompletableFuture.complete(new Result(e.toString(),false));
        thesisListFuture.complete(null);
      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(response.isSuccessful()){
          resultCompletableFuture.complete(new Result(true));
          // parse all returned theses.
          thesisListFuture.complete(null);
        }else{
          resultCompletableFuture.complete(new Result("not successful",false));
          thesisListFuture.complete(null);
        }
      }
    });

    Tuple<CompletableFuture<List<Thesis>>,CompletableFuture<Result>> resultCompletableFutureTuple
            = new Tuple<>(thesisListFuture,resultCompletableFuture);
    return resultCompletableFutureTuple;
  }
}
