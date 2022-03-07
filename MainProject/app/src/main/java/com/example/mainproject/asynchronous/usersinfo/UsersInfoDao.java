package com.example.mainproject.asynchronous.usersinfo;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsersInfoDao {
    @Query("INSERT INTO UsersInfo(Name, Salt, Hash, DP, Money, Year, Month, Day, Hour, Minute, Second) VALUES(:name, :salt, :hash, :dp, :money, :year, :month, :day, :hour, :minute, :second)")
    void signUpTask(String name, String salt, String hash, String dp, String money, int year, int month, int day, int hour, int minute, int second);

    @Query("UPDATE UsersInfo " +
            "SET Year = :year, " +
                "Month = :month, " +
                "Day = :day, " +
                "Hour = :hour, " +
                "Minute = :minute, " +
                "Second = :second " +
            "WHERE Name = :name")
    void updateTimeTask(String name, int year, int month, int day, int hour, int minute, int second);

    @Query("UPDATE UsersInfo " +
            "SET DP = :dp, " +
                "Money = :money " +
            "WHERE Name = :name")
    void updatePossessionTask(String name, String dp, String money);

    @Query("SELECT * FROM UsersInfo WHERE Name = :name")
    List<UsersInfo> getLineTask(String name);
}