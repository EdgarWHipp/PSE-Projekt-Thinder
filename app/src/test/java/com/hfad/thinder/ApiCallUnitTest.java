package com.hfad.thinder;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.filter.log.RequestLoggingFilter.with;

import com.google.gson.JsonObject;
import com.hfad.thinder.data.source.repository.ThesisRepository;
import com.hfad.thinder.data.source.repository.UniversityRepository;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import okhttp3.HttpUrl;

public class ApiCallUnitTest {
  private String localHost="http://localhost:8080/";
  @Test
  public void post_a_student() {
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
  public void registrate_a_user_complete() {
    JSONObject user = new JSONObject();




    given().header("Content-type", "application/json")
            .port(8080)
            .body(user.toString())
            .when()
            .post(localHost+"users/verify").
            then(). statusCode(200);
  }
  @Test
  public void post_a_thesis_and_the_necessary_supervisor() throws JSONException {
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
    given().header("Content-type", "application/json")
            .port(8080)
            .body(supervisor.toString())
            .when()
            .post(localHost+"users").
            then(). statusCode(200);
    given().header("Content-type", "application/json")
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
  public void full_registration_including_posting_a_university() {
    //Result universityResult = UniversityRepository.getInstance().addUniversity("KSSIT",".*@student.kit.edu",".*@kit.edu");
   // Result resultUser = UserRepository.getInstance().registrate("edgar","hipp","password123","ux2x@student.kit.edu");
    //Post auf theses noch nicht implementiert -> Result thesisResult = ThesisRepository.getInstance().addThesis("thesisName","thesisBody",null,null,null,null);
    Result loginResult = UserRepository.getInstance().login("Klavierboy4!/","u22@kit.edu");
   //Assert.assertEquals(thesisResult.getSuccess() , true);
    //Assert.assertEquals(loginResult.getSuccess(),true);
   //Assert.assertEquals(universityResult.getSuccess() , true);
    System.out.print(loginResult.getErrorMessage());
   Assert.assertEquals(loginResult.getSuccess() , true);


  }

}
