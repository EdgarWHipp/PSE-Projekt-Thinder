package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.Supervisor;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.remote.UsersRemoteDataSource;
import com.hfad.thinder.data.source.remote.okhttp.ApiUtils;
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


}
