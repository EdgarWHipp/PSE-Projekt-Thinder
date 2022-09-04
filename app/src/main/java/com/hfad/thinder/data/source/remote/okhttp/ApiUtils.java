package com.hfad.thinder.data.source.remote.okhttp;

import okhttp3.HttpUrl;

/**
 * This class holds various settings for the HTTP calls, mainly making the switch from the live setup to the test setup (local) possible.
 */
public class ApiUtils {
    private static ApiUtils instance;

    private boolean liveSetup = true;

    private String host = "thinder-staging.herokuapp.com";

    private int port = 8080;

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
        }
        return instance;
    }

    /**
     * Differentiates between the live setup (heroku) and the local test setup.
     *
     * @return HttpUrl.Builder
     */
    public HttpUrl.Builder getHttpUrlBuilder() {
        if (liveSetup) {
            return new HttpUrl.Builder()
                    .scheme("https")
                    .host(host);
        } else {
            return new HttpUrl.Builder()
                    .scheme("http")
                    .host(host)
                    .port(port);
        }
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
}