package com.hfad.thinder;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.source.repository.UserRepository;
import com.hfad.thinder.data.source.result.Result;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import io.restassured.RestAssured;

public class ApiCallUnitTest {

    @Test
    public void test_live_server_basic() {
        String bodyAsString = RestAssured.get("https://thinder-api.herokuapp.com/helloOpen").body().asString();
        Assert.assertEquals(bodyAsString.contains("Hello world!"), true);
    }

    @Test
    public void full_registration_including_posting_a_university() throws InterruptedException {
        Result universityResult = UniversityRepository.getInstance().addUniversity("KIT", ".*@student.kit.edu", ".*@kit.edu");
        Result resultUser = UserRepository.getInstance().register("edgar", "hippp", "Klavierboy4!/", "uxmnx@student.kit.edu");
        System.out.print(resultUser.getErrorMessage());
        System.out.println(UserRepository.getInstance().getType());
        // Result loginResult = UserRepository.getInstance().login("Klavierboy4!/","uxmnx@student.kit.edu");
        System.out.println(UserRepository.getInstance().getType());
        Set<Degree> set = new HashSet<>();
        ;
        Result resultStudent = new Result(false);
        Result resultSupervisor = new Result(false);
        set.add(new Degree("informatik bachelor"));
        if (UserRepository.getInstance().getType().equals(USERTYPE.STUDENT)) {

            resultStudent = UserRepository.getInstance().editProfileStudent(set, "edgar wilhelm", "hipp");
        } else if (UserRepository.getInstance().getType().equals(USERTYPE.SUPERVISOR)) {
            //resultSupervisor = UserRepository.getInstance().editProfilSupervisor("bachelor","karlsruhe","Telematik","015234336652","edgar wilhelm","hipp");
        } else {

            Assert.fail();
        }


        Assert.assertEquals(resultStudent.getSuccess() || resultSupervisor.getSuccess(), true);
        //  Assert.assertEquals(loginResult.getSuccess(),true);
        Assert.assertEquals(universityResult.getSuccess(), true);


    }

    @Test
    public void create_a_university() {
        Result createResult = UniversityRepository.getInstance().addUniversity("KIT", ".*@student.kit.edu", ".*@kit.edu");
        Assert.assertEquals(createResult.getSuccess(), true);
    }

    @Test
    public void attempt_to_registrate_user_with_wrong_password_format() {

        Result registrationResult = UserRepository.getInstance().register("max", "mustermann", "a", "max@kit.edu");
        Assert.assertEquals(registrationResult.getSuccess(), false);

    }

    @Test
    public void attempt_to_registrate_user_with_correct_password_format_and_supervisor_mail() throws InterruptedException {
        Result registrationResult = UserRepository.getInstance().register("max", "mustermann", "Password123!", "max1@kit.edu");
        Thread.sleep(1000);
        System.out.print(registrationResult.getErrorMessage());
        Assert.assertEquals(registrationResult.getErrorMessage(), null);
        Assert.assertEquals(UserRepository.getInstance().getType() == USERTYPE.SUPERVISOR, true);
        Assert.assertEquals(registrationResult.getSuccess(), true);
    }

    @Test
    public void attempt_to_registrate_user_with_correct_password_format_and_student_mail() {
        Result registrationResult = UserRepository.getInstance().register("max", "mustermann", "Password123!", "max@student.kit.edu");
        Assert.assertEquals(registrationResult.getErrorMessage(), null);
        Assert.assertEquals(UserRepository.getInstance().getType() == USERTYPE.STUDENT, true);
        Assert.assertEquals(registrationResult.getSuccess(), true);

    }

    @Test
    public void login() throws JSONException, IOException {

        Result result = UserRepository.getInstance().verifyUser("52555b2a-fb88-46fd-a0cd-b04b313155c2");
        System.out.println(result.getErrorMessage());
    }
}
