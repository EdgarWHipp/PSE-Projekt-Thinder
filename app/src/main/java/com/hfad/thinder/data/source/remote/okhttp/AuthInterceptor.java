package com.hfad.thinder.data.source.remote.okhttp;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class is used to create the authentication client for HTTP basic authentification - in our case the mail and password is used for this process.
 */
class AuthInterceptor implements Interceptor {
  private String credentials;

  protected AuthInterceptor(String email, String password) {
    this.credentials = Credentials.basic(email, password);
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Request authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build();
    return chain.proceed(authenticatedRequest);
  }
}
