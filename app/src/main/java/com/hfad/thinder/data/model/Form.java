package com.hfad.thinder.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class defines attributes of a Form, only the questions are needed to create a thesis.
 */
public class Form {
    @SerializedName("questions")
    private String questions;
    @SerializedName("answers")
    private String answers;

    public Form(String questions) {
        this.questions = questions;
    }
    public Form(String questions,String answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

}
