package com.example.db_test3.db.tests;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.db_test3.db.tests.Texts;

import java.util.List;

@Dao
public interface TextsDao {
    @Insert
    void insert(Texts text);

    @Update
    void update(Texts text);

    @Query("SELECT * FROM TextData")
    List<Texts> getAll();

    @Query("SELECT * FROM TextData WHERE id IN (:ids)")
    List<Texts> loadAllByIds(int[] ids);

    @Query("DELETE FROM TextData")
    void deleteAll();

    @Query("DELETE FROM TextData WHERE Text = (:text)")
    void deleteText(String text);
}
