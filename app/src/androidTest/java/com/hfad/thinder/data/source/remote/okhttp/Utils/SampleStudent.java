package com.hfad.thinder.data.source.remote.okhttp.Utils;

import com.hfad.thinder.data.model.Degree;
import com.hfad.thinder.data.model.Student;
import com.hfad.thinder.data.model.USERTYPE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class SampleStudent extends SampleUser {

    public static final String typeStudent = "STUDENT";

    public static final String degreeName = "M. Sc. Informatik";
    public static final UUID degreeId =
            new UUID(0x8a3a5503cd414b9aL, 0xa86eaa3d64c4d314L);

    public static final Degree degree = new Degree(degreeName, degreeId);
    public static final ArrayList<Degree> degrees =
            new ArrayList<>(Collections.singletonList(degree));

    public static Student studentObject() {
        return new Student(USERTYPE.STUDENT, SampleUser.id, SampleUser.active, SampleUser.uni_id,
                SampleUser.mail, SampleUser.firstName, SampleUser.lastName,
                degrees, SampleUser.complete);
    }

    public static JSONObject getStudentJson() throws JSONException {
        degrees.get(0).setUniversityID(SampleUser.uni_id);
        return SampleUser.getUserJson()
                .put("type", typeStudent)
                .put("degrees", new JSONArray().put(new JSONObject()
                        .put("id", degreeId.toString())
                        .put("degree", degreeName)
                        .put("university_id", SampleUser.uni_id)
                ));
    }
}
