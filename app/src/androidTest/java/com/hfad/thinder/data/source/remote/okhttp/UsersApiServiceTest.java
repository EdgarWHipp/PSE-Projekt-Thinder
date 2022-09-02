package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleStudent;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleSupervisor;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleUser;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;
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
        apiUtils.setHost(server.getHostName());
        apiUtils.setPort(server.getPort());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testLoginStudentSuccess() throws JSONException, InterruptedException,
            ExecutionException, IOException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(SampleStudent.getStudentJson().toString());
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.getUserDetails(SampleUser.getLogin());

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(SampleUser.authHeader(), authToken);

        Assert.assertTrue(result.get().getSuccess());

        UserRepository userRepository = UserRepository.getInstance();
        Student student = (Student) userRepository.getUser();

        // TODO
        //Assert.assertEquals(UserJson.studentObject(), student);



        // User data
        Assert.assertEquals(USERTYPE.STUDENT, student.getType());
        Assert.assertTrue(student.isActive());
        Assert.assertEquals(SampleUser.id.toString(), student.getId().toString());
        Assert.assertEquals(SampleUser.firstName, student.getFirstName());
        Assert.assertEquals(SampleUser.lastName, student.getLastName());
        Assert.assertEquals(SampleUser.mail, student.getMail());
        Assert.assertEquals(SampleUser.uni_id.toString(), student.getUniversityId().toString());

        // Student data
        Assert.assertEquals(SampleStudent.degrees, student.getDegrees());
    }

    @Test
    public void testLoginupervisorSuccessS() throws JSONException, InterruptedException,
            ExecutionException, IOException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(SampleSupervisor.getSupervisorJson().toString());
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setType(USERTYPE.SUPERVISOR);

        CompletableFuture<Result> result = usersApiService.getUserDetails(SampleUser.getLogin());

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(SampleUser.authHeader(), authToken);

        Assert.assertTrue(result.get().getSuccess());

        Supervisor supervisor = (Supervisor) userRepository.getUser();

        // TODO 1 assert per test

        // User data
        Assert.assertEquals(USERTYPE.SUPERVISOR, supervisor.getType());
        Assert.assertTrue(supervisor.isActive());
        Assert.assertEquals(SampleUser.id.toString(), supervisor.getId().toString());
        Assert.assertEquals(SampleUser.firstName, supervisor.getFirstName());
        Assert.assertEquals(SampleUser.lastName, supervisor.getLastName());
        Assert.assertEquals(SampleUser.mail, supervisor.getMail());
        Assert.assertEquals(SampleUser.uni_id.toString(), supervisor.getUniversityId().toString());

        // Supervisor data
        Assert.assertEquals(SampleSupervisor.academicDegree, supervisor.getAcademicDegree());
        Assert.assertEquals(SampleSupervisor.building, supervisor.getBuilding());
        Assert.assertEquals(SampleSupervisor.officeNumber, supervisor.getOfficeNumber());
        Assert.assertEquals(SampleSupervisor.institute, supervisor.getInstitute());
        Assert.assertEquals(SampleSupervisor.phoneNumber, supervisor.getPhoneNumber());
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
    public void testCreateNewUserFutureSuccess() throws Exception {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserCreation newUser = SampleUser.userCreation();

        CompletableFuture<Result> result = usersApiService.createNewUserFuture(newUser);

        RecordedRequest request = server.takeRequest();

        // Parse request body to Json
        String requestBody = request.getBody().readUtf8();
        JSONObject newUserJson = new JSONObject(requestBody);

        // Check if status 200 response is handled properly
        Assert.assertTrue(result.get().getSuccess());

        // Check request body parameters
        Assert.assertEquals(SampleUser.firstName, newUserJson.get("firstName").toString());
        Assert.assertEquals(SampleUser.lastName, newUserJson.get("lastName").toString());
        Assert.assertEquals(SampleUser.mail, newUserJson.get("mail").toString());
        Assert.assertEquals(SampleUser.password, newUserJson.get("password").toString());
    }

    @Test
    public void testCreateNewUserFutureFail() throws Exception {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        UserCreation newUser = SampleUser.userCreation();

        CompletableFuture<Result> result = usersApiService.createNewUserFuture(newUser);

        // Check if status 500 response is handled properly
        Assert.assertFalse(result.get().getSuccess());
    }

    @Test
    public void testDeleteUserFutureSuccess() throws IOException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setUser(SampleStudent.studentObject());
        userRepository.setPassword(SampleStudent.password);


        CompletableFuture<Result> result = usersApiService.deleteUserFuture();

        RecordedRequest request = server.takeRequest();

        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        Assert.assertEquals(SampleUser.authHeader(), authToken);

        // Check if status 200 response is handled properly
        Assert.assertTrue(result.get().getSuccess());
    }

    @Test
    public void testResetPasswordSuccess() throws InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.resetPassword(SampleUser.mail);

        RecordedRequest request = server.takeRequest();

        String mail = Objects.requireNonNull(request.getRequestUrl()).queryParameter("mail");

        // Correct mail set
        Assert.assertEquals(SampleUser.mail, mail);

        // Check if status 200 response is handled properly
        Assert.assertTrue(result.get().getSuccess());
    }
    @Test
    public void testResetPasswordFail() throws InterruptedException, ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.resetPassword(SampleUser.mail);

        RecordedRequest request = server.takeRequest();

        String mail = Objects.requireNonNull(request.getRequestUrl()).queryParameter("mail");



        // Check if status 200 response is handled properly
        Assert.assertFalse(result.get().getSuccess());
    }

    @Test
    public void testPostNewPasswordSuccess() throws JSONException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.postNewPassword("xyztoken",
                SampleUser.password);

        RecordedRequest request = server.takeRequest();

        String requestBody = request.getBody().readUtf8();
        JSONObject requestBodyJson = new JSONObject(requestBody);

        // Correct mail set
        Assert.assertEquals(SampleUser.password, requestBodyJson.get("newPassword"));
        Assert.assertEquals("xyztoken", requestBodyJson.get("token"));


        // Check if status 200 response is handled properly
        Assert.assertTrue(result.get().getSuccess());
    }
    @Test
    public void testPostNewPasswordFail() throws JSONException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.postNewPassword("xyztoken",
                SampleUser.password);

        RecordedRequest request = server.takeRequest();

        String requestBody = request.getBody().readUtf8();
        JSONObject requestBodyJson = new JSONObject(requestBody);

        // Correct mail set
        Assert.assertEquals(SampleUser.password, requestBodyJson.get("newPassword"));
        Assert.assertEquals("xyztoken", requestBodyJson.get("token"));


        // Check if status 200 response is handled properly
        Assert.assertFalse(result.get().getSuccess());
    }
}