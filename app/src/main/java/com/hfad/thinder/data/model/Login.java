package com.hfad.thinder.data.model;

/**
 * This class saves the password and mail, both which are needed for a successful login.
 */
public class Login {
    public String password;
    public String mail;

    public Login(String mail, String password) {
        this.password = password;
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
