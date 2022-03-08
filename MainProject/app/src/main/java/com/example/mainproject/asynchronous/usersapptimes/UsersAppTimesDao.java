package com.example.mainproject.asynchronous.usersapptimes;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersAppTimesDao {
    @Insert
    void signUpTask(UsersAppTimes usersAppTimes);

    @Query("UPDATE UsersAppTimes " +
            "SET Year = :year, " +
            "    Month = :month, " +
            "    Day = :day, " +
            "    Hour = :hour, " +
            "    Minute = :minute, " +
            "    Second = :second " +
            "WHERE Name = :name")
    void updateTimeTask(String name,
                        int year,
                        int month,
                        int day,
                        int hour,
                        int minute,
                        int second);

    @Query("SELECT * FROM UsersAppTimes WHERE Name = :name")
    List<UsersAppTimes> getTimesTask(String name);
}
