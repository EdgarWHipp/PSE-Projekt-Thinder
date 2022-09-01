package com.hfad.thinder.data.source.remote.okhttp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleStudent;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleSupervisor;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleThesis;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleUser;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ThesesApiServiceTest {
    private MockWebServer server;
    private ThesesApiService thesisApiService;
    private ApiUtils apiUtils;

    @Before
    public void setUp() {
        server = new MockWebServer();

        thesisApiService = new ThesesApiService();
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
    public void createNewThesisFuture() throws JSONException, InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        CompletableFuture<Result> resultThesisUpload =
                thesisApiService.createNewThesisFuture(SampleThesis.thesisObject());

        RecordedRequest request = server.takeRequest();
        JSONObject newThesisJson = new JSONObject(request.getBody().readUtf8());

        // Check response code 200 is handled properly
        assertTrue(resultThesisUpload.get().getSuccess());

        // Check fields
        assertEquals(SampleThesis.name, newThesisJson.get("name"));
        assertEquals(SampleThesis.supervisingProfessor, newThesisJson.get("supervisingProfessor"));
        assertEquals(SampleThesis.motivation, newThesisJson.get("motivation"));
        assertEquals(SampleThesis.task, newThesisJson.get("task"));
        assertEquals(SampleThesis.form.getQuestions(), newThesisJson.get("questions"));
//        assertEquals(SampleSupervisor.supervisorObject().toString(), newThesisJson.get("supervisor")); TODO
//        assertEquals(SampleStudent.degrees, newThesisJson.get("degrees"));
//        assertEquals(SampleThesis.images.stream().toArray()[0],
//                newThesisJson.getJSONArray("images").getJSONArray(0).toString());

        assertEquals(SampleSupervisor.authHeader, request.getHeader("Authorization"));
    }

    @Test
    public void uploadThesisTestFail() throws JSONException, InterruptedException,
            ExecutionException {
        MockResponse responseUpload = new MockResponse().setResponseCode(500);
        server.enqueue(responseUpload);

        // Set current user
        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        Thesis thesis = SampleThesis.thesisObject();

        CompletableFuture<Result> resultThesisUpload =
                thesisApiService.createNewThesisFuture(thesis);

        assertFalse(resultThesisUpload.get().getSuccess());
    }
//
//
//
//    //Important
//    @Test
//    public void getThesesStack() {
//
//    }
//
//    @Test
//    public void getThesesStackFail() {
//
//    }
//
    @Test
    public void deleteThesis() throws InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        CompletableFuture<Result> resultCompletableFuture =
                thesisApiService.deleteThesisFuture(SampleThesis.id);

        RecordedRequest request = server.takeRequest();

        assertEquals(SampleThesis.id.toString(),
                Objects.requireNonNull(request.getRequestUrl()).encodedPathSegments().get(1));
        assertTrue(resultCompletableFuture.get().getSuccess());


        assertEquals(SampleSupervisor.authHeader, request.getHeader("Authorization"));
    }

    @Test
    public void deleteThesisFail() throws InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        CompletableFuture<Result> resultCompletableFuture =
                thesisApiService.deleteThesisFuture(new UUID(32, 32));

        assertFalse(resultCompletableFuture.get().getSuccess());
    }

    @Test
    public void deleteThesisFuture() throws InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        UUID uuid = new UUID(0x1234abcdL, 0x1234abcdL);

        CompletableFuture<Result> result =
                thesisApiService.deleteThesisFuture(uuid);

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        assertTrue(result.get().getSuccess());
        assertEquals(SampleSupervisor.authHeader, authToken);
        assertEquals(uuid.toString(),
                Objects.requireNonNull(request.getRequestUrl()).encodedPathSegments().get(1));
    }

//    @Test
//    public void deleteThesisFutureFail() throws InterruptedException, ExecutionException {
//        //Set the user
//        UserRepository.getInstance().setUser(supervisor);
//        UserRepository.getInstance().setPassword(supervisor.getPassword());
//        //actual deleteThesis mock test
//        MockResponse response = new MockResponse().setResponseCode(500);
//        server.enqueue(response);
//        CompletableFuture<Result> result = thesisApiService.deleteThesisFuture(new UUID(32,31));
//        server.takeRequest();
//        assertFalse(result.get().getSuccess());
//    }


}
