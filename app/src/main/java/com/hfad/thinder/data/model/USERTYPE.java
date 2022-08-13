package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines the 2 user types student and supervisor. During later development a admin type may be useful.
 */
public enum USERTYPE {
    @SerializedName("STUDENT")
    STUDENT,
    @SerializedName("SUPERVISOR")
    SUPERVISOR
}
