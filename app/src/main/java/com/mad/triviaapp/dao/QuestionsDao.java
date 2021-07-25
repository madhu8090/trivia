package com.mad.triviaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mad.triviaapp.model.Questions;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface QuestionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Questions question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Questions> questions);

    @Query("SELECT * FROM questions")
    LiveData<List<Questions>> loadAll();
}
