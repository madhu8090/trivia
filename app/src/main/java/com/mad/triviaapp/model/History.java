package com.mad.triviaapp.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "history_table")
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private long creationDate;

    @TypeConverters(QuestionConvertor.class)
    private List<Question> questions;

    public History() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
