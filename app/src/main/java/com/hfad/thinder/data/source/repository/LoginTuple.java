package com.hfad.thinder.data.source.repository;

import com.hfad.thinder.viewmodels.LoginResult;

public class LoginTuple {
  private LoginResult first;
  private int second;

  public LoginResult getFirst() {
    return first;
  }

  public int getSecond() {
    return second;
  }

  public LoginTuple(LoginResult first, int second){
    this.first = first;
    this.second = second;
  }


}
