package com.hfad.thinder.data.source.remote.okhttp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ThesesApiServiceTest {
    private MockWebServer server;
    private ThesesApiService thesisApiService;
    private Supervisor supervisor;
    private ApiUtils apiUtils;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        supervisor = new Supervisor(USERTYPE.SUPERVISOR,
                new UUID(32, 32), true,
                new UUID(32, 32),
                "Test123", "test@kit.edu", "max", "mustermann", "Msc Informatik",
                "10.20", "2", "Telematik", "0123123123", false);

        thesisApiService = new ThesesApiService();
        apiUtils = ApiUtils.getInstance();
        apiUtils.setLiveSetup(false);
        apiUtils.setScheme("http");
        apiUtils.setHost(server.getHostName());
        apiUtils.setPort(server.getPort());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }


    @Test
    public void uploadThesisTest() throws JSONException, InterruptedException, IOException, ExecutionException {

        //User has to be logged in first:
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword(supervisor.getPassword());
        //Create actual Thesis post
        Set<Image> images = new HashSet<>();
        byte[] bytes = {64, 64, 64};
        images.add(new Image(bytes));
        Set<Degree> degrees = new HashSet<>();
        degrees.add(new Degree("Informatik", new UUID(32, 32)));
        Thesis thesis = new Thesis("Dr. Prof. Tamim",
                "testThesis", "aaaa", "aaa", new Form("asd"), images,
                supervisor, degrees);
        MockResponse responseUpload = new MockResponse().setResponseCode(200);
        server.enqueue(responseUpload);
        CompletableFuture<Result> resultThesisUpload = thesisApiService.createNewThesisFuture(thesis);
        server.takeRequest();
        assertTrue(resultThesisUpload.get().getSuccess());


    }

    @Test
    public void uploadThesisTestFail() throws JSONException, InterruptedException, IOException, ExecutionException {

        //User has to be logged in first:
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword(supervisor.getPassword());
        //Create actual Thesis post
        Set<Image> images = new HashSet<>();
        byte[] bytes = {64, 64, 64};
        images.add(new Image(bytes));
        Set<Degree> degrees = new HashSet<>();
        degrees.add(new Degree("Informatik", new UUID(32, 32)));
        Thesis thesis = new Thesis("Dr. Prof. Tamim",
                "testThesis", "aaaa", "aaa", new Form("asd"), images,
                supervisor, degrees);
        MockResponse responseUpload = new MockResponse().setResponseCode(500);
        server.enqueue(responseUpload);
        CompletableFuture<Result> resultThesisUpload = thesisApiService.createNewThesisFuture(thesis);
        server.takeRequest();
        assertFalse(resultThesisUpload.get().getSuccess());

    }



    //Important
    @Test
    public void getThesesStack() {

    }

    @Test
    public void getThesesStackFail() {

    }

    @Test
    public void deleteThesis() throws InterruptedException, ExecutionException {
        //Set the user
        UserRepository.getInstance().setUser(supervisor);
        //actual deleteThesis mock test
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        CompletableFuture<Result> resultCompletableFuture = thesisApiService.deleteThesisFuture(new UUID(32, 32));
        RecordedRequest request = server.takeRequest();
        Log.e("", request.getBody().toString());
        assertTrue(resultCompletableFuture.get().getSuccess());
    }

    @Test
    public void deleteThesisFail() throws InterruptedException, ExecutionException {
        //Set the user
        UserRepository.getInstance().setUser(supervisor);
        //actual deleteThesis mock test
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        CompletableFuture<Result> resultCompletableFuture = thesisApiService.deleteThesisFuture(new UUID(32, 32));
        server.takeRequest();
        assertFalse(resultCompletableFuture.get().getSuccess());
    }

    @Test
    public void deleteThesisFuture() throws InterruptedException, ExecutionException {
        //Set the user
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword(supervisor.getPassword());
        //actual deleteThesis mock test
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        CompletableFuture<Result> result = thesisApiService.deleteThesisFuture(new UUID(32,31));
        server.takeRequest();
        assertTrue(result.get().getSuccess());
    }
    @Test
    public void deleteThesisFutureFail() throws InterruptedException, ExecutionException {
        //Set the user
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword(supervisor.getPassword());
        //actual deleteThesis mock test
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        CompletableFuture<Result> result = thesisApiService.deleteThesisFuture(new UUID(32,31));
        server.takeRequest();
        assertFalse(result.get().getSuccess());
    }


}
