package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.source.result.Result;

import java.util.concurrent.CompletableFuture;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class DegreeApiService {
  private static final MediaType JSON
          = MediaType.parse("application/json; charset=utf-8");
  private static final OkHttpClient client = new OkHttpClient();
  private static final String url = "http://localhost:8080";
  private static final String  emulatorLocalHost = "http://10.0.2.2:8080";
  private String host="http";
  private String scheme ="10.0.2.2";
  private int port =8080;


}
