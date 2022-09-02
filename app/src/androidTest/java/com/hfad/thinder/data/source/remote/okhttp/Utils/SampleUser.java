package com.hfad.thinder.data.source.remote.okhttp.Utils;

import com.hfad.thinder.data.model.Login;
import com.hfad.thinder.data.model.USERTYPE;
import com.hfad.thinder.data.model.User;
import com.hfad.thinder.data.model.UserCreation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class SampleUser {

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

    public static UserCreation userCreation() {
        return new UserCreation(firstName, lastName, mail, password);
    }
    // two incomplete Users
    public static User emptyStudent(){
        return new User(USERTYPE.STUDENT,id,active,uni_id,mail,firstName,lastName,false);
    }
    public static User emptySupervisor(){
        return new User(USERTYPE.SUPERVISOR,id,active,uni_id,mail,firstName,lastName,false);
    }
    public static String authHeader() {
        return authHeader;
    }

    public static Login getLogin() {
        return new Login(mail, password);
    }
}
