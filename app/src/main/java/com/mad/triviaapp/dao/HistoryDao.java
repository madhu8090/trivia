package com.mad.triviaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mad.triviaapp.model.History;
import com.mad.triviaapp.model.User;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void insertHistory(History history);

    @Update
    void updateHistory(History history);

    @Delete
    void deleteHistory(History history);

    @Query("DELETE FROM history_table")
    void deleteAllHistory();

    @Query("SELECT * FROM history_table")
    LiveData<List<History>> getAllHistory();

}
