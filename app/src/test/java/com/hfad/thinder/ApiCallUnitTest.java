package com.hfad.thinder;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.filter.log.RequestLoggingFilter.with;

import org.junit.Test;

import io.restassured.RestAssured;

public class ApiCallUnitTest {
  @Test
  public void post_a_user() {
    given().params("password","example@gmail.com",1,"max","mustermann","KIT").
            when().
            post("/api/Users").
            then().
            body("user.firstName", equalTo("max")).
            body("user.lastName", equalTo("mustermann")).
            body("user.eMail", equalTo("example@gmail.com")).
            body("user.id", equalTo("1")).
            body("user.university", equalTo("KIT")).
            body("user.password", equalTo("password"));
  }
  @Test
  public void check_hello() {
    when().get("/hello").then().assertThat().body("",equalTo("Hello, Gandalf"));
  }
  @Test
  public void post_a_thesis() {
    given().params("1","Telematik Arbeit","body of this sample thesis",null,null).
            when().
            post("/api/Theses").
            then().
            body("thesis.id", equalTo("1")).
            body("thesis.name", equalTo("Telematik Arbeit")).
            body("thesis.body", equalTo("body of this sample thesis")).
            body("thesis.form", equalTo(null)).
            body("thesis.imageList", equalTo(null));

  }
  @Test
  public void fixed_thesis_test() {
    when().
            get("/api/thesisTest/{thesisId}", 1).
            then(). statusCode(200).
            body("thesis.id", equalTo("1")).
            body("thesis.name", equalTo("Telematik Arbeit")).
            body("thesis.body", equalTo("body of this sample thesis")).
            body("thesis.form", equalTo(null)).
            body("thesis.imageList", equalTo(null));

  }

}
