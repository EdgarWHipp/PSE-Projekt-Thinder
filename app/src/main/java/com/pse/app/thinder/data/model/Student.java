package com.pse.app.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Student {

    String firstName;

    String lastName;
    @SerializedName("id")
    int studentId;

    String password;

    String eMail;

    String university;

    String degree;

}
