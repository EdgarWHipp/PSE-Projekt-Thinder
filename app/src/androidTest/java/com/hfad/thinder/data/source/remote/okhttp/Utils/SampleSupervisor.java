package com.hfad.thinder.data.source.remote.okhttp.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SampleSupervisor {

    public static final String typeSupervisor = "SUPERVISOR";

    public static final String academicDegree = "M. Sc.";
    public static final String officeNumber = "Room 102";
    public static final String building = "Building 50.34";
    public static final String institute = "Telematik";
    public static final String phoneNumber = "0173 1234567";

    public static JSONObject getSupervisorJson() throws JSONException {
        return SampleUser.getUserJson()
                .put("type", typeSupervisor)
                .put("academicDegree", academicDegree)
                .put("officeNumber", officeNumber)
                .put("building", building)
                .put("institute", institute)
                .put("phoneNumber", phoneNumber);
    }
}
