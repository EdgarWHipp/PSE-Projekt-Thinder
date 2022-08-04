package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

public class Form {
    @SerializedName("text")
    private String questions;
    private String answers;

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Form(String questions) {
        this.questions = questions;
    }

    public String getQuestions() {
        return questions;
    }

}
