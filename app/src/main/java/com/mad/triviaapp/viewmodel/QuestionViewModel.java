package com.mad.triviaapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mad.triviaapp.model.Option;
import com.mad.triviaapp.model.Questions;
import com.mad.triviaapp.repository.QuestionRepository;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionRepository repository;
    private LiveData<List<Questions>> questions;
    private LiveData<List<Option>> options;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        repository = new QuestionRepository(application);
        questions = repository.getQuestionList();
        options = repository.getOptions();
    }

    public void insert(Questions question) {
        repository.insert(question);
    }

    public LiveData<List<Questions>> getQuestions() {
        return questions;
    }

    public LiveData<List<Option>> getOptions() {
        return options;
    }


}
