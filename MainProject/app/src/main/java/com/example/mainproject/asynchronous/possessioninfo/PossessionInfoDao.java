package com.example.mainproject.asynchronous.possessioninfo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface PossessionInfoDao {
    @Insert
    void insert(PossessionInfo possessionInfo);

    @Query("UPDATE PossessionInfo " +
            "SET Name = :name, " +
            "    Year = :year, " +
            "    Month = :month, " +
            "    Day =  :day, " +
            "    Hour = :hour, " +
            "    Minute = :minute, " +
            "    Second = :second " +
            "WHERE Name = :name")
    void UpDateTime(String name, int year, int month, int day, int hour, int minute, int second);

    @Query("DELETE FROM PossessionInfo WHERE Name = :name")
    void deletePossessionInfo(String name);

    @Query("DELETE FROM PossessionInfo")
    void deleteAll();

    @Query("SELECT * FROM PossessionInfo WHERE Name = :name")
    List<PossessionInfo> getLine(String name);

    @Query("SELECT * FROM PossessionInfo")
    List<PossessionInfo> getAll();
}