package com.hfad.thinder.data.source.remote.okhttp.Utils;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.UserCreation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class UserUtils {
    public static final String typeStudent = "STUDENT";
    public static final String typeSupervisor = "SUPERVISOR";
    public static final UUID id =
            new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L);
    public static final String firstName = "Olf";
    public static final String lastName = "Rieffel";
    public static final String password = "password";
    public static final String mail = "mail@gmail.com";
    public static final Boolean active = true;
    public static final UUID uni_id =
            new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4c314L);
    public static final Boolean complete = true;

    public static final String degreeName = "M. Sc. Informatik";
    public static final UUID degreeId =
            new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4d314L);

    public static final Degree degree = new Degree(degreeName, degreeId);
    public static final ArrayList<Degree> degrees =
            new ArrayList<>(Collections.singletonList(degree));

    // Supervisor
    public static final String academicDegree = "M. Sc.";
    public static final String officeNumber = "Room 102";
    public static final String building = "Building 50.34";
    public static final String institute = "Telematik";
    public static final String phoneNumber = "0173 1234567";

    // Auth header
    public static final String authHeader = "Basic bWFpbEBnbWFpbC5jb206cGFzc3dvcmQ=";

    public static JSONObject getUserJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("mail", mail)
                .put("active", active)
                .put("uni_id", uni_id);
    }

    public static Student studentObject() {
        return new Student(USERTYPE.STUDENT, id, active, uni_id, null, mail, firstName,
                lastName, degrees, complete);
    }

    public static UserCreation userCreation() {
        return new UserCreation(firstName, lastName, mail, password);
    }

    public static JSONObject getStudentJson() throws JSONException {
        degrees.get(0).setUniversityID(uni_id);
        return getUserJson()
                .put("type", typeStudent)
                .put("degrees", new JSONArray().put(new JSONObject()
                        .put("id", degreeId.toString())
                        .put("degree", degreeName)
                        .put("university_id", uni_id)
                ));
    }

    public static JSONObject getSupervisorJson() throws JSONException {
        return getUserJson()
                .put("type", typeSupervisor)
                .put("academicDegree", academicDegree)
                .put("officeNumber", officeNumber)
                .put("building", building)
                .put("institute", institute)
                .put("phoneNumber", phoneNumber);
    }

    public static String authHeader() {
        return authHeader;
    }

    public static Login getLogin() {
        return new Login(mail, password);
    }


}
