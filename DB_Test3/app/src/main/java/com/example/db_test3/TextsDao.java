package com.example.db_test3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface TextsDao {
    @Insert
    void insert(Texts text);

    @Update
    void update(Texts text);

    @Delete
    void delete(Texts text);

    @Query("SELECT * FROM TextData")
    List<Texts> getAll();
}
