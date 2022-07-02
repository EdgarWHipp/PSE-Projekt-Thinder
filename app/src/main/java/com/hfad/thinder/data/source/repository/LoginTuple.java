package com.hfad.thinder.data.source.repository;


import com.hfad.thinder.data.source.result.Result;

public class LoginTuple {
  private Result first;
  private String second;

  public Result getFirst() {
    return first;
  }

  public String getSecond() {
    return second;
  }

  public LoginTuple(Result first, String second){
    this.first = first;
    this.second = second;
  }


}
