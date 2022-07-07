package com.hfad.thinder.data.source.remote;

public class Login {
  public String password;
  public String eMail;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String geteMail() {
    return eMail;
  }

  public void seteMail(String eMail) {
    this.eMail = eMail;
  }

  public Login(String password, String eMail) {
    this.password = password;
    this.eMail = eMail;
  }
}
