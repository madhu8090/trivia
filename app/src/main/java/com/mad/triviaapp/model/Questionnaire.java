package com.mad.triviaapp.model;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Questionnaire {

    private Questions question;
    private List<Option> options;

    public Questionnaire(Questions question, List<Option> options) {
        this.question = question;
        this.options = options;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public static Questionnaire fromJson(String json) {
        return new Gson().fromJson(json, Questionnaire.class);
    }

    @NotNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
