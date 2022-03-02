package com.example.main.asynchronous.usersinfo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UsersInfoDao {
    @Insert
    void insert(UsersInfo usersInfo);

    @Query("DELETE FROM UsersInfo WHERE Name = :name")
    void deleteUserInfo(String name);

    @Query("DELETE FROM UsersInfo")
    void deleteAll();

    @Query("SELECT * FROM UsersInfo WHERE Name = :name")
    List<UsersInfo> getLine(String name);

    @Query("SELECT * FROM UsersInfo")
    List<UsersInfo> getAll();
}
