package com.example.mainproject.asynchronous.usersinfo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsersInfoDao {
    @Insert
    void signUpTask(UsersInfo usersInfo);

    @Query("SELECT * FROM UsersInfo WHERE Name = :name")
    List<UsersInfo> getUserInfoTask(String name);
}