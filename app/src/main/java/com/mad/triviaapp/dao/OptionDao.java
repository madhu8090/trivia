package com.mad.triviaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mad.triviaapp.model.Option;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface OptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Option option);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Option> options);

    @Query("SELECT * FROM option_table")
    LiveData<List<Option>> loadAll();

    @Query("SELECT * FROM option_table WHERE question_id = :questionId")
    LiveData<List<Option>> loadAllByQuestionId(Long questionId);
}
