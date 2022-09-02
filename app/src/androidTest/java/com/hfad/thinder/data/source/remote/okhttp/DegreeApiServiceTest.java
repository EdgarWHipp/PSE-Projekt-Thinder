package com.hfad.thinder.data.source.remote.okhttp;

import android.util.Log;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleSupervisor;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleUser;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class DegreeApiServiceTest {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private MockWebServer server;
    private DegreeApiService degreeApiService;
    private ApiUtils apiUtils;
    @Before
    public void setUp() {
        server = new MockWebServer();

        degreeApiService = new DegreeApiService();
        apiUtils = ApiUtils.getInstance();

        apiUtils.setLiveSetup(false);
        apiUtils.setHost(server.getHostName());
        apiUtils.setPort(server.getPort());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void fetchAllCoursesOfStudySuccess() throws ExecutionException, InterruptedException {
        //set a user
        Supervisor supervisor = SampleSupervisor.supervisorObject();
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword("password");
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        Pair<CompletableFuture<ArrayList<Degree>>, CompletableFuture<Result>> values =  degreeApiService.fetchAllCoursesOfStudyFuture();
        ArrayList<Degree> degrees=values.getFirst().get();
        Result result = values.getSecond().get();
        Assert.assertEquals(degrees,null);
        RecordedRequest request = server.takeRequest();
        Assert.assertTrue(result.getSuccess());
    }
    @Test
    public void fetchAllCoursesOfStudyFail() throws ExecutionException, InterruptedException {
        //set a user
        Supervisor supervisor = SampleSupervisor.supervisorObject();
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword("password");
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Log.e("","works till here");
        Pair<CompletableFuture<ArrayList<Degree>>, CompletableFuture<Result>> values =  degreeApiService.fetchAllCoursesOfStudyFuture();
        Log.e("","http call worked ");
        Result result = values.getSecond().get();
        Assert.assertFalse(result.getSuccess());
    }
}
