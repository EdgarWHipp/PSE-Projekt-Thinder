package com.hfad.thinder.data.source.remote.okhttp;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Image;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class UsersApiServiceTest extends TestCase {
    private MockWebServer server;
    private UsersApiService usersApiService;
    private Login login;

    @Before
    public void setUp() {
        server = new MockWebServer();
        login = new Login("mail@gmail.com", "password");

        usersApiService =  new UsersApiService();
        usersApiService.setHost(server.getHostName());
        usersApiService.setPort(server.getPort());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testGetUserRoleSuccess() throws ExecutionException, InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody("\"STUDENT\"");
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.getUserRole(login);

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertTrue(result.get().getSuccess());
    }

    @Test
    public void testGetUserRoleFailure() throws ExecutionException, InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(404);
        server.enqueue(response);

        CompletableFuture<Result> result = usersApiService.getUserRole(login);

        assertFalse(result.get().getSuccess());
    }

    @Test
    public void testLoginSuccessStudent() throws JSONException, InterruptedException,
            ExecutionException, IOException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(UserJson.getStudentJson().toString());
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setType(USERTYPE.STUDENT);

        CompletableFuture<Result> result = usersApiService.getUserDetails(login);


        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertTrue(result.get().getSuccess());

        Student student = (Student) userRepository.getUser();

        // User data
        assertEquals(USERTYPE.STUDENT, student.getRole());
        assertTrue(student.isActive());
        assertEquals(UserJson.id, student.getId().toString());
        assertEquals(UserJson.firstName, student.getFirstName());
        assertEquals(UserJson.lastName, student.getLastName());
        assertEquals(UserJson.password, student.getPassword());
        assertEquals(UserJson.mail, student.getMail());
        assertEquals(UserJson.uni_id, student.getUniversityId().toString());

        // Student data
        assertEquals(UserJson.degrees, student.getDegree());
    }

    @Test
    public void testLoginSuccessSupervisor() throws JSONException, InterruptedException,
            ExecutionException, IOException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(UserJson.getSupervisorJson().toString());
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setType(USERTYPE.SUPERVISOR);

        CompletableFuture<Result> result = usersApiService.getUserDetails(login);

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertTrue(result.get().getSuccess());

        Supervisor supervisor = (Supervisor) userRepository.getUser();

        // User data
        assertEquals(USERTYPE.SUPERVISOR, supervisor.getRole());
        assertTrue(supervisor.isActive());
        assertEquals(UserJson.id, supervisor.getId().toString());
        assertEquals(UserJson.firstName, supervisor.getFirstName());
        assertEquals(UserJson.lastName, supervisor.getLastName());
        assertEquals(UserJson.password, supervisor.getPassword());
        assertEquals(UserJson.mail, supervisor.getMail());
        assertEquals(UserJson.uni_id, supervisor.getUniversityId().toString());

        // Supervisor data
        assertEquals(UserJson.academicDegree, supervisor.getAcademicDegree());
        assertEquals(UserJson.building, supervisor.getBuilding());
        assertEquals(UserJson.officeNumber, supervisor.getOfficeNumber());
        assertEquals(UserJson.institute, supervisor.getInstitute());
        assertEquals(UserJson.phoneNumber, supervisor.getPhoneNumber());
    }

    public void testVerifyFuture() {
    }

    @Ignore
    @Test
    public void testCreateNewUserFuture() throws Exception {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        String url = server.url("/users").toString();

        UserCreation user = new UserCreation("tom", "mu", "x@y.de","asdf");
        CompletableFuture<Result> result = usersApiService.createNewUserFuture(user);
        RecordedRequest request = server.takeRequest();
        assertTrue(result.get().getSuccess());
    }

    @Ignore
    @Test
    public void testCreateNewUserFutureFail() throws Exception {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        String url = server.url("/users").toString();

        UserCreation user = new UserCreation("tom", "mu", "x@y.de","asdf");
        CompletableFuture<Result> result = usersApiService.createNewUserFuture(user);
        RecordedRequest request = server.takeRequest();
        assertFalse(result.get().getSuccess());
    }



    @Test
    public void testDeleteUserFutureAfterlogin() throws IOException, JSONException, ExecutionException, InterruptedException, TimeoutException {
        MockResponse response = new MockResponse().setResponseCode(200);
        response.setBody(UserJson.getSupervisorJson().toString());
        server.enqueue(response);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setType(USERTYPE.SUPERVISOR);

        CompletableFuture<Result> result = usersApiService.getUserDetails(login);

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");

        // Correct auth header set in request
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertTrue(result.get().getSuccess());

        Supervisor supervisor = (Supervisor) userRepository.getUser();

        // User data
        assertEquals(USERTYPE.SUPERVISOR, supervisor.getRole());
        assertTrue(supervisor.isActive());
        assertEquals(UserJson.id, supervisor.getId().toString());
        assertEquals(UserJson.firstName, supervisor.getFirstName());
        assertEquals(UserJson.lastName, supervisor.getLastName());
        assertEquals(UserJson.password, supervisor.getPassword());
        assertEquals(UserJson.mail, supervisor.getMail());
        assertEquals(UserJson.uni_id, supervisor.getUniversityId().toString());

        // Supervisor data
        assertEquals(UserJson.academicDegree, supervisor.getAcademicDegree());
        assertEquals(UserJson.officeNumber, supervisor.getOfficeNumber());
        assertEquals(UserJson.building, supervisor.getBuilding());
        assertEquals(UserJson.institute, supervisor.getInstitute());
        assertEquals(UserJson.phoneNumber, supervisor.getPhoneNumber());

        MockResponse responseDelete = new MockResponse().setResponseCode(200);
        response.setBody(""); //void returned from backend
        server.enqueue(response);
        CompletableFuture<Result> resultDelete = usersApiService.deleteUserFuture();
        assertEquals(resultDelete.get().getSuccess(),true);
    }

    public void swipeThesisWrite(){

    }

    public void testResetPasswordFuture() {
    }

    public void testSendNewPasswordFuture() {
    }

    public void testDeleteUserThesisCall() {
    }

    public void testGetUserThesisResponse() {
    }


    private static class UserJson {
        private static String roleStudent = "STUDENT";
        private static String roleSupervisor = "SUPERVISOR";
        private static String id = "d5acac7e-78d2-440d-953e-13132f3f0ccf";
        private static String firstName = "Olf";
        private static String lastName = "Rieffel";
        private static String password = "asdfasdf";
        private static String mail = "olf@gmail.com";
        private static Boolean active = true;
        private static String uni_id = "4e452928-5f57-4ddd-a5c9-36c5787b3afa";

        // Student
        private static Object degrees = null;

        // Supervisor
        private static String academicDegree = "M. Sc.";
        private static String officeNumber = "Room 102";
        private static String building = "Building 50.34";
        private static String institute = "Telematik";
        private static String phoneNumber = "0173 1234567";

        private static JSONObject getUserJson() throws JSONException {
            return new JSONObject()
                    .put("id", id)
                    .put("firstName",firstName)
                    .put("lastName", lastName)
                    .put("password",password)
                    .put("mail",mail)
                    .put("active",active)
                    .put("uni_id", uni_id);
        }

        private static JSONObject getStudentJson() throws JSONException {
            return getUserJson()
                    .put("role", roleStudent)
                    .put("degrees", degrees);
        }

        private static JSONObject getSupervisorJson() throws JSONException {
            return getUserJson()
                    .put("role", roleSupervisor)
                    .put("degree", academicDegree)
                    .put("officeNumber", officeNumber)
                    .put("building",building)
                    .put("institute", institute)
                    .put("phoneNumber", phoneNumber);
        }
    }
}