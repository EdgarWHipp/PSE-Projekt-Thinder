package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.okhttp.Utils.UserUtils;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class UsersApiServiceTest {
    private MockWebServer server;
    private UsersApiService usersApiService;
    private ApiUtils apiUtils;

    @Before
    public void setUp() {
        server = new MockWebServer();

        usersApiService = new UsersApiService();

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
    public void testLoginSuccessStudent() throws JSONException, InterruptedException,
            ExecutionException, IOException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(UserUtils.getStudentJson().toString());
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.getUserDetails(UserUtils.getLogin());

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(UserUtils.authHeader(), authToken);

        Assert.assertTrue(result.get().getSuccess());

        UserRepository userRepository = UserRepository.getInstance();
        Student student = (Student) userRepository.getUser();

        // TODO
        //Assert.assertEquals(UserJson.studentObject(), student);

        // User data
        Assert.assertEquals(USERTYPE.STUDENT, student.getType());
        Assert.assertTrue(student.isActive());
        Assert.assertEquals(UserUtils.id.toString(), student.getId().toString());
        Assert.assertEquals(UserUtils.firstName, student.getFirstName());
        Assert.assertEquals(UserUtils.lastName, student.getLastName());
        Assert.assertEquals(UserUtils.mail, student.getMail());
        Assert.assertEquals(UserUtils.uni_id.toString(), student.getUniversityId().toString());

        // Student data
        Assert.assertEquals(UserUtils.degrees, student.getDegrees());
    }

    @Test
    public void testLoginSuccessSupervisor() throws JSONException, InterruptedException,
            ExecutionException, IOException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(UserUtils.getSupervisorJson().toString());
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setType(USERTYPE.SUPERVISOR);

        CompletableFuture<Result> result = usersApiService.getUserDetails(UserUtils.getLogin());

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(UserUtils.authHeader(), authToken);

        Assert.assertTrue(result.get().getSuccess());

        Supervisor supervisor = (Supervisor) userRepository.getUser();

        // TODO 1 assert per test

        // User data
        Assert.assertEquals(USERTYPE.SUPERVISOR, supervisor.getType());
        Assert.assertTrue(supervisor.isActive());
        Assert.assertEquals(UserUtils.id.toString(), supervisor.getId().toString());
        Assert.assertEquals(UserUtils.firstName, supervisor.getFirstName());
        Assert.assertEquals(UserUtils.lastName, supervisor.getLastName());
        Assert.assertEquals(UserUtils.mail, supervisor.getMail());
        Assert.assertEquals(UserUtils.uni_id.toString(), supervisor.getUniversityId().toString());

        // Supervisor data
        Assert.assertEquals(UserUtils.academicDegree, supervisor.getAcademicDegree());
        Assert.assertEquals(UserUtils.building, supervisor.getBuilding());
        Assert.assertEquals(UserUtils.officeNumber, supervisor.getOfficeNumber());
        Assert.assertEquals(UserUtils.institute, supervisor.getInstitute());
        Assert.assertEquals(UserUtils.phoneNumber, supervisor.getPhoneNumber());
    }

    @Test
    public void testVerifyFuture() throws InterruptedException, JSONException, IOException,
            ExecutionException, TimeoutException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.verifyUser("testToken");

        RecordedRequest request = server.takeRequest();
        String verificationToken = request.getRequestUrl().queryParameter("token");

        // Correct auth header set in request
        Assert.assertTrue(result.get().getSuccess());
        Assert.assertEquals("testToken", verificationToken);
    }

    @Test
    public void testCreateNewUserFuture() throws Exception {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserCreation newUser = UserUtils.userCreation();

        CompletableFuture<Result> result = usersApiService.createNewUserFuture(newUser);

        RecordedRequest request = server.takeRequest();

        // Parse request body to Json
        String requestBody = request.getBody().readUtf8();
        JSONObject newUserJson = new JSONObject(requestBody);

        // Check if status 200 response is handled properly
        Assert.assertTrue(result.get().getSuccess());

        // Check request body parameters
        Assert.assertEquals(UserUtils.firstName, newUserJson.get("firstName").toString());
        Assert.assertEquals(UserUtils.lastName, newUserJson.get("lastName").toString());
        Assert.assertEquals(UserUtils.mail, newUserJson.get("mail").toString());
        Assert.assertEquals(UserUtils.password, newUserJson.get("password").toString());
    }

    @Test
    public void testCreateNewUserFutureFail() throws Exception {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        UserCreation newUser = UserUtils.userCreation();

        CompletableFuture<Result> result = usersApiService.createNewUserFuture(newUser);

        // Check if status 500 response is handled properly
        Assert.assertFalse(result.get().getSuccess());
    }

    @Test
    public void testDeleteUserFuture() throws IOException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setUser(UserUtils.studentObject());
        userRepository.setPassword(UserUtils.password);


        CompletableFuture<Result> result = usersApiService.deleteUserFuture();

        RecordedRequest request = server.takeRequest();

        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(UserUtils.authHeader(), authToken);

        // Check if status 200 response is handled properly
        Assert.assertTrue(result.get().getSuccess());
    }

    @Test
    public void testResetPasswordFuture() {
    }

    @Test
    public void testSendNewPasswordFuture() {
    }

    @Test
    public void testDeleteUserThesisCall() {
    }

    @Test
    public void testGetUserThesisResponse() {
    }
}