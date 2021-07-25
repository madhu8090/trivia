package com.mad.triviaapp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mad.triviaapp.dao.OptionDao;
import com.mad.triviaapp.model.Option;

@Database(entities = {Option.class}, version = 1, exportSchema = false)
public abstract class OptionsDataBase extends RoomDatabase {

    private static OptionsDataBase instance;

    public abstract OptionDao optionDao();

    // singleton class to access database instance
    public static synchronized OptionsDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OptionsDataBase.class, "options_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // prepopulate options for questions
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private OptionDao optionDao;

        private PopulateDbAsyncTask(OptionsDataBase db) {
            optionDao = db.optionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("Populating options", "Success");
            optionDao.insert(new Option("Sachin Tendulkar", 1));
            optionDao.insert(new Option("Virat Kolli", 1));
            optionDao.insert(new Option("Adam Gilchirst", 1));
            optionDao.insert(new Option("Jacques Kallis", 1));

            optionDao.insert(new Option("White", 2));
            optionDao.insert(new Option("Yellow", 2));
            optionDao.insert(new Option("Orange", 2));
            optionDao.insert(new Option("Green", 2));
            return null;
        }
    }
}