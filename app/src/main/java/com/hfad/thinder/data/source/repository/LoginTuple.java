package com.hfad.thinder.data.source.repository;


import com.hfad.thinder.data.source.result.Result;

import java.util.UUID;

public class LoginTuple {
  private Result first;
  private UUID second;

  public Result getFirst() {
    return first;
  }

  public UUID getSecond() {
    return second;
  }

  public LoginTuple(Result first, UUID second){
    this.first = first;
    this.second = second;
  }


}
