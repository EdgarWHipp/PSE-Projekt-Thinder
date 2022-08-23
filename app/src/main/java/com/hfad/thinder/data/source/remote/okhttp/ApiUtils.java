package com.hfad.thinder.data.source.remote.okhttp;

import okhttp3.HttpUrl;

public class ApiUtils {
    private static ApiUtils instance;

    private boolean liveSetup = true;

    private String scheme = "http";
    private String host = "thinder-staging.herokuapp.com";

    private int port = 8080;

    public HttpUrl.Builder getHttpUrlBuilder() {
        if (liveSetup) {
            return new HttpUrl.Builder()
                    .scheme("https")
                    .host(host);
        } else {
            return new HttpUrl.Builder()
                    .scheme(scheme)
                    .host(host)
                    .port(port);
        }
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setLiveSetup(boolean liveSetup) {
        this.liveSetup = liveSetup;
    }

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
        }
        return instance;
    }
}