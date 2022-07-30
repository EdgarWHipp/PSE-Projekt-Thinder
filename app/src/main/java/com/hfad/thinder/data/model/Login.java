package com.hfad.thinder.data.model;

public class Login {
  public String password;
  public String mail;

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

  public Login(String password, String mail) {
    this.password = password;
    this.mail = mail;
  }
}
