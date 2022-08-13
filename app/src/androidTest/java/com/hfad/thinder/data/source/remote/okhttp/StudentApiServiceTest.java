package com.hfad.thinder.data.source.remote.okhttp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class StudentApiServiceTest {
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private MockWebServer server;
    private StudentApiService studentApiService;
    private Login login;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        login = new Login("mail@gmail.com", "password");

        studentApiService = new StudentApiService();
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

        Degree degreeInformatik = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", degreesOld, true);

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setUser(student);
        userRepository.setPassword(student.getPassword());
        Degree degreeMathe = new Degree("Mathematik Msc", new UUID(32, 31));
        ArrayList<Degree> degreesNew = new ArrayList<>();
        degreesNew.add(degreeMathe);

        CompletableFuture<Result> result = studentApiService
                .editStudentProfileFuture(degreesNew, "Tom", "Müller");

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        String body = request.getBody().toString();
        assertTrue(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertEquals(UserRepository.getInstance().getUser().getFirstName(), "Tom");
        assertEquals(((Student) UserRepository.getInstance().getUser()).getDegrees(), degreesNew);
        assertEquals(UserRepository.getInstance().getUser().getLastName(), "Müller");
    }

    @Test
    public void rateThesisFuture() throws JSONException, InterruptedException, ExecutionException {
        Degree degreeInformatik = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", degreesOld, true);
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //Set Password
        UserRepository.getInstance().setPassword(student.getPassword());
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Collection<Pair<UUID, Boolean>> ratings = new ArrayList<Pair<UUID, Boolean>>();
        ratings.add(new Pair<>(new UUID(32, 32), true));
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
        Degree degreeInformatik = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", degreesOld, false);
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword(student.getPassword());
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Collection<Pair<UUID, Boolean>> ratings = new ArrayList<>();
        ratings.add(new Pair<>(new UUID(32, 32), true));
        CompletableFuture<Result> result = studentApiService.rateThesisFuture(ratings);
        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        String body = request.getBody().toString();

        assertFalse(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
    }
    //Erst wenn bei den Thesis alles steht!
    @Test
    public void getRateableThesis() throws ExecutionException, InterruptedException {
        //Set user
        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", null, false);
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword(student.getPassword());


        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Pair<CompletableFuture<ArrayList<Thesis>>, CompletableFuture<Result>> values = studentApiService.getAllThesesForTheStudentFuture();
        ArrayList<Thesis> theses= values.getFirst().get();
        assertEquals(theses, ThesisRepository.getInstance().getAllSwipeableTheses());
        Log.e("",ThesisRepository.getInstance().getAllSwipeableTheses().toString());
        assertTrue(ThesisRepository.getInstance().getAllSwipeableTheses().contains(theses));
        RecordedRequest request = server.takeRequest();
        Log.e("",request.getBody().toString());
    }

    @Test
    public void getRateableThesisFail() {
        //Set user
        Student student = new Student(USERTYPE.STUDENT,
                new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                true, new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L),
                "password", "mail@gmail.com", "Olf", "Molf", null, false);
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword(student.getPassword());

        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

    }


    @Test
    public void getLikedThesesFuture() {

    }
}