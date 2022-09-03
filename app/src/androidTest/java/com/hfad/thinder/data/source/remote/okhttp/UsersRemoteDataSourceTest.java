package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.source.remote.DegreeRemoteDataSource;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleStudent;
import com.hfad.thinder.data.source.remote.okhttp.Utils.SampleSupervisor;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockWebServer;

public class UsersRemoteDataSourceTest {

    private MockWebServer server;
    private UsersRemoteDataSource usersRemoteDataSource;
    private ApiUtils apiUtils;
    @Before
    public void setUp() {
        server = new MockWebServer();

        usersRemoteDataSource = new UsersRemoteDataSource();
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
    public void loginStudentSuccess(){
        //set the user (as done during registration)
        Student student = SampleStudent.studentObject();
        UserRepository.getInstance().setUser(student);
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.STUDENT);
        Login login = SampleStudent.getLogin();
        Result result = usersRemoteDataSource.login(login);
        Assert.assertFalse(result.getSuccess());
    }
    @Test
    public void loginSupervisorSuccess(){
        Supervisor supervisor= SampleSupervisor.supervisorObject();
        UserRepository.getInstance().setUser(supervisor);
        UserRepository.getInstance().setPassword("password");
        UserRepository.getInstance().setType(USERTYPE.SUPERVISOR);
        Login login = SampleSupervisor.getLogin();
        Result result = usersRemoteDataSource.login(login);
        Assert.assertTrue(result.getSuccess());
    }

}
