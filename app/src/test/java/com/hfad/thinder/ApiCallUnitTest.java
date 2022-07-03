package com.hfad.thinder;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.filter.log.RequestLoggingFilter.with;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class ApiCallUnitTest {
  private String localHost="http://localhost:8080/";
  @Test
  public void post_a_user() {
    given().params("password","example@gmail.com",1,"max","mustermann","KIT").
            when().
            post(localHost+"/api/Users").
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
    String bodyAsString = RestAssured.get(localHost+"/hello").body().asString();
    Assert.assertEquals(bodyAsString.contains("Hello, Gandalf"),true);

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
  public void fixed_thesis_test() throws JSONException {
    JSONObject supervisor = new JSONObject()
            .put("academicDegree","Computer Science Bachelor")
            .put("building","101.123")
            .put("officeNumber","123")
            .put("institute","Anthropomatik und Robotik")
            .put("phoneNumber","12345678")
            .put("theses","null");

    JSONObject thesis = new JSONObject()
            .put("id","123")
            .put("name","exampleThesis")
            .put("description","text")
            .put("questionForm","text")
            .put("supervisor",supervisor)
            .put("studentRatings","null")
            .put("images","null")
            .put("possibleDegrees","Computer Science Bachelor");
    given()
            .port(8080)
            .body(supervisor.toString())
            .when()
            .post(localHost+"users").
            then(). statusCode(200);
    given()
            .port(8080)
            .body(thesis.toString())
            .when()
            .post(localHost+"theses").
            then(). statusCode(200);

  }
  @Test
  public void test_live_server_basic() {
    String bodyAsString = RestAssured.get("https://thinder-api.herokuapp.com/helloOpen").body().asString();
    Assert.assertEquals(bodyAsString.contains("Hello world!"),true);

  }
  @Test
  public void test_post_a_uni_and_one_user() throws JSONException {
    JSONObject universityJson =new JSONObject()
            .put("name","KIT")
            .put("members","null")
            .put("studentMailRegex",".@student.kit.edu")
            .put("supervisorMailRegex",".@kit.edu");

    JSONObject userJson = new JSONObject()
            .put("firstName","max")
            .put("lastName","mustermann")
            .put("password","testpw")
            .put("mail","max@student.kit.edu");
    given()
            .port(8080) // port number
            .body(universityJson.toString())   // use jsonObj toString method
            .when()
            .post("/university")
            .then()
            .statusCode(200);
    given()
            .port(8080)
            .body(userJson.toString())
            .when()
            .post("/users")
            .then()
            .statusCode(200);



  }

}
