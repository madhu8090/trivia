package com.mad.triviaapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mad.triviaapp.dao.OptionDao;
import com.mad.triviaapp.dao.QuestionsDao;
import com.mad.triviaapp.database.OptionsDataBase;
import com.mad.triviaapp.database.QuestionDataBase;
import com.mad.triviaapp.model.Option;
import com.mad.triviaapp.model.Questions;

import java.util.List;

public class QuestionRepository {
    private QuestionsDao questionDao;
    private OptionDao optionDao;
    private LiveData<List<Questions>> questionList;
    private LiveData<List<Option>> optionList;

    public QuestionRepository(Application application) {
        QuestionDataBase database = QuestionDataBase.getInstance(application);
        OptionsDataBase optionsDataBase = OptionsDataBase.getInstance(application);
        questionDao = database.questionDao();
        optionDao = optionsDataBase.optionDao();
        questionList = questionDao.loadAll();
        optionList = optionDao.loadAll();
    }

    public void insert(Questions question) {
        new InsertQuestionAsyncTask(questionDao).execute(question);
    }

    public LiveData<List<Questions>> getQuestionList() {

        return questionList;
    }

    public LiveData<List<Option>> getOptions() {
        return optionList;
    }

    private static class InsertQuestionAsyncTask extends AsyncTask<Questions, Void, Void> {
        private QuestionsDao questionDao;

        private InsertQuestionAsyncTask(QuestionsDao questionDao) {
            this.questionDao = questionDao;
        }

        @Override
        protected Void doInBackground(Questions... questions) {
            questionDao.insert(questions[0]);
            return null;
        }
    }
}