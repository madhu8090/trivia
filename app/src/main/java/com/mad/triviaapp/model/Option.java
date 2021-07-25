package com.mad.triviaapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "option_table")
public class Option {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String option_text;
    private int question_id;

    public Option(String option_text, int question_id) {
        this.option_text = option_text;
        this.question_id = question_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getOption_text() {
        return option_text;
    }

    public void setOption_text(String option_text) {
        this.option_text = option_text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
