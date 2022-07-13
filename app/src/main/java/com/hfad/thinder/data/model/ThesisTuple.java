package com.hfad.thinder.data.model;

import com.hfad.thinder.data.source.result.Result;

public class ThesisTuple {
    private Thesis thesis;
    private Result result;

    public ThesisTuple(Thesis thesis, Result result) {
        this.thesis = thesis;
        this.result = result;
    }

    public Thesis getThesis() {
        return thesis;
    }

    public void setThesis(Thesis thesis) {
        this.thesis = thesis;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
