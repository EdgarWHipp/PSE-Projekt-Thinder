package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Supervisor {
    String firstName;

    String lastName;
    @SerializedName("id")
    int supervisorId;

    String university;
    @SerializedName("degree")
    String academicDegree;

    String location;

    String institute;

    String phoneNumber;

}
