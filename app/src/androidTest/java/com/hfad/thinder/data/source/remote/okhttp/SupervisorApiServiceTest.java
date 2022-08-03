package com.hfad.thinder.data.source.remote.okhttp;

import com.hfad.thinder.data.model.Login;

import org.junit.After;
import org.junit.Before;

import okhttp3.mockwebserver.MockWebServer;



public class SupervisorApiServiceTest {
  private MockWebServer server;
  private SupervisorApiService supervisorApiService;
  @Before
  public void setUp() throws Exception {
    server = new MockWebServer();
    supervisorApiService =  new SupervisorApiService();
    supervisorApiService.setHost(server.getHostName());
    supervisorApiService.setPort(server.getPort());
  }

  @After
  public void tearDown() throws Exception {
    server.shutdown();
  }

}
