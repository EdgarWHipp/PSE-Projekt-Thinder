package com.hfad.thinder.data.source.repository;


import com.hfad.thinder.data.source.result.Result;

public class LoginTuple {
  private Result first;
  private int second;

  public Result getFirst() {
    return first;
  }

  public int getSecond() {
    return second;
  }

  public LoginTuple(Result first, int second){
    this.first = first;
    this.second = second;
  }


}
