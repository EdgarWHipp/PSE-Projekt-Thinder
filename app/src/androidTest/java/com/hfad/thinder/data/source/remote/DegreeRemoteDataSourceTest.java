package com.hfad.thinder.data.source.remote;

import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.source.remote.DegreeRemoteDataSource;
import com.hfad.thinder.data.source.remote.okhttp.ApiUtils;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleSupervisor;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class DegreeRemoteDataSourceTest {
    private MockWebServer server;
    private DegreeRemoteDataSource degreeRemoteDataSource;
    private ApiUtils apiUtils;

    @Before
    public void setUp() {
        server = new MockWebServer();

        degreeRemoteDataSource = new DegreeRemoteDataSource();
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
    public void fetchAllCoursesOfStudyFromAUniverisitySuccess() {
        //set a user
        Supervisor supervisor = SampleSupervisor.supervisorObject();
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword("password");


        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Result result = degreeRemoteDataSource.fetchAllCoursesOfStudyFromAUniverisity();
        Assert.assertTrue(result.getSuccess());

    }

    @Test
    public void fetchAllCoursesOfStudyFromAUniverisityFail() {
        //set a user
        Supervisor supervisor = SampleSupervisor.supervisorObject();
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword("password");


        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Result result = degreeRemoteDataSource.fetchAllCoursesOfStudyFromAUniverisity();
        Assert.assertFalse(result.getSuccess());
    }

}
