package com.mad.triviaapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Questions {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String question_text;
    private Constants.QType question_type;

    public Questions(String question_text, Constants.QType question_type) {
        this.question_text = question_text;
        this.question_type = question_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public Constants.QType getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Constants.QType question_type) {
        this.question_type = question_type;
    }
}
