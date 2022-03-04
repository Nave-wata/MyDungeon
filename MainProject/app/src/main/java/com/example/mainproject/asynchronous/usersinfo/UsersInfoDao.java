package com.example.mainproject.asynchronous.usersinfo;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsersInfoDao {
    @Query("INSERT INTO UsersInfo(Name, Salt, Hash, Year, Month, Day, Hour, Minute, Second) VALUES(:name, :salt, :hash, :year, :month, :day, :hour, :minute, :second)")
    void signUpTask(String name, String salt, String hash, int year, int month, int day, int hour, int minute, int second);

    @Query("UPDATE UsersInfo " +
            "SET Year = :year, " +
                "Month = :month, " +
                "Day = :day, " +
                "Hour = :hour, " +
                "Minute = :minute, " +
                "Second = :second " +
            "WHERE Name = :name")
    void updateTimeTask(String name, int year, int month, int day, int hour, int minute, int second);

    @Query("DELETE FROM UsersInfo WHERE Name = :name")
    void deleteUserInfo(String name);

    @Query("DELETE FROM UsersInfo")
    void deleteAll();

    @Query("SELECT * FROM UsersInfo WHERE Name = :name")
    List<UsersInfo> getLine(String name);

    @Query("SELECT * FROM UsersInfo")
    List<UsersInfo> getAll();
}