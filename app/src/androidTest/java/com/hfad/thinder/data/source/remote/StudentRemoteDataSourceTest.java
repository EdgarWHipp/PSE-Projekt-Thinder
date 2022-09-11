package com.hfad.thinder.data.source.remote;

import android.util.Log;

import com.hfad.thinder.data.model.Form;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.okhttp.ApiUtils;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleStudent;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleUser;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Pair;
import com.hfad.thinder.data.source.result.Result;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * The RemoteDataSourceTests can simply test if the Result is correct.
 */
public class StudentRemoteDataSourceTest {
    private MockWebServer server;
    private StudentRemoteDataSource studentRemoteDataSource;
    private ApiUtils apiUtils;

    @Before
    public void setUp() {
        server = new MockWebServer();

        studentRemoteDataSource = new StudentRemoteDataSource();
        apiUtils = ApiUtils.getInstance();

        apiUtils.setLiveSetup(false);
        apiUtils.setHost(server.getHostName());
        apiUtils.setPort(server.getPort());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
/*
    @Test
    public void extendUserToStudentSuccess() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        MockResponse response2 = new MockResponse().setResponseCode(200);
        server.enqueue(response2);
        Result result = studentRemoteDataSource.extendUserToStudent(SampleStudent.degrees, SampleStudent.firstName, SampleStudent.lastName);
        Assert.assertTrue(result.getSuccess());
        Assert.assertEquals(((Student) UserRepository.getInstance().getUser()).getDegrees(), SampleStudent.degrees);
        Assert.assertEquals(UserRepository.getInstance().getUser().getFirstName(), SampleStudent.firstName);
        Assert.assertEquals(UserRepository.getInstance().getUser().getLastName(), SampleStudent.lastName);
    }

    @Test
    public void extendUserToStudentFail() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Result result = studentRemoteDataSource.extendUserToStudent(SampleStudent.degrees, SampleStudent.firstName, SampleStudent.lastName);
        Assert.assertFalse(result.getSuccess());
        Assert.assertEquals(((Student) UserRepository.getInstance().getUser()).getDegrees(), SampleStudent.degrees);
        Assert.assertEquals(UserRepository.getInstance().getUser().getFirstName(), SampleStudent.firstName);
        Assert.assertEquals(UserRepository.getInstance().getUser().getLastName(), SampleStudent.lastName);
    }
*/
    @Test
    public void rateThesisSuccess() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Collection<Pair<UUID, Boolean>> ratings = new ArrayList<Pair<UUID, Boolean>>();
        ratings.add(new Pair<UUID, Boolean>(new UUID(32, 32), false));
        Result result = studentRemoteDataSource.rateThesis(ratings);
        Assert.assertTrue(result.getSuccess());
    }

    @Test
    public void rateThesisFail() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Collection<Pair<UUID, Boolean>> ratings = new ArrayList<Pair<UUID, Boolean>>();
        ratings.add(new Pair<UUID, Boolean>(new UUID(32, 32), false));
        Result result = studentRemoteDataSource.rateThesis(ratings);
        Assert.assertFalse(result.getSuccess());
    }

    @Test
    public void getAllLikedThesesFromAStudentSuccess() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Result result = studentRemoteDataSource.getAllLikedThesesFromAStudent();
        Assert.assertTrue(result.getSuccess());
    }

    @Test
    public void getAllLikedThesesFromAStudentFail() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Result result = studentRemoteDataSource.getAllLikedThesesFromAStudent();
        Assert.assertFalse(result.getSuccess());
    }

    @Test
    public void getAllThesisForAStudentSuccess() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Result result = studentRemoteDataSource.getAllThesisForAStudent();
        Assert.assertTrue(result.getSuccess());
    }

    @Test
    public void getAllThesisForAStudentFail() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Result result = studentRemoteDataSource.getAllThesisForAStudent();
        Assert.assertFalse(result.getSuccess());
    }

    @Test
    public void sendTheFormToTheSupervisorSuccess() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Form form = new Form("question?", "answers");
        Result result = studentRemoteDataSource.sendTheFormToTheSupervisor(form, new UUID(32, 32));
        Assert.assertTrue(result.getSuccess());
    }

    @Test
    public void sendTheFormToTheSupervisorFail() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Form form = new Form("question?", "answers");
        Result result = studentRemoteDataSource.sendTheFormToTheSupervisor(form, new UUID(32, 32));
        Assert.assertFalse(result.getSuccess());
    }

    @Test
    public void removeLikedThesisFromStudentSuccess() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Result result = studentRemoteDataSource.removeLikedThesisFromStudent(new UUID(32, 32));
        Assert.assertTrue(result.getSuccess());
    }

    @Test
    public void removeLikedThesisFromStudentFail() {
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Result result = studentRemoteDataSource.removeLikedThesisFromStudent(new UUID(32, 32));
        Assert.assertFalse(result.getSuccess());
    }
}
