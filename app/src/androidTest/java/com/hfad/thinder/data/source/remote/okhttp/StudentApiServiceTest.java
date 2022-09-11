package com.hfad.thinder.data.source.remote.okhttp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Thesis;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleStudent;
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
import java.util.HashMap;
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
    private ApiUtils apiUtils;

    @Before
    public void setUp() {
        server = new MockWebServer();

        studentApiService = new StudentApiService();
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
    public void editStudentProfileFutureSuccess() throws JSONException, IOException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);

        Degree degreeCS = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeCS);

        Student student = SampleStudent.studentObject();

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setUser(student);
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        userRepository.setPassword("password");
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
    public void editStudentProfileFutureFail() throws JSONException, IOException, InterruptedException,
            ExecutionException {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);

        Degree degreeCS = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeCS);

        Student student = SampleStudent.studentObject();

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.setUser(student);
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        userRepository.setPassword("password");
        Degree degreeMathe = new Degree("Mathematik Msc", new UUID(32, 31));
        ArrayList<Degree> degreesNew = new ArrayList<>();
        degreesNew.add(degreeMathe);

        CompletableFuture<Result> result = studentApiService
                .editStudentProfileFuture(degreesNew, "Tom", "Müller");

        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        String body = request.getBody().toString();
        assertFalse(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
        assertNotEquals(UserRepository.getInstance().getUser().getFirstName(), "Tom");
        assertNotEquals(((Student) UserRepository.getInstance().getUser()).getDegrees(), degreesNew);
        assertNotEquals(UserRepository.getInstance().getUser().getLastName(), "Müller");
    }

    @Test
    public void rateThesisFutureSuccess() throws JSONException, InterruptedException, ExecutionException {
        Degree degreeInformatik = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //Set Password
        UserRepository.getInstance().setPassword("password");
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Collection<Pair<UUID, Boolean>> ratings = new ArrayList<Pair<UUID, Boolean>>();
        ratings.add(new Pair<>(new UUID(32, 32), true));
        CompletableFuture<Result> result = studentApiService.rateThesisFuture(
                ratings);
        RecordedRequest request = server.takeRequest();
        String authToken = request.getHeader("Authorization");
        assertTrue(result.get().getSuccess());
        assertEquals("Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=", authToken);
    }

    @Test
    public void rateThesisFutureFail() throws JSONException, InterruptedException, ExecutionException {
        //Set current loged in user
        Degree degreeInformatik = new Degree("Informatik Bsc", new UUID(32, 32));
        ArrayList<Degree> degreesOld = new ArrayList<>();
        degreesOld.add(degreeInformatik);

        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
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

    @Test
    public void getRateableThesisSuccess() throws ExecutionException, InterruptedException {
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Pair<CompletableFuture<ArrayList<Thesis>>, CompletableFuture<Result>> values = studentApiService.getAllThesesForTheStudentFuture();
        //assertEquals(theses, ThesisRepository.getInstance().getAllSwipeableTheses());
        //assertTrue(ThesisRepository.getInstance().getAllSwipeableTheses().contains(theses));
        assertTrue(values.getSecond().get().getSuccess());
    }

    @Test
    public void getRateableThesisFail() throws ExecutionException, InterruptedException {
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");

        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Pair<CompletableFuture<ArrayList<Thesis>>, CompletableFuture<Result>> values = studentApiService.getAllThesesForTheStudentFuture();
        ArrayList<Thesis> theses = values.getFirst().get();
        assertEquals(new ArrayList<>(), ThesisRepository.getInstance().getTheses());
        assertFalse(ThesisRepository.getInstance().getTheses().contains(theses));
        RecordedRequest request = server.takeRequest();
        assertFalse(values.getSecond().get().getSuccess());
    }


    @Test
    public void getAllLikedThesesSuccess() throws InterruptedException, ExecutionException {
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> values = studentApiService.getAllPositiveRatedThesesFuture();
        HashMap<UUID, Thesis> map = values.getFirst().get();
        assertEquals(map, null);
        Result result = values.getSecond().get();
        assertTrue(result.getSuccess());
        RecordedRequest request = server.takeRequest();

    }

    @Test
    public void getAllLikedThesesFail() throws ExecutionException, InterruptedException {
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Pair<CompletableFuture<HashMap<UUID, Thesis>>, CompletableFuture<Result>> result = studentApiService.getAllPositiveRatedThesesFuture();
        assertFalse(result.getSecond().get().getSuccess());
        RecordedRequest request = server.takeRequest();
    }

    @Test
    public void removeLikedThesisFail() throws ExecutionException, InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        CompletableFuture<Result> result = studentApiService.removeALikedThesisFromAStudentFuture(new UUID(32, 32));

        assertFalse(result.get().getSuccess());
        RecordedRequest request = server.takeRequest();
    }

    @Test
    public void removeLikedThesisSuccess() throws ExecutionException, InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        CompletableFuture<Result> result = studentApiService.removeALikedThesisFromAStudentFuture(new UUID(32, 32));

        assertTrue(result.get().getSuccess());
        RecordedRequest request = server.takeRequest();
    }

    @Test
    public void sendFormToSupervisorFail() throws JSONException, ExecutionException, InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        Form form = new Form("Wie geht es dir?", "gut");
        CompletableFuture<Result> result = studentApiService.sendThesisFormToSupervisorFuture(form, new UUID(32, 32));

        assertFalse(result.get().getSuccess());
        RecordedRequest request = server.takeRequest();
    }

    @Test
    public void sendFormToSupervisorSuccess() throws JSONException, ExecutionException, InterruptedException {
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        //Set user
        Student student = SampleStudent.studentObject();
        // Actual Thesis Rating call
        UserRepository.getInstance().setUser(student);
        //set password
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        Form form = new Form("Wie geht es dir?", "gut");
        CompletableFuture<Result> result = studentApiService.sendThesisFormToSupervisorFuture(form, new UUID(32, 32));

        assertTrue(result.get().getSuccess());
        RecordedRequest request = server.takeRequest();
    }


}