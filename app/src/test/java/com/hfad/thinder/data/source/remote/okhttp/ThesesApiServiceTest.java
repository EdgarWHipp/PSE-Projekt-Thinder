package com.hfad.thinder.data.source.remote.okhttp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.source.remote.okhttp.ApiUtils;
import com.hfad.thinder.data.source.remote.okhttp.ThesesApiService;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public void createNewThesisFutureSuccess() throws JSONException, InterruptedException,
            ExecutionException {
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
        Assert.assertEquals(SampleThesis.name, newThesisJson.get("name"));
        Assert.assertEquals(SampleThesis.supervisingProfessor, newThesisJson.get("supervisingProfessor"));
        Assert.assertEquals(SampleThesis.motivation, newThesisJson.get("motivation"));
        Assert.assertEquals(SampleThesis.task, newThesisJson.get("task"));
        Assert.assertEquals(SampleThesis.form.getQuestions(), newThesisJson.get("questions"));
        assertEquals(
                new JSONObject()
                        .put("id", SampleThesis.supervisor.getId().toString())
                        .put("type", SampleThesis.supervisor.getType().toString()).toString(),
                newThesisJson.get("supervisor").toString());
        assertEquals(new JSONArray().toString(), newThesisJson.get("possibleDegrees").toString());
//        assertEquals(new JSONArray().toString(), newThesisJson.getJSONArray("images").toString()); // TODO: properly test this

        Assert.assertEquals(SampleSupervisor.authHeader, request.getHeader("Authorization"));
    }

    @Test
    public void createNewThesisFutureFail() throws JSONException, InterruptedException,
            ExecutionException {
        MockResponse responseUpload = new MockResponse().setResponseCode(500);
        server.enqueue(responseUpload);

        // Set current user
        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        Thesis thesis = SampleThesis.thesisObject();

        CompletableFuture<Result> resultThesisUpload =
                thesisApiService.createNewThesisFuture(thesis);
        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(SampleUser.authHeader(), authToken);
        assertFalse(resultThesisUpload.get().getSuccess());
    }

    @Test
    public void getSpecificThesisFutureSuccess() throws InterruptedException, ExecutionException,
            JSONException {


        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setBody(SampleThesis.getThesisJson().toString());
        server.enqueue(response);

        UserRepository.getInstance().setUser(SampleStudent.studentObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        UUID uuid = new UUID(0x1234abcdL, 0x1234abcdL);

        Pair<CompletableFuture<Thesis>, CompletableFuture<Result>> result =
                thesisApiService.getSpecificThesisFuture(uuid);

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        assertTrue(result.getSecond().get().getSuccess());
        Assert.assertEquals(SampleSupervisor.authHeader, authToken);
        Assert.assertEquals(SampleThesis.form.getQuestions(),
                Objects.requireNonNull(result.getFirst().get().getForm()).getQuestions());
        Assert.assertEquals(SampleThesis.task, result.getFirst().get().getTask());
        Assert.assertEquals(SampleThesis.id.toString(), result.getFirst().get().getId().toString());
        Assert.assertEquals(SampleThesis.motivation, result.getFirst().get().getMotivation());
        Assert.assertEquals(SampleThesis.name, result.getFirst().get().getName());
        Assert.assertEquals(SampleThesis.supervisingProfessor,
                result.getFirst().get().getSupervisingProfessor());
        Assert.assertEquals(SampleThesis.supervisor.getFirstName(),
                result.getFirst().get().getSupervisor().getFirstName());

    }

    @Test
    public void deleteThesisFutureSuccess() throws InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserRepository.getInstance().setUser(SampleSupervisor.supervisorObject());
        UserRepository.getInstance().setPassword(SampleSupervisor.password);

        UUID uuid = new UUID(0x1234abcdL, 0x1234abcdL);

        CompletableFuture<Result> result =
                thesisApiService.deleteThesisFuture(uuid);

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");


        // Correct auth header set in request
        Assert.assertEquals(SampleUser.authHeader(), authToken);

        assertTrue(result.get().getSuccess());
        Assert.assertEquals(SampleSupervisor.authHeader, authToken);
        assertEquals(uuid.toString(),
                Objects.requireNonNull(request.getRequestUrl()).encodedPathSegments().get(1));
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


}
