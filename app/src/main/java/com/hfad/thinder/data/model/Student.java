package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Student {

    String firstName;

    String lastName;
    @SerializedName("id")
    int studentId;

    String university;

    String degree;

}
