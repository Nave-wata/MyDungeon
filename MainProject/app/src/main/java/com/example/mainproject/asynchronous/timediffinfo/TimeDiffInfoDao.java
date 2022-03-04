package com.example.mainproject.asynchronous.timediffinfo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface TimeDiffInfoDao {
    @Insert
    void insert(TimeDiffInfo timeDiffInfo);

    @Query("UPDATE TimeDiffInfo " +
            "SET Name = :name, " +
            "    Year = :year, " +
            "    Month = :month, " +
            "    Day =  :day, " +
            "    Hour = :hour, " +
            "    Minute = :minute, " +
            "    Second = :second " +
            "WHERE Name = :name")
    void UpDateTime(String name, int year, int month, int day, int hour, int minute, int second);

    @Query("DELETE FROM TimeDiffInfo WHERE Name = :name")
    void deletePossessionInfo(String name);

    @Query("DELETE FROM TimeDiffInfo")
    void deleteAll();

    @Query("SELECT * FROM TimeDiffInfo WHERE Name = :name")
    List<TimeDiffInfo> getLine(String name);

    @Query("SELECT * FROM TimeDiffInfo")
    List<TimeDiffInfo> getAll();
}