package com.hfad.thinder.data.model;


import com.hfad.thinder.data.source.result.Result;

import java.util.UUID;

public class LoginTuple {
    private final Result first;
    private final UUID second;

    public LoginTuple(Result first, UUID second) {
        this.first = first;
        this.second = second;
    }

    public Result getFirst() {
        return first;
    }

    public UUID getSecond() {
        return second;
    }


}
