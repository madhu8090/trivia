package com.mad.triviaapp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mad.triviaapp.dao.QuestionsDao;
import com.mad.triviaapp.model.Constants;
import com.mad.triviaapp.model.Questions;

@Database(entities = {Questions.class}, version = 1, exportSchema = false)
public abstract class QuestionDataBase extends RoomDatabase {

    private static QuestionDataBase instance;

    public abstract QuestionsDao questionDao();

    // singleton class to access database instance
    public static synchronized QuestionDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionDataBase.class, "question_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // populating question when database is created.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuestionsDao questionDao;

        private PopulateDbAsyncTask(QuestionDataBase db) {
            questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("Populating questions", "Success");
            questionDao.insert(new Questions("Who is the best cricketer in the world?", Constants.QType.SINGLE));
            questionDao.insert(new Questions("What are the colors in the Indian national flag? Select all:", Constants.QType.MULTIPLE));
            return null;
        }
    }
}