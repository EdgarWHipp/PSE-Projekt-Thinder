package com.hfad.thinder.data.source.remote.okhttp;

import okhttp3.HttpUrl;

public class ApiUtils {
    private static final String HTTPS = "https";
    private static final boolean liveSetup = true;

    private static final String HTTP = "http";
    private static final String REMOTE_HOST = "thinder-staging.herokuapp.com";
    private static final String LOCALHOST = "127.0.0.1";

    private static int port = 8080;


    /**
     * Private utility default constructor
     */
    private ApiUtils(){

    }

    public static HttpUrl.Builder getHttpUrlBuilder() {
        if (liveSetup) {
            return new HttpUrl.Builder()
                    .scheme(HTTPS)
                    .host(REMOTE_HOST);
        } else {
            return new HttpUrl.Builder()
                    .scheme(HTTP)
                    .host(LOCALHOST)
                    .port(port);
        }
    }
}