package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.UserCreation;
import com.hfad.thinder.data.source.remote.StudentRemoteDataSource;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleStudent;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleSupervisor;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleUser;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

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

    @Test
    public void extendUserToStudentSuccess(){
        //set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(200);
        server.enqueue(response);
        Result result = studentRemoteDataSource.extendUserToStudent(SampleStudent.degrees,SampleStudent.firstName,SampleStudent.lastName);
        Assert.assertTrue(result.getSuccess());
        Assert.assertEquals(((Student)UserRepository.getInstance().getUser()).getDegrees(),SampleStudent.degrees);
        Assert.assertEquals(((Student)UserRepository.getInstance().getUser()).getFirstName(),SampleStudent.firstName);
        Assert.assertEquals(((Student)UserRepository.getInstance().getUser()).getLastName(),SampleStudent.lastName);
    }
    @Test
    public void extendUserToStudentFail(){
//set a user
        UserRepository.getInstance().setUser(SampleUser.emptyStudent());
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        MockResponse response = new MockResponse().setResponseCode(500);
        server.enqueue(response);
        Result result = studentRemoteDataSource.extendUserToStudent(SampleStudent.degrees,SampleStudent.firstName,SampleStudent.lastName);
        Assert.assertFalse(result.getSuccess());
        Assert.assertEquals(((Student)UserRepository.getInstance().getUser()).getDegrees(),SampleStudent.degrees);
        Assert.assertEquals(((Student)UserRepository.getInstance().getUser()).getFirstName(),SampleStudent.firstName);
        Assert.assertEquals(((Student)UserRepository.getInstance().getUser()).getLastName(),SampleStudent.lastName);
    }
}
