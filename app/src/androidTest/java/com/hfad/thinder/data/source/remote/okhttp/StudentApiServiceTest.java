package com.hfad.thinder.data.source.remote.okhttp;

import static org.junit.Assert.*;

import android.util.Log;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class StudentApiServiceTest {
    private MockWebServer server;
    private StudentApiService studentApiService;
    private Login login;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        login = new Login("mail@gmail.com", "password");

        studentApiService =  new StudentApiService();
        studentApiService.setHost(server.getHostName());
        studentApiService.setPort(server.getPort());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void editStudentProfileFuture() throws JSONException, IOException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        Degree degreeInformatik = new Degree("Informatik Bsc",new UUID(32,32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", degreesOld,true);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setType(USERTYPE.STUDENT);
        userRepository.setUser(student);

        Degree degreeMathe = new Degree("Mathematik Msc",new UUID(32,32));
        ArrayList<Degree> degreesNew = new ArrayList<>();
        degreesNew.add(degreeMathe);

        CompletableFuture<Result> result = studentApiService
                .editStudentProfileFuture(degreesNew, "Tom", "Müller");

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        String body = request.getBody().toString();
        System.out.println("This is the returned body:" + body);
        assertTrue(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertEquals(UserRepository.getInstance().getUser().getFirstName(),"Tom");
        assertEquals(((Student)UserRepository.getInstance().getUser()).getDegrees(),degreesNew);
        assertEquals(UserRepository.getInstance().getUser().getLastName(),"Müller");
        assertEquals(UserRepository.getInstance().getUser().getFirstName(),"Tom");
    }
    @Test
    public void rateThesisFuture() throws JSONException, InterruptedException, ExecutionException {
        //Set current loged in user
        Degree degreeInformatik = new Degree("Informatik Bsc",new UUID(32,32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", degreesOld,true);
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Collection<Pair<UUID,Boolean>> ratings =  new ArrayList<Pair<UUID,Boolean>>();
        ratings.add(new Pair<>(new UUID(32,32),true));
        CompletableFuture<Result> result = studentApiService.rateThesisFuture(
                ratings);
        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        String body = request.getBody().toString();

        assertTrue(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
    }
    @Test
    public void rateThesisFutureFail() throws JSONException, InterruptedException, ExecutionException {
        //Set current loged in user
        Degree degreeInformatik = new Degree("Informatik Bsc",new UUID(32,32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", degreesOld,false);
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Collection<Pair<UUID,Boolean>> ratings = new ArrayList<>();
        ratings.add(new Pair<>(new UUID(32,32),true));
        CompletableFuture<Result> result = studentApiService.rateThesisFuture(ratings);
        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        String body = request.getBody().toString();

        assertFalse(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
    }

    @Test
    public void getRateableThesis() {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Pair<CompletableFuture<ArrayList<Thesis>>,CompletableFuture<Result>> values= studentApiService.getAllThesesForTheStudentFuture();

    }
    @Test
    public void getRateableThesisFail() {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

    }



    @Test
    public void getLikedThesesFuture() {

    }
}