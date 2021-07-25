package com.mad.triviaapp.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mad.triviaapp.dao.HistoryDao;
import com.mad.triviaapp.dao.OptionDao;
import com.mad.triviaapp.model.History;
import com.mad.triviaapp.model.Option;

@Database(entities = {History.class}, version = 2, exportSchema = false)
public abstract class HistoryDataBase extends RoomDatabase {

    private static HistoryDataBase instance;

    public abstract HistoryDao historyDao();

    // singleton class to access database instance
    public static synchronized HistoryDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HistoryDataBase.class, "history_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // create the database
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}